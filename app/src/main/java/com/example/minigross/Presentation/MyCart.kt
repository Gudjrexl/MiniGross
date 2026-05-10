package com.example.minigross.Presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.minigross.Model.DataStoreManager
import com.example.minigross.Retrofit.Constf
import com.example.minigross.ViewModel.Producrviewmodel

@Composable
fun MyCartScreen(
    navController: NavHostController,
    vm: Producrviewmodel = viewModel()
) {

    val context = LocalContext.current

    var phoneNumber by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        phoneNumber =
            DataStoreManager
                .getUserPhone(context)
                ?: ""

        if(phoneNumber.isNotEmpty()){

            vm.getCartProducts(phoneNumber)
        }
    }

    val cartItems = vm.cartProducts

    val deliveryCharge = 40
    val handlingFee = 20

    val subtotal =
        cartItems.sumOf {

            it.product.Prodtprice * it.quantity
        }

    val total =
        subtotal + deliveryCharge + handlingFee

    if(vm.isCartLoading){

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){

            CircularProgressIndicator()
        }

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),
                Color.White  )))
            .statusBarsPadding()) {

        // TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 5.dp
                ),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "My Cart",

                fontWeight = FontWeight.Bold,

                fontSize = 20.sp
            )
        }

        if(cartItems.isEmpty()){

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){

                Text(
                    text = "No items in cart",

                    fontSize = 22.sp,

                    fontWeight = FontWeight.Bold
                )
            }

            return
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            // CART ITEMS
            items(cartItems) { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ),

                    shape = RoundedCornerShape(14.dp)
                ) {


                    Column(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFB3E5FC),
                                        Color.White
                                    )
                                )
                            )
                    ) {

                        Row(
                            modifier = Modifier.padding(12.dp)
                        ) {



                        AsyncImage(
                            model =
                                Constf.BASE_URL +
                                        item.product.Productimg,

                            contentDescription = null,

                            modifier = Modifier
                                .size(110.dp),

                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text =
                                    item.product.Prodtname,

                                style =
                                    MaterialTheme.typography.titleMedium,

                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text =
                                    "₹ ${item.product.Prodtprice}",

                                style =
                                    MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // QUANTITY
                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Button(
                                    onClick = {

                                        if(item.quantity > 1){

                                            vm.updateQuantity(
                                                phoneno = phoneNumber,
                                                prodid = item.product.prodid,
                                                quantity = item.quantity - 1
                                            )
                                        }
                                    },

                                    enabled = item.quantity > 1,

                                    modifier = Modifier.size(
                                        width = 40.dp,
                                        height = 36.dp
                                    ),

                                    contentPadding =
                                        androidx.compose.foundation.layout.PaddingValues(
                                            0.dp
                                        )
                                ) {

                                    Text("-")
                                }

                                Text(
                                    text = "  ${item.quantity}  ",

                                    fontWeight = FontWeight.Bold
                                )

                                Button(
                                    onClick = {

                                        vm.updateQuantity(
                                            phoneno = phoneNumber,
                                            prodid = item.product.prodid,
                                            quantity = item.quantity + 1
                                        )
                                    },

                                    modifier = Modifier.size(
                                        width = 40.dp,
                                        height = 36.dp
                                    ),

                                    contentPadding =
                                        androidx.compose.foundation.layout.PaddingValues(
                                            0.dp
                                        )
                                ) {

                                    Text("+")
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // REMOVE BUTTON
                            Button(
                                onClick = {

                                    vm.removeCartItem(
                                        phoneno = phoneNumber,
                                        prodid = item.product.prodid
                                    )
                                },

                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor = Color.Red
                                    ),

                                shape = RoundedCornerShape(10.dp),

                                modifier = Modifier.height(34.dp)
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,

                                    tint = Color.White,

                                    modifier = Modifier.size(16.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = "Remove",

                                    color = Color.White,

                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }}

            // BILL SUMMARY
            item {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),

                    shape = RoundedCornerShape(16.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFB3E5FC),
                                        Color.White
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {

                        Text(
                            text = "Bill Summary",

                            fontWeight = FontWeight.Bold,

                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        cartItems.forEach { item ->

                            Row(
                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text =
                                        "${item.product.Prodtname} x${item.quantity}"
                                )

                                Text(
                                    text =
                                        "₹ ${
                                            item.product.Prodtprice *
                                                    item.quantity
                                        }"
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text("Delivery Charge")

                            Text("₹ $deliveryCharge")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text("Handling Fee")

                            Text("₹ $handlingFee")
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Total Amount",

                                fontWeight = FontWeight.Bold,

                                fontSize = 18.sp
                            )

                            Text(
                                text = "₹ $total",

                                fontWeight = FontWeight.Bold,

                                fontSize = 18.sp
                            )
                        }





                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .navigationBarsPadding()
                )
            }



        }


        Button(
            onClick = {

                val items =

                    cartItems.joinToString(",") {

                        "${it.product.prodid}:${it.quantity}"
                    }

                navController.navigate(
                    "buynow/$items"
                )


            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

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