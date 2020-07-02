package com.dmioto.desafio_android_daniel_mioto.heroes_list.list

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dmioto.desafio_android_daniel_mioto.heroes_list.R
import kotlinx.android.synthetic.main.heroes_list_item.view.*


interface ItemClickListener {
    fun onItemClick(position: Int)
}


class CardHolder (inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.heroes_list_item, parent, false)){

    fun bind(name: String, thumbnail: String){

        Uri.parse(thumbnail).let {
            Glide.with(itemView.context).load(it).into(itemView.character_image)
        }
        itemView.character_name.text = name
    }

    fun onClick(itemClickListener: ItemClickListener) {
        itemView.character_image.setOnClickListener {
            itemClickListener.onItemClick(adapterPosition)
        }
    }
}