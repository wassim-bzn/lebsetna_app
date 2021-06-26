package com.example.pfe

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter (val context: Context, val contacts:List<Habits>): RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.post_item,parent,false)


        return Holder(view)
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(context,contacts[position])
    }

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){

        val call_ic = itemView.findViewById<ImageView>(R.id.call_ic)
        fun bindView(context: Context, contact: Habits){

            call_ic.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:"+contact.telephone)
                context.startActivity(dialIntent)
            }
        }
    }




}

