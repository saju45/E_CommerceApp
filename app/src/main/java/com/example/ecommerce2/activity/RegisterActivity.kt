package com.example.ecommerce2.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecommerce2.R
import com.example.ecommerce2.databinding.ActivityRegisterBinding
import com.example.ecommerce2.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {

            ValidateUser()

        }

        binding.loginBtn.setOnClickListener {
          OpenLogin()
        }
    }

    private fun ValidateUser() {

        val name:String=binding.nameEt.text.toString()
        val phone:String=binding.numberEt.text.toString()

        if (name.isEmpty()){
            binding.nameEt.error="please enter your name"
            binding.nameEt.requestFocus()
        }else if(phone.isEmpty()){
            binding.numberEt.error="Enter your number"
            binding.numberEt.requestFocus()
        }else{
            StoreData()
        }
    }

    private fun StoreData() {

        val dialog=AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait..")
            .setCancelable(false)
            .create()

        dialog.show()

        val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putString("name",binding.nameEt.text.toString())
        editor.putString("number",binding.numberEt.text.toString())
        editor.apply()

       val data=UserModel(userName = binding.nameEt.text.toString(), userPhoneNumber = binding.numberEt.text.toString())

        Firebase.firestore.collection("users")
            .document(binding.numberEt.text.toString())
            .set(data)
            .addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(this, "register is success", Toast.LENGTH_SHORT).show()
                OpenLogin()

            }.addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(this, "something want wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun OpenLogin(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}