package com.example.ecommerce2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ecommerce2.MainActivity
import com.example.ecommerce2.databinding.ActivityCheckOutBinding
import com.example.ecommerce2.room_db.AppDatabase
import com.example.ecommerce2.room_db.ProductModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckOutActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCheckOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val price=intent.getStringExtra("totalCost")
      // val ids=intent.getStringArrayListExtra("productIds")

        binding.priceEt.setText("$price")
        binding.oderBtn.setOnClickListener {
            uploadData()
        }

    }

    fun uploadData(){

        val id=intent.getStringArrayListExtra("productIds")

        for (currentId in id!!){

            fetchData(currentId)

        }

    }

    private fun fetchData(currentId: String?) {


        val dao=AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("product")
            .document(currentId!!)
            .get()
            .addOnSuccessListener {

                lifecycleScope.launch(Dispatchers.IO){
                    dao.deleteProduct(ProductModel(productId = currentId))
                }
                saveData(it.getString("productName"),
                it.getString("productSp"),
                currentId)

            }.addOnFailureListener {

            }

    }

    private fun saveData(name: String?, productSp: String?, currentId: String) {

        val preferences=this.getSharedPreferences("user", MODE_PRIVATE)

        val data= hashMapOf<String,Any>()
        data["name"]=name!!
        data["price"]=productSp!!
        data["productId"]=currentId!!
        data["status"]="ordered"
        data["userId"]=preferences.getString("number","")!!


        val firestore=Firebase.firestore.collection("allOrders")
        val key=firestore.document().id
        data["orderId"]=key

        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this, "Order placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
        }.addOnFailureListener {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        }

    }
}