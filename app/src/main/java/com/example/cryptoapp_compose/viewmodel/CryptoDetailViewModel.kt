package com.example.cryptoapp_compose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoapp_compose.model.Crypto
import com.example.cryptoapp_compose.repository.CryptoRepository
import com.example.cryptoapp_compose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    suspend fun getCrypto(id: String) : Resource<Crypto> {
        return repository.getCrypto(id)
    }
}