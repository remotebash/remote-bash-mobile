package com.remotebash

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
        if (etAndar.text.isNullOrBlank()) {
            etAndar.setBackgroundColor(R.drawable.edit_text_login_err)
            etAndar.requestFocus()
            Toast.makeText(this, "Nome Computador Obrigatorio!", Toast.LENGTH_SHORT).show()
        } else {
            val laboratorio = LaboratorioModel(etAndar.text.toString(), etCapacidade.text.toString())
            val callAddLaboratorios = RetrofitInitializer().laboratorioService().addLaboratorio(laboratorio)

            callAddLaboratorios.enqueue(object : Callback<LaboratorioModel> {
                override fun onFailure(call: Call<LaboratorioModel>, t: Throwable) {
                    Log.e("onFailure addLab error", t.toString())
                    Toast.makeText(this@NewLaboratorio, "Erro de conexão", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<LaboratorioModel>, response: Response<LaboratorioModel>) {
                    response.body()?.let {
                        Toast.makeText(this@NewLaboratorio, "Laboratório cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                        onBackPressed()
                    }
                }

            })
        }
    }
}
