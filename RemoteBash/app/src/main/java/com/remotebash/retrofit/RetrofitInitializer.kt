package com.remotebash.retrofit

import com.remotebash.retrofit.service.LaboratorioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.90.2.38:8083/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //single-expression function
    fun laboratorioService() = retrofit.create(LaboratorioService::class.java)

}
