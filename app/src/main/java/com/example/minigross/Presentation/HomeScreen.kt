package com.example.minigross.Presentation

import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.minigross.Model.DataClass.ProductDetDatacls
import com.example.minigross.R
import com.example.minigross.Retrofit.Constf
import com.example.minigross.ViewModel.Producrviewmodel
import com.example.minigross.ViewModel.SearchViewModel


@Composable
fun HomeScreen(navController: NavHostController, vm: Producrviewmodel = viewModel(),
               searchVm: SearchViewModel = viewModel()
) {
    var searchText by remember { mutableStateOf("") }

    var showSheet by remember { mutableStateOf(false) }


    LaunchedEffect(Unit){
        vm.fetchproducr()
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),
            Color.White  )))
        .statusBarsPadding()
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp,top= 8.dp)) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {

                searchText = it

                searchVm.searchProduct(it)
            },
            placeholder = { Text("Search...",  fontSize = 11.sp, modifier = Modifier.offset(x= -8.dp,y= -4.dp)) },

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            shape = RoundedCornerShape(60.dp),
            modifier = Modifier.weight(10f)
                .height(48.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            })
        Spacer(modifier = Modifier.size(20.dp))
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .height(48.dp)
                .width(48.dp)
                .background(Color.White, shape = CircleShape)
            ,
        ) {
            IconButton(
                onClick = {
                    showSheet = true
                },
                modifier = Modifier

                    .height(48.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.filterproduct),
                    contentDescription = "Support Image",
                    modifier = Modifier.size(24.dp)
                )
            }
        }



        if (showSheet) {
            FilterBottomSheetWithSections(
                onDismiss = { showSheet = false },
                onApply = { showSheet = false }
            )
        }

    }

        Spacer(modifier = Modifier.size(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(1.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {

            val productList =

                when {

                    searchText.isNotEmpty() -> {

                        searchVm.searchProducts
                    }

                    vm.productfilter.isNotEmpty() -> {

                        vm.productfilter
                    }

                    else -> {

                        vm.products
                    }
                }

                    .filter {

                        !it.Prodtname.isNullOrEmpty()
                    }
            items(productList.chunked(2)) { ritm ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        ProductCard(ritm[0],
                            navController = navController
                        )
                    }

                    if (ritm.size > 1) {
                        Box(modifier = Modifier.weight(1f)) {
                            ProductCard(ritm[1],
                                navController = navController
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }




    }

}



@Composable
fun ProductCard(product: ProductDetDatacls,
                navController: NavHostController
) {

    val imagePath = product.Productimg ?: ""
    val hardcodedImageUrl = Constf.BASE_URL + imagePath

    if (product.Prodtname.isNullOrEmpty()){
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(4.dp)
            .clickable {

                navController.navigate(
                    "myorder/${product.prodid}"
                )

            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(7.dp)
        ) {

            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(hardcodedImageUrl)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),

                    contentDescription = "Product Image",

                    contentScale = ContentScale.Crop,

                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.Prodtname.orEmpty(),

                style = MaterialTheme.typography.titleMedium,

                maxLines = 1,

                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "${product.Prodtprice ?: 0}")
            Spacer(modifier = Modifier.height(2.dp))


            Text(
                text = product.Prodtdescription.orEmpty(),

                maxLines = 2,

                overflow = TextOverflow.Ellipsis
            )

        }
    }
}






