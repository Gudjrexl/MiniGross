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
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImage
import com.example.minigross.Model.DataStoreManager
import com.example.minigross.Retrofit.Constf
import com.example.minigross.ViewModel.Producrviewmodel

@Composable
fun orderedproduct(

    vm: Producrviewmodel = viewModel()
) {

    val context = LocalContext.current

    var phoneNumber by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){

        phoneNumber =
            DataStoreManager
                .getUserPhone(context)
                ?: ""

        if(phoneNumber.isNotEmpty()){

            vm.getOrders(phoneNumber)
        }
    }

    val orders = vm.orders

    if(vm.ordersLoading){

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){

            CircularProgressIndicator()
        }

        return
    }

    if(orders.isEmpty()){

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){

            Text(
                text = "No Orders Yet",

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold
            )
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
                    vertical = 2.dp
                ),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "My Orders",

                fontWeight = FontWeight.Bold,

                fontSize = 20.sp
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(orders){ order ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        ),

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
                            .padding(14.dp)
                    ) {

                        // ORDER ITEMS
                        order.items.forEach { item ->

                            Row {

                                AsyncImage(
                                    model =
                                        Constf.BASE_URL +
                                                item.image,

                                    contentDescription = null,

                                    modifier = Modifier
                                        .size(100.dp),

                                    contentScale =
                                        ContentScale.Crop
                                )

                                Spacer(
                                    modifier =
                                        Modifier.width(12.dp)
                                )

                                Column(
                                    modifier =
                                        Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = item.name,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleMedium,

                                        fontWeight =
                                            FontWeight.Bold
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(6.dp)
                                    )

                                    Text(
                                        text =
                                            "Qty : ${item.quantity}"
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(4.dp)
                                    )

                                    Text(
                                        text =
                                            "₹ ${item.price}"
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(4.dp)
                                    )

                                    Text(
                                        text =
                                            "Total : ₹ ${item.total}",

                                        fontWeight =
                                            FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            HorizontalDivider()

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )
                        }

                        // TOTAL BILL
                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Total Amount",

                                fontWeight =
                                    FontWeight.Bold
                            )

                            Text(
                                text =
                                    "₹ ${order.bill.totalAmount}",

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }


                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "Payment : ${order.paymentMethod}"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )

                        Text(
                            text =
                                "Status : ${order.orderStatus}"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )

                        Text(
                            text =
                                "Delivery : ${order.deliveryDate}"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )

                        // CANCEL BUTTON
                        Button(

                            onClick = {

                                vm.cancelOrder(
                                    order._id
                                )
                            },

                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        Color.Red
                                ),

                            modifier =
                                Modifier.fillMaxWidth()
                        ) {

                            Text(
                                text = "Cancel Order",

                                color = Color.White
                            )
                        }
                    }
                }
            }

            item {

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}