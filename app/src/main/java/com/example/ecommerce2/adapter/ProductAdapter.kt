package com.example.ecommerce2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce2.R
import com.example.ecommerce2.activity.ProductDetailsActivity
import com.example.ecommerce2.databinding.ProductLayoutBinding
import com.example.ecommerce2.model.AddProductModel

class ProductAdapter(val context: Context,val list:ArrayList<AddProductModel>):
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view:View): RecyclerView.ViewHolder(view){

        val binding=ProductLayoutBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        return ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.binding.productName.text=list[position].productName
        holder.binding.productCategory.text=list[position].productCategory
        holder.binding.productPrice.text=list[position].productMrp

        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.image)
        holder.itemView.setOnClickListener {

            val intent= Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}