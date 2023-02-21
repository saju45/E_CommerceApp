package com.example.ecommerce2.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.ecommerce2.R
import com.example.ecommerce2.databinding.ActivityAddressBinding
import com.example.ecommerce2.model.UserModel
import com.google.apphosting.datastore.testing.DatastoreTestTrace.FirestoreV1Action.ListCollectionIds
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddressBinding
    private lateinit var preferences:SharedPreferences
    private lateinit var totalPrice:String
    private lateinit var ids:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

         totalPrice= intent.getStringExtra("totalCost")!!
         ids= intent.getStringArrayListExtra("productIds")!!

       preferences =this.getSharedPreferences("user", MODE_PRIVATE)
        loadUserInfo()

        binding.checkout.setOnClickListener {
            ValidateUser()
        }

    }

    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .get()
            .addOnSuccessListener {

                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.userCity.setText(it.getString("city"))
                binding.userState.setText(it.getString("state"))
                binding.userPinCode.setText(it.getString("pinCode"))

            }.addOnFailureListener {

            }
    }

    private fun ValidateUser() {

        if(binding.userName.text.toString().isEmpty()){
            binding.userName.error="Enter your name"
            binding.userName.requestFocus()
        }else if (binding.userNumber.text.toString().isEmpty()){
            binding.userNumber.error="enter your number"
            binding.userNumber.requestFocus()
        }else if (binding.userVillage.text.toString().isEmpty()){
            binding.userVillage.error="please enter your village"
            binding.userVillage.requestFocus()
        }else if(binding.userCity.text.toString().isEmpty()){
            binding.userCity.error="enter you city"
            binding.userCity.requestFocus()
        }else if(binding.userState.text.toString().isEmpty()){
            binding.userState.error="enter state"
            binding.userState.requestFocus()
        }else if(binding.userPinCode.text.toString().isEmpty()){
            binding.userPinCode.error="enter pinCode"
            binding.userPinCode.requestFocus()
        }else{
            storeData()
        }

    }

    private fun storeData() {


        val map= mutableMapOf<String,Any>()
        map["village"]=binding.userVillage.text.toString()
        map["city"]=binding.userCity.text.toString()
        map["state"]=binding.userState.text.toString()
        map["pinCode"]=binding.userPinCode.text.toString()

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map)
            .addOnSuccessListener {

                val intent=Intent(this, CheckOutActivity::class.java)
                val b=Bundle()
                b.putString("totalCost",totalPrice)
                b.putStringArrayList("productIds",ids)
                intent.putExtras(b)
               // intent.putExtra("totalCost",intent.getStringExtra("totalCost"))
              //  intent.putExtra("productIds",intent.getStringArrayExtra("productIds"))
                startActivity(intent)
            }.addOnFailureListener {

                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            }
    }
}