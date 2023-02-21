package com.example.ecommerce2.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ecommerce2.activity.AddressActivity
import com.example.ecommerce2.adapter.CardAdapter
import com.example.ecommerce2.databinding.FragmentCardBinding
import com.example.ecommerce2.room_db.AppDatabase
import com.example.ecommerce2.room_db.ProductModel


class CardFragment : Fragment() {

    private lateinit var binding:FragmentCardBinding
    private lateinit var list:ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCardBinding.inflate(layoutInflater)

        val preferences=requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val edit=preferences.edit()
        edit.putBoolean("isCard",true)
        edit.apply()


        getCardData()

        return binding.root
    }

    private fun getCardData() {

        val dao=AppDatabase.getInstance(requireContext()).productDao()

        list=ArrayList()

        dao.getAllProduct().observe(requireActivity(), Observer {
            binding.cardRv.adapter=CardAdapter(requireContext(),it)

            list.clear()
            for(data in it){
                list.add(data.productId)
            }
            totalCoast(it)
        })
    }

    private fun totalCoast(data: List<ProductModel>?) {
        var total=0
        for (item in data!!){

            total+= item.productSp!!.toInt()
        }

        binding.totalItem.text="Total item in card is ${data.size}"
        binding.totalPrice.text="Total Coast : $total"

        binding.checkout.setOnClickListener {
            val intent=Intent(requireContext(), AddressActivity::class.java)

            val b=Bundle()
            b.putString("totalCost",total.toString())
            b.putStringArrayList("productIds",list)
            intent.putExtras(b)
           // intent.putExtra("totalCost",total.toString())
           // intent.putStringArrayListExtra("productIds",list)
            startActivity(intent)
        }

    }

}