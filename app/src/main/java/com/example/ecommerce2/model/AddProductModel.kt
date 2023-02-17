package com.example.ecommerce2.model

data class AddProductModel(
    val productName:String?="",
    val productDescription:String?="",
    val productCoverImg:String?="",
    val productCategory:String?="",
    val productMrp:String?="",
    val productId:String?="",
    val productSp:String?="",
    val productImages:ArrayList<String> =ArrayList()
)
