package com.example.e2logypracticaltest

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("s/p57gxwqm84zkp96/demo_api.json")
    fun getStoreInfo(): Call<ApiResponse>
}
