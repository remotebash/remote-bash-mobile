package com.remotebash.retrofit.service

import com.remotebash.model.LaboratorioModel
import retrofit2.Call
import retrofit2.http.*

interface LaboratorioService {

    @GET("search/laboratories")
    fun listLaboratorio(): Call<List<LaboratorioModel>>

    @GET("search/laboratories/{id}")
    fun laboratorio(@Path("id") id: Int): Call<LaboratorioModel>

    @POST("register/laboratories")
    fun addLaboratorio(@Body laboratorio: LaboratorioModel): Call<LaboratorioModel>
}

