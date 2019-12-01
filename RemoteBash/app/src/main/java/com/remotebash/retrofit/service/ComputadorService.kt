package com.remotebash.retrofit.service

import com.remotebash.model.ComputadorModel
import com.remotebash.model.LaboratorioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ComputadorService {

    @POST("register/computers")
    fun addComputador(@Body computador: ComputadorModel): Call<ComputadorModel>

    @GET("search/laboratories/{id}")
    fun listLaboratorio(): Call<LaboratorioModel>
}