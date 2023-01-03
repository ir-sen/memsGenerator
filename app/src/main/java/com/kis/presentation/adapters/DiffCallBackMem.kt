package com.kis.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.kis.classes.Meme

class DiffCallBackMem: DiffUtil.ItemCallback<Meme>(){

    override fun areItemsTheSame(oldItem: Meme, newItem: Meme): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Meme, newItem: Meme): Boolean {
        return oldItem == newItem
    }

}