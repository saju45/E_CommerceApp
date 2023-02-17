package com.example.ecommerce2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce2.R
import com.example.ecommerce2.activity.CategoryActivity
import com.example.ecommerce2.databinding.CategoryLayoutBinding
import com.example.ecommerce2.model.CategoryModel

class CategoryAdapter(private val context: Context, private val list: ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view:View): RecyclerView.ViewHolder(view) {

        val binding=CategoryLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val view=LayoutInflater.from(context).inflate(R.layout.category_layout,parent,false)

        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {


        holder.binding.productName.text=list[position].cate
       Glide.with(context).load(list[position].img).into(holder.binding.image)

        holder.itemView.setOnClickListener {

            val intent=Intent(context,CategoryActivity::class.java)
            intent.putExtra("category",list[position].cate)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return list.size
    }
}