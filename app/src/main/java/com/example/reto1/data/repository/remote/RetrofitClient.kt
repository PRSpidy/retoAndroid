package com.example.reto1.data.repository.remote

import com.example.reto1.MyApp
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // esta es la ip a la que vamos a hacer peticiones (es localhost pero desde el emulador no deja)
    // NOTE acordarse de que hace falta actualizar el manifest
    //const val API_URI = "http://10.5.7.114:8080/api/"
    const val API_URI = "http://10.5.7.184:8080/api/"


    var client = OkHttpClient.Builder().addInterceptor {chain ->
        val authToken = MyApp.userPreferences.fetchAuthToken()
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization","Bearer $authToken")
            .build()
        chain.proceed(newRequest)
    }.build()


    // creamos el cliente de retrofit con la url de la api
    val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(API_URI)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: APIInterface by lazy {
        retrofitClient
            .build()
            .create(APIInterface::class.java)
    }


}