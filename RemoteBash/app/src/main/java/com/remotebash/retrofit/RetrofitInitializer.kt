package com.remotebash.retrofit

import com.remotebash.retrofit.service.LaboratorioService
import retrofit2.Retrofit
import com.remotebash.retrofit.service.UsuarioService
import retrofit2.converter.gson.GsonConverterFactory
import com.remotebash.retrofit.service.ComandoService
import com.remotebash.retrofit.service.ComputadorService
import javax.xml.datatype.DatatypeConstants.SECONDS
import javax.xml.datatype.DatatypeConstants.MINUTES
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class RetrofitInitializer {

    /*
    val gson = GsonBuilder()
            .setLenient()
            .create()
    */
    var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://3.94.151.158:8082/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(/*gson*/))
        .build()

    //single-expression function
    fun laboratorioService() = retrofit.create(LaboratorioService::class.java)

    fun usuarioService() = retrofit.create(UsuarioService::class.java)
    
    fun computadorService() = retrofit.create(ComputadorService::class.java)

    fun comandoService() = retrofit.create(ComandoService::class.java)

}
