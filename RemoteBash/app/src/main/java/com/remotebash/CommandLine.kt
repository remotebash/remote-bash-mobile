package com.remotebash

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.remotebash.model.ComandoModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_command__line.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//command, operationalSystem, userId, idComputer
class CommandLine : AppCompatActivity() {

    var preferencias: SharedPreferences? = null
    var editPreferencias: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command__line)

        preferencias = getSharedPreferences("remotebash", Context.MODE_PRIVATE)
        editPreferencias = preferencias?.edit()

    }

    fun enviarComnado(v: View) {
        val comando = etComando.text.toString()
        val idUsuario = preferencias!!.getInt("idUsuario", 2)
        val idComputador = intent.getIntExtra("idComputador", 2)

        Toast.makeText(this@CommandLine, "usuario: ${idUsuario} computador: ${idComputador}", Toast.LENGTH_LONG).show()
        val comandoModel = ComandoModel(comando, idComputador, "Windows", idUsuario)

        val callCommandLine = RetrofitInitializer().comandoService().enviarComando(comandoModel)
        callCommandLine.enqueue(object : Callback<ComandoModel> {
            override fun onFailure(call: Call<ComandoModel>, t: Throwable) {
                Log.e("onFailure command error", t.toString())
                Toast.makeText(this@CommandLine, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ComandoModel>, response: Response<ComandoModel>) {
                if(response.code() == 200) {
                    response.body()?.let {

                        val campoRetorno = tvRetorno.text.toString()
                        campoRetorno.plus("\n" + it.command).plus("\n" + it.result)
                        tvRetorno.setText(campoRetorno)
                        etComando.setText("")
                        Toast.makeText(this@CommandLine, "Comando executado", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    response.errorBody()?.let {

                        Log.e("Response 400", it.string())
                        var campoRetorno = tvRetorno.text.toString()
                        campoRetorno.plus("\nO computador não está online.")
                        tvRetorno.setText(campoRetorno)
                        etComando.setText("")
                    }
                }
            }

        })


    }


}
