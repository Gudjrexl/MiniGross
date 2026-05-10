package com.example.minigross.Presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.minigross.Navigation.Screen
import com.example.minigross.ViewModel.UserInfoVm
import com.example.minigross.R


@Composable
fun Login(    navController: NavHostController,
              vm: UserInfoVm = viewModel()) {
    var context = LocalContext.current
    var phone by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var showotp by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(brush = Brush.linearGradient(colors = listOf(
            Color(0xFFE1BEE7),
            Color(0xFFD1C4E9)
        ))).windowInsetsPadding(WindowInsets.statusBars).padding(top = 5.dp, start = 4.dp, end = 4.dp)){

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.login)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "logo",
                modifier = Modifier.size(350.dp)
            )


            Text(text = "Enter your mobile",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(listOf(Color(0xFF6A11CB), Color(0xFF2575FC)))
                ),
                modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                label = { Text("Phone") },
                singleLine = true,
                leadingIcon = {Text("+91")},
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, bottom = 5.dp)


            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                Log.d("phone", phone)
                if (phone.length == 10){
                    isLoading = true
                    val phonen = "+91" + phone
                    showotp = true
                    vm.sendOtp(phonen){ res->
                        Log.d("reuest sent", res)
                        isLoading = false
                        if(res == "sucess"){
                            Log.d("otp sent", "otp sent")
                        }else{
                            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{

                    Toast.makeText(context, "not invalid phone number", Toast.LENGTH_LONG).show()


                }

            },

                Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(20.dp)).padding(end = 10.dp, start = 10.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.Blue.copy(0.5f),
                    containerColor = Color.Red.copy(0.6f))) {
                Text("Send otp")
            }


            if (showotp){
                Text(text = "Enter Otp",
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        brush = Brush.linearGradient(listOf(Color(0xFF6A11CB), Color(0xFF2575FC)))
                    ),
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp))

                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text("Enter Otp") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, bottom = 5.dp)

                )

                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    isLoading = true
                    val phonen = "+91" + phone.trim()
                    vm.verifyOtp(context, phonen, otp) { res ->
                        isLoading = false
                        if (res == "success") {

                            navController.navigate("home")

                        }



                    }

                },
                    Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(20.dp)).padding(end = 10.dp, start = 10.dp),
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Blue.copy(0.5f),
                        containerColor = Color.Red.copy(0.6f))
                ) {
                    Text("Login")
                }

                LoadingOverlay(isLoading)


        }
    }



}}



@Composable
fun LoadingOverlay(isLoading: Boolean, message: String = "Please wait...") {
    if (isLoading) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0x80000000)), // semi-transparent background
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                androidx.compose.material3.CircularProgressIndicator(color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))
                androidx.compose.material3.Text(
                    text = message,
                    color = Color.White
                )
            }
        }

    }
}
