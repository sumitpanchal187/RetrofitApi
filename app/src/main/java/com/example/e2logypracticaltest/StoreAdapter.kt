package com.example.e2logypracticaltest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StoreAdapter(
    private val context: Context,
    private val storeList: List<Store>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(store: Store)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.store_image)
        val titleTextView: TextView = view.findViewById(R.id.store_title)
        val descriptionTextView: TextView = view.findViewById(R.id.store_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.store_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = storeList[position]
        holder.titleTextView.text = store.title
        holder.descriptionTextView.text = store.description

        Glide.with(holder.itemView.context)
            .load(store.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            listener.onItemClick(store)
        }
    }

    override fun getItemCount(): Int = storeList.size
}
