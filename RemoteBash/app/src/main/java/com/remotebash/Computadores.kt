package com.remotebash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_computadores.*

class Computadores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computadores)

        Toast.makeText(this, intent.getStringExtra("idLaboratorio"), Toast.LENGTH_SHORT).show()

        ibComando.setOnClickListener {
            val comando = Intent(this,  CommandLine::class.java)
            startActivity(comando)
        }
    }
}
