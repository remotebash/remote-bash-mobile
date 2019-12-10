package com.remotebash.retrofit.service

import com.remotebash.model.ComputadorModel
import com.remotebash.model.LaboratorioModel
import retrofit2.Call
import retrofit2.http.*

interface ComputadorService {

    @POST("register/computers")
    fun addComputador(@Body computador: ComputadorModel): Call<ComputadorModel>

    @GET("/search/computers/{id}")
    fun computador(@Path("id") id: Int): Call<ComputadorModel>

    @GET("/search/computers")
    fun computadorForMac(@Query("macAddressList") mac: String): Call<ComputadorModel>

    @PUT("update/computers/{id}")
    fun updatePcOnLab(@Body laboratorio: LaboratorioModel, @Path("id") id: Int): Call<ComputadorModel>

}