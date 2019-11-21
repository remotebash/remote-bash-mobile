package com.remotebash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.remotebash.model.LaboratorioModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_new_laboratorio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewLaboratorio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_laboratorio)
    }

    fun salvarLaboratorio(v: View) {
        if (etAndar.text.toString().isNullOrBlank()) {
            etAndar.setBackgroundColor(R.drawable.edit_text_login_err)
            etAndar.requestFocus()
            Toast.makeText(this, "Andar Obrigatorio!!", Toast.LENGTH_SHORT).show()
        }
        else {
            val laboratorio = LaboratorioModel(etAndar.text.toString(), etCapacidade.text.toString())
            val callAddLaboratorios = RetrofitInitializer().laboratorioService().addLaboratorio(laboratorio)

            callAddLaboratorios.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("onFailure addLab error", t?.message)
                    Toast.makeText(this@NewLaboratorio, "Erro de conexão", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    response.body()?.let {
                        if (it != null) {
                            Toast.makeText(this@NewLaboratorio, "Laboratório cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                            onBackPressed()
                        }
                    }
                }

            })
        }
    }
}
