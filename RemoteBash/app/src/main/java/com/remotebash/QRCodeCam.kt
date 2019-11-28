package com.remotebash

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.remotebash.util.*
import kotlinx.android.synthetic.main.activity_qrcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


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
        tvContent.text = "***********"
        processButtonOpen(result)
        zXingScanner.resumeCameraPreview(this)
    }

    private fun processButtonOpen(result: Result) {
        if (result.text.contains("remotebash")) {
            setButtonOpenAction(resources.getString(R.string.registerPC))
            setTextViewAlert(status = false)
            ivValidation.setImageDrawable(getDrawable(R.mipmap.like))
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
        btnRegister.setOnClickListener {
            Toast.makeText(this, "Computador cadastrado com sucesso", Toast.LENGTH_SHORT).show()
            clearContent()
        }
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