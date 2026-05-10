package com.example.minigross.Model.DataClass


data class ProductDetDatacls(
    val prodid: Int,
    val Prodtname: String,
    val Prodtdescription: String,
    val Prodtprice: Int,
    val Prodtcategory: String,
    val Productimg : String,
)

data class Filtercat(
    val Prodtcategory: List<String>,
    val Prodtprice: List<Int>
)

data class OfferDetDatacls(
    val offername: String,
    val offerdescription: String,
    val offerprice: Int,
    val Offerimg : String,
)

data class categorydatclss( val name: String, val ctimg: String)

data class AddToCartRequest(
    val phoneno: String,
    val prodid: Int
)


data class CartItemResponse(
    val quantity: Int,
    val product: ProductDetDatacls
)


data class UpdateQuantityRequest(
    val phoneno: String,
    val prodid: Int,
    val quantity: Int
)


data class AddressData(

    val name: String,

    val phone: String,

    val houseno: String,

    val area: String,

    val state: String,

    val country: String = "India",

    val pincode: String
)

data class OrderItem(

    val prodid: Int,

    val quantity: Int
)

data class PlaceOrderRequest(

    val phoneno: String,

    val paymentMethod: String,

    val address: AddressData,

    val items: List<OrderItem>
)


data class OrderedItem(

    val prodid: Int,

    val name: String,

    val image: String,

    val price: Int,

    val quantity: Int,

    val total: Int
)

data class BillData(

    val subtotal: Int,

    val deliveryCharge: Int,

    val handlingFee: Int,

    val totalAmount: Int
)


data class OrderedProductResponse(

    val _id: String,

    val phoneno: String,

    val items: List<OrderedItem>,

    val bill: BillData,

    val paymentMethod: String,

    val orderStatus: String,

    val deliveryDate: String
)