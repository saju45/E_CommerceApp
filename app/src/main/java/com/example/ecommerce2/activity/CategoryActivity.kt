package com.example.ecommerce2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerce2.R
import com.example.ecommerce2.adapter.CategoryProductAdapter
import com.example.ecommerce2.adapter.ProductAdapter
import com.example.ecommerce2.databinding.ActivityCategoryBinding
import com.example.ecommerce2.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getProduct(intent.getStringExtra("category")!!)

    }

    private fun getProduct(category: String) {

        val list= ArrayList<AddProductModel>()
        Firebase.firestore.collection("product").whereEqualTo("productCategory",category)
            .get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data=doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }

                binding.categoryProductRv.adapter= CategoryProductAdapter(this,list)


            }.addOnFailureListener {

            }
    }

}