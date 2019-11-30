package com.remotebash.retrofit.service

import com.remotebash.model.LaboratorioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface LaboratorioService {

    @GET("search/laboratories")
    fun listLaboratorio(): Call<List<LaboratorioModel>>

    @POST("register/laboratories")
    fun addLaboratorio(@Body laboratorio: LaboratorioModel): Call<LaboratorioModel>
}

