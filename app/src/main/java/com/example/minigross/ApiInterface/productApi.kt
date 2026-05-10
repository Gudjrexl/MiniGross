package com.example.minigross.ApiInterface

import com.example.minigross.Model.DataClass.AddToCartRequest
import com.example.minigross.Model.DataClass.CartItemResponse
import com.example.minigross.Model.DataClass.Filtercat
import com.example.minigross.Model.DataClass.OrderedProductResponse
import com.example.minigross.Model.DataClass.PlaceOrderRequest
import com.example.minigross.Model.DataClass.ProductDetDatacls
import com.example.minigross.Model.DataClass.UpdateQuantityRequest
import com.example.minigross.Model.DataClass.categorydatclss
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductDetApi {
    @GET("productdetails/productdet")
    suspend fun GetProductDetl(): List<ProductDetDatacls>

    @POST("productdetails/productfilter")
    suspend fun filterdatainter(@Body filter: Filtercat): Response<List<ProductDetDatacls>>

    @GET("category/category")
    suspend fun GetCategory(): List<categorydatclss>

    @GET("productdetails/productdet/{prodid}")
    suspend fun getSingleProduct(
        @Path("prodid") prodid: String
    ): ProductDetDatacls


    @POST("cart/addtocart")
    suspend fun addToCart(
        @Body request: AddToCartRequest
    ): Response<Unit>


    @GET("cart/getcartproducts/{phoneno}")
    suspend fun getCartProducts(
        @Path("phoneno") phoneno: String
    ): List<CartItemResponse>

    @PUT("cart/updatequantity")
    suspend fun updateQuantity(
        @Body request: UpdateQuantityRequest
    ): Response<Unit>


    @DELETE("cart/removeitem/{phoneno}/{prodid}")
    suspend fun removeCartItem(
        @Path("phoneno") phoneno: String,
        @Path("prodid") prodid: Int
    ): Response<Unit>


    @POST("cart/placeorder")
    suspend fun placeOrder(
        @Body request: PlaceOrderRequest
    ): Response<Unit>


    @GET("cart/getorders/{phoneno}")
    suspend fun getOrders(

        @Path("phoneno")
        phoneno: String

    ): List<OrderedProductResponse>

    @DELETE("cart/cancelorder/{orderid}")
    suspend fun cancelOrder(

        @Path("orderid")
        orderid: String

    ): Response<Unit>

    @GET("productdetails/search/{text}")
    suspend fun searchProducts(

        @Path("text")
        text: String

    ): List<ProductDetDatacls>

}