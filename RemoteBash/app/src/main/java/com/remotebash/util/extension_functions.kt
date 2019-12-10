package com.remotebash.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Camera
import android.os.Build
import android.os.SystemClock
import me.dm7.barcodescanner.core.CameraUtils
import me.dm7.barcodescanner.zxing.ZXingScannerView
import kotlin.concurrent.thread

fun ZXingScannerView.startCameraForAllDevices(context: Context) {
    this.configCameraForAllDevices(context)
    this.startCamera()
    this.setTag(this.id, true)
}

private fun ZXingScannerView.configCameraForAllDevices(context: Context) {
    this.setBorderColor(Color.RED)
    this.setLaserColor(Color.YELLOW)
    this.setAutoFocus(true)
    this.rotation = 0.0F
    val brand = Build.MANUFACTURER
    if (brand.equals("HUAWEI", true)) {
        this.setAspectTolerance(0.5F)
    }
}

fun ZXingScannerView.stopCameraForAllDevices() {
    this.stopCamera()
    this.releaseForAllDevices()
    this.setTag(this.id, false)
}

private fun ZXingScannerView.releaseForAllDevices() {
    val camera = CameraUtils.getCameraInstance()
    if (camera != null) {
        (camera as Camera).release()
    }
}


fun ZXingScannerView.isCameraStarted(): Boolean {
    val startData = this.getTag(this.id)
    val startStatus = (startData ?: false) as Boolean
    return startStatus
}


fun ZXingScannerView.isFlashSupported(context: Context) =
    context
        .packageManager
        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

fun ZXingScannerView.enableFlash(
    context: Context,
    status: Boolean
) {
    if (this.isFlashSupported(context)) {
        this.flash = status
    }
}

fun ZXingScannerView.threadCallWhenCameraIsWorking(callback: () -> Unit) {
    thread {
        while (!this.isShown()) {
            SystemClock.sleep(1000)
        }
        callback()
    }
}