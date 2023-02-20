package com.example.ecommerce2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce2.R
import com.example.ecommerce2.activity.ProductDetailsActivity
import com.example.ecommerce2.databinding.CardLayoutItemBinding
import com.example.ecommerce2.room_db.AppDatabase
import com.example.ecommerce2.room_db.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CardAdapter(val context: Context,val list:List<ProductModel>)
    :RecyclerView.Adapter<CardAdapter.CardViewHolder>(){

    inner class CardViewHolder(view:View):RecyclerView.ViewHolder(view){

        val binding=CardLayoutItemBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        return CardViewHolder(LayoutInflater.from(context).inflate(R.layout.card_layout_item,parent,false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {


        Glide.with(context).load(list[position].productImage).into(holder.binding.image)
        holder.binding.productName.text=list[position].productName
        holder.binding.productSp.text=list[position].productSp


        holder.itemView.setOnClickListener {

            val intent=Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

        val dao=AppDatabase.getInstance(context).productDao()
        holder.binding.deleteImg.setOnClickListener {

            GlobalScope.launch (Dispatchers.IO){

                dao.deleteProduct(ProductModel(list[position].productId,list[position].productName,list[position].productImage,list[position].productSp))
            }
        }

    }

    override fun getItemCount(): Int {

        return list.size
    }
}