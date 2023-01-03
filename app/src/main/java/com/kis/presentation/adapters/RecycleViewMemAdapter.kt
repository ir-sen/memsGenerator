package com.kis.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.kis.R
import com.kis.classes.Meme
import com.squareup.picasso.Picasso

class RecycleViewMemAdapter: androidx.recyclerview.widget.ListAdapter<Meme, MemeViewHolder>(
    DiffCallBackMem()
) {


    var onItemClickListener: ((Meme) -> Unit)? = null

    // hold view and inflate the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_mem_recycle,
            parent,
            false
        )
        return MemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val memItem = getItem(position)
        openImage(holder.view.context, memItem.url, holder.image)
        holder.description.text = memItem.name
        holder.view.setOnClickListener {
            onItemClickListener?.invoke(memItem)
        }
    }

    private fun openImage(context: Context, load: String, view: ImageView) {
        Picasso.with(context)
            .load(load)
            .into(view)
    }


}