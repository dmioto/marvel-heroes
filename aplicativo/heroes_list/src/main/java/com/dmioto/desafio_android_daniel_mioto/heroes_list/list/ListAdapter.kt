package com.dmioto.desafio_android_daniel_mioto.heroes_list.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmioto.request_api.models.Character


class ListAdapter(private val heroListAll: MutableList<Character>): RecyclerView.Adapter<CardHolder>() {

    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return heroListAll.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(heroListAll[position].name, heroListAll[position].thumbnail.fullUrl)
        holder.onClick(itemClickListener!!)
    }

    fun setOnItemClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }
}