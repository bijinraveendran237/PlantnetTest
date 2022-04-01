package com.example.myapplication


import android.R.attr.path
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.File


interface ApiInterface {

    @GET("v2/identify/all")
    fun getMovies(@Query("api-key") apikey: String,
                  @Query("images") image1: String,
                  @Query("organs") organs1: String) : Call<Details>

    companion object {
        var BASE_URL = "https://my-api.plantnet.org/"

        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
    }