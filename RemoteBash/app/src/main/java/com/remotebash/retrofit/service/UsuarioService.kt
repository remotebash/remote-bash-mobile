package com.remotebash.retrofit.service

import com.remotebash.model.UsuarioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioService {

    @POST("user/login")
    fun usuario(@Body usuario: UsuarioModel): Call<UsuarioModel>
}