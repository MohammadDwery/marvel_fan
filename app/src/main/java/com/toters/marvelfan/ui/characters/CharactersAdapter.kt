package com.toters.marvelfan.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.toters.marvelfan.data.model.CharacterModel
import com.toters.marvelfan.databinding.CharacterListItemBinding

class CharactersAdapter:
    PagingDataAdapter<CharacterModel, CharactersAdapter.CharacterViewHolder>(CharacterComparator) {
    var characterClickListener: CharacterClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterViewHolder(
            CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class CharacterViewHolder(private val binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                characterClickListener?.onCharacterClicked(
                    binding,
                    getItem(absoluteAdapterPosition) as CharacterModel
                )
            }
        }

        fun bind(item: CharacterModel) = with(binding) {
            ViewCompat.setTransitionName(binding.characterImageView, "image_${item.id}")
            ViewCompat.setTransitionName(binding.nameCard, "name_${item.id}")
            character = item
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel) =
            oldItem == newItem
    }


    interface CharacterClickListener {
        fun onCharacterClicked(binding: CharacterListItemBinding, character: CharacterModel)
    }
}