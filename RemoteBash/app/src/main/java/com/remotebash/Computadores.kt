package com.remotebash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Computadores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computadores)

        Toast.makeText(this, intent.getStringExtra("idLaboratorio"), Toast.LENGTH_SHORT).show()
    }
}
