package com.remotebash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class Computadores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computadores)

        Toast.makeText(this, intent.getStringExtra("idLaboratorio"), Toast.LENGTH_SHORT).show()
    }

    fun newComputer(v: View) {
        val newComputer = Intent(this, QRCodeCam::class.java)
        startActivity(newComputer)
    }
}
