package com.remotebash

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.remotebash.adapter.ComputadorListAdapter
import com.remotebash.adapter.LaboratorioListAdapter
import com.remotebash.model.ComputadorModel
import com.remotebash.model.LaboratorioModel
import com.remotebash.retrofit.RetrofitInitializer
import com.remotebash.retrofit.service.LaboratorioService
import com.remotebash.util.*
import kotlinx.android.synthetic.main.activity_laboratorios.*
import kotlinx.android.synthetic.main.activity_layout_cardview_lab.*
import kotlinx.android.synthetic.main.activity_layout_cardview_lab.laboratorio_item_nome
import kotlinx.android.synthetic.main.activity_layout_cardview_lab.view.*
import kotlinx.android.synthetic.main.activity_qrcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QRCodeCam : AppCompatActivity(),
    ZXingScannerView.ResultHandler,
    EasyPermissions.PermissionCallbacks {

    val REQUEST_CODE_CAMERA = 182

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        askCameraPermission()
        lastResultVerification()
    }

    private fun lastResultVerification() {
        val result = Database.getSavedResult(this)
        if (result != null) {
            processBarcodeResult(result.text, result.barcodeFormat.name)
        }
    }

    override fun onResume() {
        super.onResume()
        zXingScanner.setResultHandler(this)
        restartCameraIfInactive()
    }

    private fun restartCameraIfInactive() {
        if (!zXingScanner.isCameraStarted()
            && EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
        ) {
            startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        zXingScanner.stopCameraForAllDevices()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this
        )
    }

    override fun onPermissionsDenied(
        requestCode: Int,
        perms: MutableList<String>
    ) {
        askCameraPermission()
    }

    private fun askCameraPermission() {
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(this, REQUEST_CODE_CAMERA, Manifest.permission.CAMERA)
                .setRationale(getString(R.string.requestDescription))
                .setPositiveButtonText(getString(R.string.requestButtonOk))
                .setNegativeButtonText(getString(R.string.requestButtonCancel))
                .build()
        )
    }

    override fun onPermissionsGranted(
        requestCode: Int,
        perms: MutableList<String>
    ) {
        startCamera()
    }

    private fun startCamera() {
        if (!zXingScanner.isFlashSupported(this)) {
        }
        zXingScanner.startCameraForAllDevices(this)
    }


    override fun handleResult(result: Result?) {
        if (result == null) {
            unrecognizedCode(this, { clearContent() })
            return
        }

        processBarcodeResult(
            result.text,
            result.barcodeFormat.name
        )
    }

    private fun processBarcodeResult(
        text: String,
        barcodeFormatName: String
    ) {

        val resultSaved = Database.getSavedResult(this)
        if (resultSaved == null || !resultSaved.text.equals(text, true)) {
            notification(this)
        }

        val result = Result(
            text,
            text.toByteArray(),
            arrayOf(),
            BarcodeFormat.valueOf(barcodeFormatName)
        )

        Database.saveResult(this, result)
        tvContent.text = "********"
        processButtonOpen(result)
        zXingScanner.resumeCameraPreview(this)
    }

    private fun processButtonOpen(result: Result) {
        if (result.text.contains("remotebash")) {
            setButtonOpenAction(resources.getString(R.string.registerPC))
            setTextViewAlert(status = false)
            ivValidation.setImageDrawable(getDrawable(R.mipmap.like))
            btnRegister.setOnClickListener {
                btnRegister.apply {
                    var idLab = intent.getStringExtra("idLab")
                    var delimiter = ";"
                    var parts = result.toString().split(delimiter)

                    val macaddress = parts[2]
                    val ip = parts[3]
                    val operationalSystem = parts[4]
                    val ramMemory = parts[5]
                    val hdTotal = parts[6]
                    val hdUsage = parts[7]
                    val processorBrand = parts[8]
                    val processorModel = parts[10]

                    val computador =
                        ComputadorModel(
                            ip,
                            operationalSystem,
                            ramMemory,
                            hdTotal,
                            hdUsage,
                            processorBrand,
                            processorModel,
                            macaddress
                        )

                    val callAddComputers =
                        RetrofitInitializer().computadorService().addComputador(computador)

                    callAddComputers.enqueue(object : Callback<ComputadorModel> {
                        override fun onFailure(call: Call<ComputadorModel>, t: Throwable) {
                            Log.e("onFailure addPc error", t.toString())
                            Toast.makeText(this@QRCodeCam, "Erro de conexão", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(
                            call: Call<ComputadorModel>,
                            response: Response<ComputadorModel>
                        ) {

                            response.body()?.let {

                                val callListaLaboratorios =
                                    RetrofitInitializer().laboratorioService().listLaboratorio()
                                callListaLaboratorios.enqueue(object :
                                    Callback<List<LaboratorioModel>?> {
                                    override fun onFailure(
                                        call: Call<List<LaboratorioModel>?>,
                                        t: Throwable
                                    ) {
                                        Log.e("onFailure addPc error", t.toString())
                                        Toast.makeText(
                                            this@QRCodeCam,
                                            "Erro de conexão",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }

                                    override fun onResponse(
                                        call: Call<List<LaboratorioModel>?>,
                                        response: Response<List<LaboratorioModel>?>
                                    ) {
                                        response.body()?.let {

                                            var labModel = it[1]

                                            val callListPcs =
                                                RetrofitInitializer().computadorService()
                                                    .computadorForMac(computador.macaddress.toString())



                                            callListPcs.enqueue(object : Callback<ComputadorModel> {
                                                override fun onFailure(
                                                    call: Call<ComputadorModel>,
                                                    t: Throwable
                                                ) {

                                                }

                                                override fun onResponse(
                                                    call: Call<ComputadorModel>,
                                                    response: Response<ComputadorModel>
                                                ) {
                                                    response.body()?.let {

                                                        var idPc = response.body()!!.id
                                                        Log.e("ID do pc", idPc.toString())

                                                        val callPcOnLab =
                                                            RetrofitInitializer().computadorService()
                                                                .updatePcOnLab(
                                                                    labModel,
                                                                    idPc!!.toInt()
                                                                )

                                                        callPcOnLab.enqueue(object :
                                                            Callback<ComputadorModel> {
                                                            override fun onFailure(
                                                                call: Call<ComputadorModel>,
                                                                t: Throwable
                                                            ) {

                                                            }

                                                            override fun onResponse(
                                                                call: Call<ComputadorModel>,
                                                                response: Response<ComputadorModel>
                                                            ) {
                                                                response.body()?.let {
                                                                    clearContent()
                                                                    onBackPressed()
                                                                }
                                                            }

                                                        })

                                                    }
                                                }

                                            })

                                        }
                                    }

                                })
                                Toast.makeText(
                                    this@QRCodeCam,
                                    "Computador cadastrado com sucesso!",
                                    Toast.LENGTH_LONG
                                ).show()
                                clearContent()
                                onBackPressed()
                            }
                        }

                    })

                }
            }
        } else if (result.text.isNullOrEmpty()) {
            setButtonOpenAction(status = false)
            setTextViewAlert(status = false)
        } else {
            setTextViewAlert(resources.getString(R.string.invalidQRCode))
            setButtonOpenAction(status = false)
            ivValidation.setImageDrawable(getDrawable(R.mipmap.dislike))
        }
        ivValidation.visibility = View.VISIBLE
    }

    private fun setButtonOpenAction(
        label: String = "",
        status: Boolean = true
    ) {
        btnRegister.text = label
        btnRegister.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun setTextViewAlert(
        label: String = "",
        status: Boolean = true
    ) {
        tvAlert.text = label
        tvAlert.visibility = if (status) View.VISIBLE else View.GONE
    }


    fun clearContent(view: View? = null) {
        tvContent.text = getString(R.string.nothingRead)
        setButtonOpenAction(status = false)
        Database.saveResult(this)
        setTextViewAlert(status = false)
        ivValidation.visibility = View.GONE
    }

}