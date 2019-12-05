package com.remotebash.retrofit.service

import com.remotebash.model.ComandoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ComandoService {

    @POST("register/command")
    fun enviarComando(@Body comando: ComandoModel): Call<ComandoModel>
}