package com.dmioto.hero_detail.list

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmioto.hero_detail.R
import com.dmioto.request_api.models.Comic

class ComicListAdapter (private val context: Context, private val comicList: MutableList<Comic>): RecyclerView.Adapter<ComicListAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicListAdapterViewHolder {
        return ComicListAdapterViewHolder(
            LayoutInflater.from(context).inflate(R.layout.content_hero_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return comicList.size
    }

    override fun onBindViewHolder(holder: ComicListAdapterViewHolder, position: Int) {
        comicList[position].run {
            holder.title.text = this.title
            holder.photo.setImageURI(Uri.parse(this.thumbnail.fullUrl), context)
        }
    }

}