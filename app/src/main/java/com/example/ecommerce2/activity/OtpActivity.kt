package com.example.ecommerce2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerce2.MainActivity
import com.example.ecommerce2.R
import com.example.ecommerce2.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.verifyBtn.setOnClickListener {

            if(binding.otpEt.text.toString().isEmpty()){

                binding.otpEt.error="please enter your otp"
                binding.otpEt.requestFocus()
            }else{
                verifyUser(binding.otpEt.text.toString())
            }
        }
    }

    private fun verifyUser(otp: String) {

        val credential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!,otp)
        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    startActivity(Intent(this,MainActivity::class.java))
                    val user = task.result?.user
                } else {

                    Toast.makeText(this, "something want wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }

}