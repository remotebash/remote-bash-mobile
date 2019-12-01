package com.remotebash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_computadores.*

class Computadores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computadores)

        Log.e("ID Laboratorio: ", intent.getStringExtra("idLaboratorio"))

        ibComando.setOnClickListener {
            val comando = Intent(this,  CommandLine::class.java)
            startActivity(comando)
        }
    }

    fun newComputer(v: View) {
        val newComputer = Intent(this, QRCodeCam::class.java)
        startActivity(newComputer)
    }
}
