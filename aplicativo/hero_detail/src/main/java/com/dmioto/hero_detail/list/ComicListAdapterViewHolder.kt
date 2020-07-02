package com.dmioto.hero_detail.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.content_hero_item.view.*

class ComicListAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val title: TextView = view.comic_title
    val photo: SimpleDraweeView = view.comic_photo

}