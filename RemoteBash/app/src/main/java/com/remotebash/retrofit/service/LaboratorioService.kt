package com.remotebash.retrofit.service

import com.remotebash.model.LaboratorioModel
import retrofit2.Call
import retrofit2.http.GET

interface LaboratorioService {

    @GET("computers")
    fun listLaboratorio(): Call<List<LaboratorioModel>>


}
