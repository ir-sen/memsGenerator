package com.kis.presentation.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kis.R
import org.w3c.dom.Text

class MemeViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.mem_img)
    val description = view.findViewById<TextView>(R.id.meme_descript_tv)
}