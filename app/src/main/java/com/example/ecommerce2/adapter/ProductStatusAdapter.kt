package com.example.ecommerce2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce2.R
import com.example.ecommerce2.databinding.ProductStatusItemLayoutBinding
import com.example.ecommerce2.model.ProductStatusModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductStatusAdapter(val context:Context, val list:ArrayList<ProductStatusModel>):RecyclerView.Adapter<ProductStatusAdapter.AllOderVideHolder>() {



    inner class AllOderVideHolder(view: View):RecyclerView.ViewHolder(view){

        val binding=ProductStatusItemLayoutBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOderVideHolder {

        return AllOderVideHolder(LayoutInflater.from(context).inflate(R.layout.product_status_item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: AllOderVideHolder, position: Int) {

        holder.binding.productName.text=list[position].name
        holder.binding.productPrice.text=list[position].price


        when(list[position].status){
            "ordered" ->{

                holder.binding.productStatus.text="Ordered"
            }
            "dispatched" ->{
                holder.binding.productStatus.text="Dispatched"
            }
            "delivered" ->{
                holder.binding.productStatus.text="Delivered"

            }
            "cancel" ->{
                holder.binding.productStatus.text="Cancel"

            }

        }



    }

    fun updateStatus(str:String,doc:String){

        val hashMap= hashMapOf<String,Any>()

        hashMap["status"]=str

        Firebase.firestore.collection("allOrders")
            .document(doc)
            .update(hashMap)
            .addOnSuccessListener {
                Toast.makeText(context, "status updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}