package com.remotebash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.remotebash.adapter.ComputadorListAdapter
import com.remotebash.model.LaboratorioModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_computadores.*
import kotlinx.android.synthetic.main.activity_layout_cardview_pc.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Computadores : AppCompatActivity() {

    var preferencias: SharedPreferences? = null
    var editPreferencias: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computadores)

        preferencias = getSharedPreferences("remotebash", Context.MODE_PRIVATE)
        editPreferencias = preferencias?.edit()

        Log.e("ID Laboratorio", intent.getStringExtra("idLaboratorio"))

        ibComando.setOnClickListener {
            val comando = Intent(this, CommandLine::class.java)
            startActivity(comando)
        }

        pbCircular.visibility = View.VISIBLE
        val callListaComputadores = RetrofitInitializer().laboratorioService()
            .laboratorio(intent.getStringExtra("idLaboratorio").toInt())
        callListaComputadores.enqueue(object : Callback<LaboratorioModel> {
            override fun onFailure(call: Call<LaboratorioModel>, t: Throwable) {
                pbCircular.visibility = View.INVISIBLE
                Log.e("onFailure computs error", t.toString())
                Toast.makeText(
                    this@Computadores,
                    getString(R.string.erroConexao),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<LaboratorioModel>,
                response: Response<LaboratorioModel>
            ) {
                response.body()?.let {
                    val recyclerView = rvComputadores
                    recyclerView.adapter = ComputadorListAdapter(it, this@Computadores)
                    val layoutManager =
                        StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView.layoutManager = layoutManager
                }
                pbCircular.visibility = View.INVISIBLE
            }

        })

    }

    fun newComputer(v: View) {
        val newComputer = Intent(this, QRCodeCam::class.java)
        newComputer.putExtra("idLab", intent.getStringExtra("idLaboratorio"))
        startActivity(newComputer)
    }

    private var doubleBackToExit = false
    override fun onBackPressed() {
        if (doubleBackToExit) {
            editPreferencias?.putBoolean("autenticado", false)
            editPreferencias?.commit()
            super.onBackPressed()
        }
        this.doubleBackToExit = true
        Toast.makeText(this, "Toque novamente para sair", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExit = false }, 1500)
    }
}
