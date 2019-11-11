package com.remotebash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.remotebash.adapter.LaboratorioListAdapter
import com.remotebash.model.LaboratorioModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_laboratorio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class Laboratorio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laboratorio)

        val callRetrofit = RetrofitInitializer().laboratorioService().listLaboratorio()
        callRetrofit.enqueue(object: Callback<List<LaboratorioModel>?> {
            override fun onFailure(call: Call<List<LaboratorioModel>?>, t: Throwable) {
                Log.e("onFailure error", t?.message)
                Toast.makeText(this@Laboratorio,"Erro de conexão verifique a internet", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<LaboratorioModel>?>, response: Response<List<LaboratorioModel>?>) {
                response.body()?.let {
                    val labs:List<LaboratorioModel> = it
                    configureListLab(labs)
                }
            }

        })

    }

    private fun configureListLab(laboratorios: List<LaboratorioModel>){
        val recyclerView = recyclerview_lab
        recyclerView.adapter = LaboratorioListAdapter(laboratorios, this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    fun newLaboratorio(v: View){
        try {
            val newLaboratorio = Intent(this, NewLaboratorio::class.java)
            startActivity(newLaboratorio)
        }
        catch (e: Exception){
            Toast.makeText(this, "Erro ao acessar novos laboratorios", Toast.LENGTH_SHORT).show()
        }

    }


}
