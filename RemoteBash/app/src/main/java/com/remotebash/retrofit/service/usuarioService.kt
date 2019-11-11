package com.remotebash.retrofit.service

import com.remotebash.model.usuario
import retrofit2.http.GET
import retrofit2.Call

interface usuarioService {

    @GET("login")
    fun usuario(): Call<usuario>
}