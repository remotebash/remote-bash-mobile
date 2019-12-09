package com.remotebash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.remotebash.model.ComputadorModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_computador_especifico.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComputadorEspecifico : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computador_especifico)

        btComando.setOnClickListener {
            val comando = Intent(this, CommandLine::class.java)
            startActivity(comando)
        }

        atualizarInfos(View(this))
    }

    fun atualizarInfos(v: View){

        pbCircular.visibility = View.VISIBLE

        val callComputador = RetrofitInitializer().computadorService().computador(intent.getLongExtra("idPc", 0).toInt())
        callComputador.enqueue(object : Callback<ComputadorModel> {
            override fun onFailure(call: Call<ComputadorModel>, t: Throwable) {
                pbCircular.visibility = View.INVISIBLE
                Log.e("onFailure lab error", t.toString())
                Toast.makeText(this@ComputadorEspecifico, getString(R.string.erroConexao), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ComputadorModel>, response: Response<ComputadorModel>) {
                response.body().let{
                    macaddress.text = it?.macaddress
                    sistemaOperacional.text = it?.operationalSystem
                    HDTotal.text = it?.hdTotal
                    HDUsado.text = it?.hdUsage
                    memoriaRAM.text = it?.ramMemory

                    var arrayHDtotal = it?.hdTotal?.split("GB")
                    var arrayHDusado = it?.hdUsage?.split("B")
                    pbProgresso.progress = (arrayHDusado!![0].toInt() * 100) / arrayHDtotal!![0].toInt()
                    namePorcentagem.text = ((arrayHDusado!![0].toInt() * 100) / arrayHDtotal!![0].toInt()).toString().plus("%")
                    processadorMarca.text = it?.processorBrand
                    processadorModelo.text = it?.processorModel
                    ip.text = it?.ip
                    Toast.makeText(this@ComputadorEspecifico, getString(R.string.dadosAtualizados), Toast.LENGTH_SHORT).show()
                }
                pbCircular.visibility = View.INVISIBLE
            }

        })
    }


}
