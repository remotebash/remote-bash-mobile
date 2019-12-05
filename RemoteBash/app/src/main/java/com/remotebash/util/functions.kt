package com.remotebash.util

import android.content.Context
import android.media.RingtoneManager
import android.widget.Toast
import com.remotebash.R

fun unrecognizedCode(context: Context, callbackClear: () -> Unit = {}) {
    Toast
        .makeText(
            context,
            context.getString(R.string.invalidCode),
            Toast.LENGTH_SHORT
        )
        .show()
    callbackClear()
}

fun notification(context: Context) {
    try {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(context.getApplicationContext(), notification)
        ringtone.play()
    } catch (e: Exception) {
    }
}