package com.bestway.pronounceit.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bestway.pronounceit.databinding.ItemTextBinding

class SynonymAntonymViewHolder(private val binding: ItemTextBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        binding.apply {
            tvSynonymOrAntonym.text = "- $item"
        }
    }
}