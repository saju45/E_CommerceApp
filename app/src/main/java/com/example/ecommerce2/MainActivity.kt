package com.example.ecommerce2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ecommerce2.activity.LoginActivity
import com.example.ecommerce2.databinding.ActivityMainBinding
import com.example.ecommerce2.fragment.CardFragment
import com.example.ecommerce2.fragment.HomeFragment
import com.example.ecommerce2.fragment.MoreFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(FirebaseAuth.getInstance().currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

   /*     val intent=Intent()
        val one =intent.getStringExtra("one")*/

        val one=intent.getStringExtra("one").toString()

     if (one.equals("1")){
         replace(CardFragment())
        // openCardFragment()

     }else{
         replace(HomeFragment())

     }




     binding.bottomBar.setOnItemSelectedListener {
         if (it==0){
             replace(HomeFragment())
             this.title="My DashBoard"
         }else if (it==1){
             replace(CardFragment())
             this.title="Cart"
         }else{
             replace(MoreFragment())
             this.title="Profile"
         }
     }

    }

    private fun openCardFragment() {
        val preferences=this.getSharedPreferences("info", MODE_PRIVATE )
        val open=preferences.getBoolean("isCard",false)

        if (open==true ){
            replace(CardFragment())
        }
    }

    fun replace(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
}