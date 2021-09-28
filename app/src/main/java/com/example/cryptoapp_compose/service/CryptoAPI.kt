package com.example.cryptoapp_compose.service

import com.example.cryptoapp_compose.model.Crypto
import com.example.cryptoapp_compose.model.CryptoList
import com.example.cryptoapp_compose.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.jar.Attributes

interface CryptoAPI {

    @GET("prices")
    suspend fun getCryptoList(
        @Query("key") key : String
    ) : CryptoList


    @GET("currencies")
    suspend fun getCrypto(
        @Query("key") key:String,
        @Query("ids") id: String,
        @Query("attributes") attributes: String
    ): Crypto
}