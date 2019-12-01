package com.remotebash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.remotebash.adapter.LaboratorioListAdapter
import com.remotebash.model.LaboratorioModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_laboratorios.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Laboratorio : AppCompatActivity() {

    var preferencias: SharedPreferences? = null
    var editPreferencias: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laboratorios)

        preferencias = getSharedPreferences("remotebash", Context.MODE_PRIVATE)
        editPreferencias = preferencias?.edit()

        val callListaLaboratorios = RetrofitInitializer().laboratorioService().listLaboratorio()
        callListaLaboratorios.enqueue(object : Callback<List<LaboratorioModel>?> {
            override fun onFailure(call: Call<List<LaboratorioModel>?>, t: Throwable) {
                Log.e("onFailure lab error", t.toString())
                Toast.makeText(this@Laboratorio, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<LaboratorioModel>?>, response: Response<List<LaboratorioModel>?>) {
                response.body()?.let {
                    val recyclerView = rvLaboratorios
                    recyclerView.adapter = LaboratorioListAdapter(it, this@Laboratorio)
                    val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView.layoutManager = layoutManager
                }
            }

        })

    }

    fun newLaboratorio(v: View) {
        val newLaboratorio = Intent(this, NewLaboratorio::class.java)
        startActivity(newLaboratorio)
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
