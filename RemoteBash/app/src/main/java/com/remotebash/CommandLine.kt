package com.remotebash

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.remotebash.model.ComandoModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_command_line.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//command, operationalSystem, userId, idComputer
class CommandLine : AppCompatActivity() {

    var preferencias: SharedPreferences? = null
    var editPreferencias: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_line)

        preferencias = getSharedPreferences("remotebash", Context.MODE_PRIVATE)
        editPreferencias = preferencias?.edit()
    }

    fun enviarComnado(v: View) {
        val idUsuario = preferencias!!.getInt("idUsuario", -1)
        val idComputador = intent.getIntExtra("idComputador", 2)

        val comandoModel =
            ComandoModel(etComando.text.toString(), idComputador, "Windows", idUsuario)

        val callCommandLine = RetrofitInitializer().comandoService().enviarComando(comandoModel)
        callCommandLine.enqueue(object : Callback<ComandoModel> {
            override fun onFailure(call: Call<ComandoModel>, t: Throwable) {
                Log.e("onFailure command error", t.toString())
                Toast.makeText(this@CommandLine, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ComandoModel>, response: Response<ComandoModel>) {
                val campoRetorno = findViewById<TextView>(R.id.tvRetorno)
                if (response.code() == 200) {
                    response.body()?.let {
                        Log.e("Response 200", it.toString())
                        campoRetorno.text =
                            String.format("%s\n%s\n%s", campoRetorno.text, it.command, it.result)
                        etComando.setText("")
                    }
                } else {
                    response.errorBody()?.let {
                        Log.e("Response ${response.code()}", it.string())
                        campoRetorno.text = String.format(
                            "%s\n%s\n%s",
                            campoRetorno.text,
                            etComando.text,
                            getString(R.string.ocorreuError)
                        )
                        etComando.setText("")
                    }
                }
            }

        })


    }


}
