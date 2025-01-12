package com.example.binlistapp2.data

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BinInfo
}