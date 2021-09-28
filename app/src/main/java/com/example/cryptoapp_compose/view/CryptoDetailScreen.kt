package com.example.cryptoapp_compose.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.cryptoapp_compose.model.Crypto
import com.example.cryptoapp_compose.util.Resource
import com.example.cryptoapp_compose.viewmodel.CryptoDetailViewModel
import kotlinx.coroutines.launch


@Composable
fun CryptoDetailScreen(
    id: String,
    price: String,
    navController: NavController,
viewModel:CryptoDetailViewModel = hiltViewModel()
) {

   /* //Step 1 -> Wrong
    val scope = rememberCoroutineScope()

    var cryptoItem by remember {
        mutableStateOf<Resource<Crypto>>(Resource.Loading())
    }

    scope.launch {
        cryptoItem = viewModel.getCrypto(id)
        println(cryptoItem.data)
    }*/

    //Step 2 -> Better

 /*   var cryptoItem by remember {
        mutableStateOf<Resource<Crypto>>(Resource.Loading())
    }

    LaunchedEffect(key1 = Unit )
    {
        cryptoItem = viewModel.getCrypto(id)
        println(cryptoItem.data)
    }*/

    //Best way
    val cryptoItem = produceState<Resource<Crypto>>(initialValue = Resource.Loading())
    {
        value = viewModel.getCrypto(id)
    }.value


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.secondary),
        contentAlignment =  Alignment.Center
    )   {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(cryptoItem){
                is Resource.Success -> {
                    val selectedCrypto = cryptoItem.data!![0]

                    Text(text = selectedCrypto.name,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(2.dp),
                    fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = rememberImagePainter(
                            data = selectedCrypto.logo_url),
                        contentDescription = selectedCrypto.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(140.dp, 140.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray)

                    )

                    Text(text = selectedCrypto.name,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.Center)

                }

                is Resource.Error -> {
                    Text(text = cryptoItem.message!!)
                }

                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }

}