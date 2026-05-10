package com.example.minigross.ViewModel


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minigross.Model.DataClass.AddToCartRequest
import com.example.minigross.Model.DataClass.AddressData
import com.example.minigross.Model.DataClass.CartItemResponse
import com.example.minigross.Model.DataClass.Filtercat
import com.example.minigross.Model.DataClass.OrderItem
import com.example.minigross.Model.DataClass.OrderedProductResponse
import com.example.minigross.Model.DataClass.PlaceOrderRequest
import com.example.minigross.Model.DataClass.ProductDetDatacls
import com.example.minigross.Model.DataClass.UpdateQuantityRequest
import com.example.minigross.Model.DataClass.categorydatclss
import com.example.minigross.Retrofit.Productretro

import kotlinx.coroutines.launch
import kotlin.collections.map

class Producrviewmodel: ViewModel() {
    var products by mutableStateOf<List<ProductDetDatacls>>(emptyList())
        private set


    private var isLoaded = false

    fun fetchproducr() {
        if (isLoaded) return

        viewModelScope.launch {
            try {
                products = Productretro.PrdtApi.GetProductDetl()
                isLoaded = true

            }
            catch (e: Exception){
                Log.e("ErrorFetching", e.toString())
            }

        } }

    var productfilter by mutableStateOf<List<ProductDetDatacls>>(emptyList())

    fun applyfilter(category:List<String>, price:List<Int>){
        viewModelScope.launch {
            try {
                val request = Filtercat(category, price)
                val res = Productretro.PrdtApi.filterdatainter(request)
                Log.d("FILTERED_DATA", res.body().toString())
                if (res.isSuccessful){
                    val result = res.body() ?: emptyList()
                    Log.d("FILTERED_DATA", "Received: ${result.size} items")
                    productfilter = result
                }
                else{
                    Log.e("Error", res.message())
                }
            }catch (e:Exception){
                Log.e("Exception", e.toString())
            }
        }
    }



    var category by mutableStateOf<List<categorydatclss>>(emptyList())
        private set
    fun categoryviewmodel(){
        viewModelScope.launch {
            try {
                category = Productretro.PrdtApi.GetCategory()
            }catch (e: Exception){
                Log.e("ErrorFetching", e.toString())}}
    }



    var singleProduct by mutableStateOf<ProductDetDatacls?>(null)
        private set

    fun getSingleProduct(prodid: String){

        viewModelScope.launch {

            try {

                singleProduct =
                    Productretro.PrdtApi.getSingleProduct(prodid)

            } catch (e: Exception){

                Log.e("SingleProduct", e.toString())

            }

        }

    }





    var isAddedToCart by mutableStateOf(false)
        private set

    var isCartLoading by mutableStateOf(false)
        private set

    fun addToCart(
        phoneno: String,
        prodid: Int
    ){

        viewModelScope.launch {

            try {

                isCartLoading = true

                val response =
                    Productretro.PrdtApi.addToCart(
                        AddToCartRequest(
                            phoneno = phoneno,
                            prodid = prodid
                        )
                    )

                if(response.isSuccessful){

                    isAddedToCart = true

                }

            } catch (e: Exception){

                Log.e(
                    "AddToCart",
                    e.toString()
                )

            } finally {

                isCartLoading = false

            }

        }

    }




    var cartProducts by mutableStateOf<List<CartItemResponse>>(
        emptyList()
    )
        private set


    var isGetCartLoading by mutableStateOf(false)
        private set


    fun getCartProducts(
        phoneno: String
    ){

        viewModelScope.launch {

            try {

                isGetCartLoading = true

                cartProducts =
                    Productretro.PrdtApi
                        .getCartProducts(
                            phoneno
                        )

            } catch (e: Exception){

                Log.e(
                    "CartProducts",
                    e.toString()
                )

            } finally {

                isGetCartLoading = false

            }
        }
    }



    fun updateQuantity(
        phoneno: String,
        prodid: Int,
        quantity: Int
    ){

        viewModelScope.launch {

            try {

                // UPDATE UI FIRST
                cartProducts =
                    cartProducts.map {

                        if(
                            it.product.prodid == prodid
                        ){

                            it.copy(
                                quantity = quantity
                            )

                        } else it
                    }

                // UPDATE SERVER
                Productretro.PrdtApi.updateQuantity(
                    UpdateQuantityRequest(
                        phoneno = phoneno,
                        prodid = prodid,
                        quantity = quantity
                    )
                )

            } catch (e: Exception){

                Log.e(
                    "UpdateQuantity",
                    e.toString()
                )
            }
        }
    }

    fun removeCartItem(
        phoneno: String,
        prodid: Int
    ){

        viewModelScope.launch {

            try {

                cartProducts =
                    cartProducts.filter {

                        it.product.prodid != prodid
                    }

                Productretro.PrdtApi.removeCartItem(
                    phoneno,
                    prodid
                )

            } catch (e: Exception){

                Log.e(
                    "RemoveCartItem",
                    e.toString()
                )
            }
        }
    }




    var orderPlaced by mutableStateOf(false)
        private set


    var placeOrderLoading by mutableStateOf(false)
        private set
    fun placeOrder(

        phoneno: String,

        paymentMethod: String,

        address: AddressData,

        items: List<OrderItem>
    ){

        viewModelScope.launch {

            try {

                placeOrderLoading = true

                val response =

                    Productretro.PrdtApi.placeOrder(

                        PlaceOrderRequest(

                            phoneno = phoneno,

                            paymentMethod = paymentMethod,

                            address = address,

                            items = items
                        )
                    )

                if(response.isSuccessful){

                    orderPlaced = true

                    // REMOVE ORDERED ITEMS
                    val orderedIds =
                        items.map { it.prodid }

                    cartProducts =
                        cartProducts.filter {

                            it.product.prodid !in orderedIds
                        }
                }

            } catch (e: Exception){

                Log.e(
                    "PlaceOrder",
                    e.toString()
                )

            } finally {

                placeOrderLoading = false
            }
        }
    }



    var orders by mutableStateOf<List<OrderedProductResponse>>(
        emptyList()
    )
        private set

    var ordersLoading by mutableStateOf(false)
        private set

    fun getOrders(
        phoneno: String
    ){

        viewModelScope.launch {

            try {

                ordersLoading = true

                orders =
                    Productretro.PrdtApi
                        .getOrders(
                            phoneno
                        )

            } catch (e: Exception){

                Log.e(
                    "GetOrders",
                    e.toString()
                )

            } finally {

                ordersLoading = false
            }
        }
    }



    fun cancelOrder(
        orderid: String
    ){

        viewModelScope.launch {

            try {

                // REMOVE FROM UI
                orders =
                    orders.filter {

                        it._id != orderid
                    }

                // REMOVE FROM SERVER
                Productretro.PrdtApi
                    .cancelOrder(
                        orderid
                    )

            } catch (e: Exception){

                Log.e(
                    "CancelOrder",
                    e.toString()
                )
            }
        }
    }



}

