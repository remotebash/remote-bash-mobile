package com.remotebash.retrofit.service

import com.remotebash.model.ComputadorModel
import com.remotebash.model.LaboratorioModel
import retrofit2.Call
import retrofit2.http.*

interface ComputadorService {

    @POST("register/computers")
    fun addComputador(@Body computador: ComputadorModel): Call<ComputadorModel>

    @PUT("update/computers/{id}")
    fun updatePcOnLab(@Path("id") id:Int): Call<LaboratorioModel>
}