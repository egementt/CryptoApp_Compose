package com.example.cryptoapp_compose.repository

import com.example.cryptoapp_compose.model.Crypto
import com.example.cryptoapp_compose.model.CryptoList
import com.example.cryptoapp_compose.service.CryptoAPI
import com.example.cryptoapp_compose.util.Constants.API_KEY
import com.example.cryptoapp_compose.util.Constants.CALL_ATTRIBUTES
import com.example.cryptoapp_compose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api : CryptoAPI
){
    suspend fun getCryptoList() : Resource<CryptoList> {
        val response = try {
            api.getCryptoList(API_KEY)
        }catch (e: Exception){
            return Resource.Error(e.message ?: "Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(id: String) : Resource<Crypto> {
        val response = try {
           api.getCrypto(API_KEY, id, CALL_ATTRIBUTES)
        }catch (e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}