package com.example.ecommerce2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecommerce2.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginBtn.setOnClickListener {

            if(binding.numberEt.text.toString().isEmpty()){
                Toast.makeText(this, "please enter your number", Toast.LENGTH_SHORT).show()
            }else{
               sendOtp(binding.numberEt.text.toString())
            }

        }

        binding.registerBtn.setOnClickListener {

            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }

    private fun sendOtp(number: String) {

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+88$number")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

         dialog= AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait..")
            .setCancelable(false)
            .create()

        dialog.show()
    }
   val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            dialog.dismiss()

        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            dialog.dismiss()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            val intent= Intent(applicationContext,OtpActivity::class.java)
            intent.putExtra("verificationId",verificationId)
            intent.putExtra("number",binding.numberEt.text.toString())
            startActivity(intent)

            dialog.dismiss()

        }
    }
}