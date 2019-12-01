package com.remotebash.retrofit

import com.remotebash.retrofit.service.LaboratorioService
import retrofit2.Retrofit
import com.remotebash.retrofit.service.UsuarioService
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.remotebash.retrofit.service.ComputadorService

class RetrofitInitializer {

    /*
    val gson = GsonBuilder()
            .setLenient()
            .create()
    */

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://3.94.151.158:8082/")
        .addConverterFactory(GsonConverterFactory.create(/*gson*/))
        .build()

    //single-expression function
    fun laboratorioService() = retrofit.create(LaboratorioService::class.java)

    fun usuarioService() = retrofit.create(UsuarioService::class.java)
    
    fun computadorService() = retrofit.create(ComputadorService::class.java)

}
