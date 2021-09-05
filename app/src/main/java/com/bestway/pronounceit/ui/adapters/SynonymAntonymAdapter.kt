package com.bestway.pronounceit.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bestway.pronounceit.databinding.ItemTextBinding

class SynonymAntonymAdapter : ListAdapter<String, SynonymAntonymViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SynonymAntonymViewHolder {
        val binding = ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SynonymAntonymViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SynonymAntonymViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    }
}