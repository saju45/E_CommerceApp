package com.example.ecommerce2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.ecommerce2.databinding.ActivityProductDetailsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProductDetails(intent.getStringExtra("id"))
        
    }

    private fun getProductDetails(productId: String?) {

        Firebase.firestore.collection("product")
            .document(productId!!)
            .get()
            .addOnSuccessListener {

                val list=it.get("productImages") as ArrayList<String>
                binding.name.text=it.getString("productName")
                binding.description.text=it.getString("productDescription")
                binding.sp.text=it.getString("productSp")

                val slideList=ArrayList<SlideModel>()
                for (data in list){

                    slideList.add(SlideModel(data,ScaleTypes.FIT))
                }

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener {

            }

    }
}