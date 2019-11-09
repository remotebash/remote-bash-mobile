package com.remotebash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.remotebash.adapter.LaboratorioListAdapter
import com.remotebash.model.LaboratorioModel
import com.remotebash.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_laboratorio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Laboratorio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laboratorio)

        val callRetrofit = RetrofitInitializer().laboratorioService().listLaboratorio()
        callRetrofit.enqueue(object: Callback<List<LaboratorioModel>?> {
            override fun onFailure(call: Call<List<LaboratorioModel>?>, t: Throwable) {
                Log.e("onFailure error", t?.message)
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
        val newLaboratorio = Intent(this, NewLaboratorio::class.java)
        startActivity(newLaboratorio)
    }


}
