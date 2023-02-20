package com.example.ecommerce2.room_db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {


    @Insert
    suspend fun insertProduct(productModel: ProductModel)
    @Delete
    suspend fun deleteProduct(productModel: ProductModel)
    @Update
    suspend  fun updateProduct(productModel: ProductModel)


    @Query("SELECT * FROM products")
    fun getAllProduct():LiveData<List<ProductModel>>


    @Query("SELECT * FROM products WHERE productId=:id")
    suspend fun isExit(id:String):ProductModel


}