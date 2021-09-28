package com.example.cryptoapp_compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoapp_compose.model.CryptoListItem
import com.example.cryptoapp_compose.ui.theme.Shapes
import com.example.cryptoapp_compose.ui.theme.cloudColor
import com.example.cryptoapp_compose.ui.theme.darkPurple
import com.example.cryptoapp_compose.ui.theme.oldBlue
import com.example.cryptoapp_compose.viewmodel.CryptoListViewModel

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxSize()
    ) {

        Column {
            Text(
                text = "Crypto Crazy", modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = Center,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = darkPurple
            )

            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                hint = "Search ...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
                 {
                    viewModel.searchCryptoList(it)
                }
            Spacer(modifier = Modifier.height(5.dp))
            CryptoList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "true")
    }

    Box(modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(color = Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                },


            )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.DarkGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}


@Composable
fun CryptoRow(
    navController: NavController,
    crypto: CryptoListItem
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.secondary)
            .clickable {
                navController.navigate(
                    "crypto_detail_screen/${crypto.currency}/${crypto.price}"
                )
            }) {
        CryptoCard(crypto = crypto)
    }
}

@Composable
fun CryptoList(navController: NavController, viewModel: CryptoListViewModel = hiltViewModel()){
    val cryptoList by remember {
        viewModel.cryptoList
    }
    val errorMessage by remember {
        viewModel.errorMessage
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    
    CryptoListView(cryptos = cryptoList, navController = navController)
    
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
        }
        if(errorMessage.isNotEmpty()){
            RetryView(error = errorMessage) {
                viewModel.loadCryptos()
            }
        }
    }
}

@Composable
fun CryptoListView(cryptos: List<CryptoListItem>, navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(4.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){
        items(cryptos) { crypto ->
            CryptoRow(navController = navController, crypto = crypto )

        }
    }
}


@Composable
fun CryptoCard(crypto: CryptoListItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(120.dp)
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .shadow(4.dp, Shapes.large, true)
            .background(color = Color.White, shape = Shapes.large)



    ){
        Column(
            Modifier
                .wrapContentSize()
                .padding(8.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center){

            Text(text = crypto.currency,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = oldBlue)

            Text(text = crypto.price,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(2.dp),
                color = oldBlue)
        }

    }
}

@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
){
    Column() {
        Text(text = error, color= Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {}) {
            
        }
    }
}