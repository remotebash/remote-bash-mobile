package com.gamota.leitorqrcode

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import com.gamota.leitorqrcode.util.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_main.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


class MainActivity : AppCompatActivity(),
    ZXingScannerView.ResultHandler,
    EasyPermissions.PermissionCallbacks {

    val REQUEST_CODE_CAMERA = 182
    val REQUEST_CODE_FULLSCREEN = 184

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        tvContent.text = result.text
        processBarcodeType(true, result.barcodeFormat.name)
        processButtonOpen(result)
        zXingScanner.resumeCameraPreview(this)
    }

    private fun processBarcodeType(status: Boolean = false, barcode: String = "") {
        tvQRCodeType.text = getString(R.string.qrcodeFormat) + barcode
        tvQRCodeType.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun processButtonOpen(result: Result) {
        when {
            URLUtil.isValidUrl(result.text) ->
                setButtonOpenAction(resources.getString(R.string.openUrl)) {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(result.text)
                    startActivity(i)
                }
            Patterns.EMAIL_ADDRESS.matcher(result.text).matches() ->
                setButtonOpenAction(getString(R.string.openEmail)) {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse("mailto:?body=${result.text}")
                    startActivity(i)
                }
            Patterns.PHONE.matcher(result.text).matches() ->
                setButtonOpenAction(getString(R.string.openCall)) {
                    val i = Intent(Intent.ACTION_DIAL)
                    i.data = Uri.parse("tel:${result.text}")
                    startActivity(i)
                }
            else -> setButtonOpenAction(status = false)
        }
    }

    private fun setButtonOpenAction(
        label: String = "",
        status: Boolean = true,
        callbackClick: () -> Unit = {}
    ) {
        btOpen.text = label
        btOpen.visibility = if (status) View.VISIBLE else View.GONE
        btOpen.setOnClickListener { callbackClick() }
    }


    fun clearContent(view: View? = null) {
        tvContent.text = getString(R.string.nothingRead)
        processBarcodeType(false)
        setButtonOpenAction(status = false)
        Database.saveResult(this)
    }
}