package com.example.recipeapp

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CustomAdapterRecycleView(var itemModel: ArrayList<recipe>, var context: Context,
                               private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CustomAdapterRecycleView.MyViewHolder>(){


    interface OnItemClickListener {
        fun onUpdateClick(position: Int)

        fun onDeleteClick(position: Int)
        fun viewClick(position: Int)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipeitems, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.deleteBtn.setOnClickListener {
            listener.onDeleteClick(position)
        }

        holder.updateBtn.setOnClickListener {

            listener.onUpdateClick(position)
        }

        holder.viewBtn.setOnClickListener {

            listener.viewClick(position)

        }


        val item = itemModel[position]



        holder.bind(itemModel[position],context,position)

    }



    override fun getItemCount(): Int {
        return  itemModel.size
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.imageName)
//        val ingredients  : TextView = itemView.findViewById(R.id.ingredientsItems)
//        val steps : TextView = itemView.findViewById(R.id.stepsItems)
//        val recipeTypes: TextView =itemView.findViewById(R.id.recipeTypesitems)

        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val deleteBtn=itemView.findViewById<Button>(R.id.deleteBtn)
        val updateBtn=itemView.findViewById<Button>(R.id.updateBtn)
        val viewBtn=itemView.findViewById<Button>(R.id.viewBtn)



        fun bind(
            itemModel:recipe,

            context: Context,
            position: Int
        ) {

            recipeName?.text = itemModel.recipeName


//            tvImageName?.text=itemModel[position].docName

//            ingredients?.text=itemModel.ingredients
//
//
//            steps?.text = itemModel.step
////            val str= sendResult()
////            var i= sendPosition()
//
//            recipeTypes?.text = itemModel.recipeType
//



            val recipeName=itemModel.recipeName.toString()
            val cache= MyCache()
            val bit: Bitmap? =cache.retrieveBitmapFromCache(recipeName)
            itemModel.image=bit
            Glide.with(context)
                .load(bit)
                .into(imageView)







        }

    }








}