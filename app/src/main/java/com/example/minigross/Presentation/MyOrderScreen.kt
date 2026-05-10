package com.example.minigross.Presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.minigross.Model.DataClass.OrderItem
import com.example.minigross.Model.DataStoreManager
import com.example.minigross.Retrofit.Constf
import com.example.minigross.ViewModel.Producrviewmodel

@Composable
fun MyOrderScreen(
    prodid: String,
    navController: NavController,
    vm: Producrviewmodel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var phoneNumber by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        vm.getSingleProduct(prodid)
        phoneNumber =
            DataStoreManager
                .getUserPhone(context)
                ?: ""
    }


    val product = vm.singleProduct

    if (product == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    val imageList = listOf(
        Constf.BASE_URL + product.Productimg,
        Constf.BASE_URL + product.Productimg,
        Constf.BASE_URL + product.Productimg
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),
                Color.White  )))
            .statusBarsPadding()) {

        // IMAGE SECTION
        val pagerState = rememberPagerState(
            pageCount = {
                imageList.size
            }
        )

        HorizontalPager(
            state = pagerState,

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.40f)
        ) { page ->

            AsyncImage(
                model = imageList[page],

                contentDescription = null,

                contentScale = ContentScale.Fit,

                modifier = Modifier.fillMaxSize()
            )
        }

        // PRODUCT NAME
        Text(
            text = product.Prodtname,

            style = MaterialTheme.typography.headlineSmall,

            fontWeight = FontWeight.Bold,

            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp
            )
        )

        // PRICE
        Text(
            text = "₹ ${product.Prodtprice}",

            style = MaterialTheme.typography.titleLarge,

            modifier = Modifier.padding(
                start = 16.dp,
                top = 8.dp
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ONLY DESCRIPTION SCROLLABLE
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {

            item {

                Text(
                    text = product.Prodtdescription,

                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // BOTTOM BUTTONS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp)
                .navigationBarsPadding(),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {

                    vm.addToCart(
                        phoneno = phoneNumber,
                        prodid = product.prodid
                    )

                },

                enabled = !vm.isAddedToCart,

                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (vm.isAddedToCart)
                            Color.Gray
                        else
                            Color.Blue
                ),

                shape = RoundedCornerShape(12.dp)
            ) {

                Text(
                    text =
                        if (vm.isAddedToCart)
                            "Added"
                        else
                            "Add To Cart",

                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {

                    val items =
                        "${product.prodid}:1"

                    navController.navigate(
                        "buynow/$items"
                    )
                },

                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                ),

                shape = RoundedCornerShape(12.dp)
            ) {

                Text(
                    text = "Buy Now",

                    color = Color.Black
                )
            }
        }
    }
}