package com.example.ecommerce2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce2.R
import com.example.ecommerce2.adapter.ProductStatusAdapter
import com.example.ecommerce2.databinding.FragmentMoreBinding
import com.example.ecommerce2.model.ProductStatusModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MoreFragment : Fragment() {

    private lateinit var binding:FragmentMoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMoreBinding.inflate(layoutInflater)

        getAllOder()
        return  binding.root
    }

    private fun getAllOder() {

        val list=ArrayList<ProductStatusModel>()

        val preferences=requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        Firebase.firestore.collection("allOrders")
            .whereEqualTo("userId",preferences.getString("number","")!!)
            .get()
            .addOnSuccessListener {

                list.clear()

                for (data in it){

                    val mainData=data.toObject(ProductStatusModel::class.java)
                    list.add(mainData)
                }
                binding.recycleriew.adapter=ProductStatusAdapter(requireContext(),list)


            }.addOnFailureListener {

            }
    }


}