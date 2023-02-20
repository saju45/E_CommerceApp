package com.example.ecommerce2.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductModel::class], version = 1)
abstract class AppDatabase:RoomDatabase() {


    abstract fun productDao():ProductDao


    companion object{
        private var database:AppDatabase?=null
        private val DATABASE_NAME="e_commerce"


        @Synchronized
        fun getInstance(context: Context):AppDatabase{
           if (database==null){
               database= Room.databaseBuilder(
                   context.applicationContext,
               AppDatabase::class.java,
               DATABASE_NAME).build()
           }
               return database!!
        }
    }

}