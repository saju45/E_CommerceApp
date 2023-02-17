package com.example.ecommerce2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.ecommerce2.R
import com.example.ecommerce2.adapter.CategoryAdapter
import com.example.ecommerce2.adapter.ProductAdapter
import com.example.ecommerce2.databinding.FragmentHomeBinding
import com.example.ecommerce2.model.AddProductModel
import com.example.ecommerce2.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(layoutInflater)

        getSlider()
        getData()
       getProducts()


        return binding.root
    }

    private fun getSlider() {


        Firebase.firestore
            .collection("slider")
            .document("item")
            .get()
            .addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.imageView)
            }

    }

    private fun getProducts() {

        val list= ArrayList<AddProductModel>()
        Firebase.firestore.collection("product")
            .get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data=doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }

                binding.productRv.adapter=ProductAdapter(requireContext(),list)


            }.addOnFailureListener {

            }
    }

    private fun getData() {

        val list= ArrayList<CategoryModel>()
        Firebase.firestore.collection("category")
            .get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data=doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }

                binding.categoryRv.adapter= CategoryAdapter(requireContext(),list)


            }.addOnFailureListener {

            }
    }


}