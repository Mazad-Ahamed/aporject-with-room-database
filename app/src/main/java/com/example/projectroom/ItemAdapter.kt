package com.example.projectroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectroom.databinding.ItemListBinding

class ItemAdapter(
    private var items: List<ItemEntity>,
    private val onEditClick: (ItemEntity) -> Unit,
    private val onDeleteClick: (ItemEntity) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding : ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvTitle.text = item.title
            tvDescription.text = item.description
            btnEdit.setOnClickListener { onEditClick(item) }
            btnDelete.setOnClickListener { onDeleteClick(item) }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<ItemEntity>) {
        items = newList
        notifyDataSetChanged()
    }
}
