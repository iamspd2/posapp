package com.example.posapp

//import kotlincodes.com.retrofitwithkotlin.model.DataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @Headers("Api-Key: 84eb75ed32164008b8bed2fcca1440f1")
    @GET("items")

    //@GET("photos")
    fun getPhotos(): Call<String>

}
