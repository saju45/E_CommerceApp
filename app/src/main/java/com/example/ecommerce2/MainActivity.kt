package com.example.ecommerce2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ecommerce2.databinding.ActivityMainBinding
import com.example.ecommerce2.fragment.CardFragment
import com.example.ecommerce2.fragment.HomeFragment
import com.example.ecommerce2.fragment.MoreFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replace(HomeFragment())

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

    fun replace(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
}