package com.remotebash.retrofit

import com.remotebash.retrofit.service.LaboratorioService
import retrofit2.Retrofit
import com.remotebash.retrofit.service.usuarioService
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.15.8:8082/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //single-expression function
    fun laboratorioService() = retrofit.create(LaboratorioService::class.java)

    fun usuarioService() = retrofit.create(usuarioService::class.java)

}
