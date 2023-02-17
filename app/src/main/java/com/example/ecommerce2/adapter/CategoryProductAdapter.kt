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
import com.example.ecommerce2.databinding.CategoryProductLayoutBinding
import com.example.ecommerce2.model.AddProductModel

class CategoryProductAdapter(val context:Context,val list:ArrayList<AddProductModel>)
    : RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>() {

    inner class CategoryProductViewHolder(view: View):RecyclerView.ViewHolder(view){

        val binding=CategoryProductLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {

        return CategoryProductViewHolder(LayoutInflater.from(context).inflate(R.layout.category_product_layout,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {

        val data=list[position]

        Glide.with(context).load(data.productCoverImg).into(holder.binding.image)
        holder.binding.productName.text=data.productName
        holder.binding.productSp.text=data.productSp

        holder.itemView.setOnClickListener {

            val intent=Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("id",data.productId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }
}