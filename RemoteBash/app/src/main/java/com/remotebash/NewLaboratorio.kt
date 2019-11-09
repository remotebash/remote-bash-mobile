package com.remotebash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class NewLaboratorio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_laboratorio)
    }

    fun onBackPressed(v: View) {
        super.onBackPressed()
    }

    fun salvarLaboratorio(v: View){
//https://medium.com/collabcode/consumindo-api-rest-no-android-com-retrofit-em-kotlin-parte-2-62d40b49f8be
    }
}
