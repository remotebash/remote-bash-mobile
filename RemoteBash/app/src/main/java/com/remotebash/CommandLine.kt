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
        pbCircular.visibility = View.VISIBLE
        val idUsuario = preferencias!!.getInt("idUsuario", 0)

        val idComputador = intent.getLongExtra("idPc", 0)
        Log.d("IdPC", idComputador.toString())

        var idLaboratorio = intent.getLongExtra("idLab", 0)
        Log.d("IdLab", idLaboratorio.toString())

        val comandoModel =
            ComandoModel(etComando.text.toString(), idComputador.toInt(), "Windows", idUsuario, idLaboratorio.toInt())

        if(idLaboratorio.toString() == "0") {
            val callCommandLine = RetrofitInitializer().comandoService().enviarComandoPC(comandoModel)
            callCommandLine.enqueue(object : Callback<ComandoModel> {
                override fun onFailure(call: Call<ComandoModel>, t: Throwable) {
                    pbCircular.visibility = View.INVISIBLE
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
                    pbCircular.visibility = View.INVISIBLE
                }

            })

        } else {
            val callCommandLine = RetrofitInitializer().comandoService().enviarComandoLab(comandoModel)
            callCommandLine.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    pbCircular.visibility = View.INVISIBLE
                    Log.e("onFailure command error", t.toString())
                    Toast.makeText(this@CommandLine, "Erro de conexão", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val campoRetorno = findViewById<TextView>(R.id.tvRetorno)
                    if (response.code() == 200) {
                        response.body()?.let {
                            Log.e("Response 200", it.toString())
                            campoRetorno.text =
                                String.format("%s\n%s\n%s", campoRetorno.text, etComando.text, it)
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
                    pbCircular.visibility = View.INVISIBLE
                }

            })

        }



    }


}
