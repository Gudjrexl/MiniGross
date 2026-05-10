package com.example.minigross.Presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.minigross.Model.DataClass.AddressData
import com.example.minigross.Model.DataClass.OrderItem
import com.example.minigross.Model.DataStoreManager
import com.example.minigross.ViewModel.Producrviewmodel

@Composable
fun BuyNowScreen(

    productList: List<OrderItem>,
    navController: NavController,

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
    }

    // ADDRESS
    var name by remember {
        mutableStateOf("")
    }

    var phone by remember {
        mutableStateOf("")
    }

    var houseNo by remember {
        mutableStateOf("")
    }

    var area by remember {
        mutableStateOf("")
    }

    var state by remember {
        mutableStateOf("")
    }

    var pincode by remember {
        mutableStateOf("")
    }

    // PAYMENT
    var paymentMethod by remember {
        mutableStateOf("COD")
    }
    if(vm.orderPlaced){

        AlertDialog(

            onDismissRequest = {},

            title = {

                Text(
                    text = "Order Successful"
                )
            },

            text = {

                Text(
                    text =
                        "Your order has been placed successfully."
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        navController.navigate("home"){

                            popUpTo(0)
                        }
                    }
                ) {

                    Text("Done")
                }
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),
                Color.White  )))
            .statusBarsPadding()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp)
            .navigationBarsPadding()
    ) {

        // ADDRESS TITLE
        Text(
            text = "Delivery Address",

            style =
                MaterialTheme.typography.headlineSmall,

            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        // NAME
        OutlinedTextField(
            value = name,

            onValueChange = {
                name = it
            },

            label = {
                Text("Full Name")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // PHONE
        OutlinedTextField(
            value = phone,

            onValueChange = {
                phone = it
            },

            label = {
                Text("Phone Number")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // HOUSE
        OutlinedTextField(
            value = houseNo,

            onValueChange = {
                houseNo = it
            },

            label = {
                Text("House No.")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // AREA
        OutlinedTextField(
            value = area,

            onValueChange = {
                area = it
            },

            label = {
                Text("Area / Street")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // STATE
        OutlinedTextField(
            value = state,

            onValueChange = {
                state = it
            },

            label = {
                Text("State")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // COUNTRY
        OutlinedTextField(
            value = "India",

            onValueChange = {},

            enabled = false,

            label = {
                Text("Country")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // PINCODE
        OutlinedTextField(
            value = pincode,

            onValueChange = {
                pincode = it
            },

            label = {
                Text("Pincode")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // PAYMENT TITLE
        Text(
            text = "Payment Method",

            style =
                MaterialTheme.typography.titleLarge,

            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // COD
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected =
                        paymentMethod == "COD",

                    onClick = {

                        paymentMethod = "COD"
                    }
                ),

            horizontalArrangement =
                Arrangement.Start
        ) {

            RadioButton(
                selected =
                    paymentMethod == "COD",

                onClick = {

                    paymentMethod = "COD"
                }
            )

            Text(
                text = "Cash On Delivery",

                modifier = Modifier.padding(top = 12.dp)
            )
        }

        // ONLINE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected =
                        paymentMethod == "ONLINE",

                    onClick = {

                        paymentMethod = "ONLINE"

                        Toast
                            .makeText(
                                context,
                                "Coming Soon",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                )
        ) {

            RadioButton(
                selected =
                    paymentMethod == "ONLINE",

                onClick = {

                    paymentMethod = "ONLINE"

                    Toast
                        .makeText(
                            context,
                            "Coming Soon",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            )

            Text(
                text = "Online Payment",

                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        // PLACE ORDER BUTTON
        if(paymentMethod == "COD"){

            Button(
                onClick = {

                    vm.placeOrder(

                        phoneno = phoneNumber,

                        paymentMethod = "COD",

                        address = AddressData(

                            name = name,

                            phone = phone,

                            houseno = houseNo,

                            area = area,

                            state = state,

                            pincode = pincode
                        ),

                        items = productList
                    )

                    Toast
                        .makeText(
                            context,
                            "Order Placed",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF4CAF50)
                    )
            ) {

                if(vm.placeOrderLoading){

                    CircularProgressIndicator(
                        color = Color.White
                    )

                } else {

                    Text(
                        text = "Place Order",

                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}