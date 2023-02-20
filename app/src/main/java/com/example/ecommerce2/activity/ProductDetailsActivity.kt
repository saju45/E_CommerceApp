package com.example.ecommerce2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.ecommerce2.MainActivity
import com.example.ecommerce2.databinding.ActivityProductDetailsBinding
import com.example.ecommerce2.room_db.AppDatabase
import com.example.ecommerce2.room_db.ProductDao
import com.example.ecommerce2.room_db.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                val name=it.getString("productName")
                val productSp=it.getString("productSp")
                val coverImg:String = it.get("productCoverImg") as String
                
                binding.name.text=name
                binding.description.text=it.getString("productDescription")
                binding.sp.text=productSp

                val slideList=ArrayList<SlideModel>()
                for (data in list){

                    slideList.add(SlideModel(data,ScaleTypes.FIT))
                }


               cardAction(productId,name,productSp, coverImg )

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener {

            }

    }

    private fun cardAction(productId: String, name: String?, productSp: String?, coverImg: String? ) {


       val productDao=AppDatabase.getInstance(this).productDao()

        lifecycleScope.launch(Dispatchers.IO){
            if (productDao.isExit(productId)!=null){

                binding.addToCard.text="Go to Card"
            }else{
                binding.addToCard.text="Add to Card"

            }
        }


        binding.addToCard.setOnClickListener {


            lifecycleScope.launch(Dispatchers.IO){
                if (productDao.isExit(productId)!=null){

                    OpenCard()
                }else{

                    AddToCard(productDao,productId,name,productSp,coverImg)
                }
            }


        }
    }

    private fun AddToCard(
        productDao: ProductDao,
        productId: String,
        name: String?,
        productSp: String?,
        coverImg: String?)
    {

        val data=ProductModel(productId,name,coverImg,productSp)
        lifecycleScope.launch (Dispatchers.IO){
            productDao.insertProduct(data)
            binding.addToCard.text="Go to Card"

        }


    }

    private fun OpenCard() {

        val preferences=this.getSharedPreferences("info", MODE_PRIVATE )
        val edit=preferences.edit()
        edit.putBoolean("isCard",true)
        edit.apply()

        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra("one","1")
        startActivity(intent)
        finish()

    }


}