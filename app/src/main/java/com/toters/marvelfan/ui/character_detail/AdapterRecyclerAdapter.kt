package com.toters.marvelfan.ui.character_detail

import androidx.recyclerview.widget.DiffUtil
import com.toters.marvelfan.R
import com.toters.marvelfan.data.model.BaseModel
import com.toters.marvelfan.databinding.CharactersDetailsListItemBinding
import com.toters.marvelfan.ui.base.BaseRecyclerAdapter

class AdapterRecyclerAdapter(screenWidth: Int) :
    BaseRecyclerAdapter<BaseModel, CharactersDetailsListItemBinding>(screenWidth) {

    override fun DIFF_CALLBACK() = object : DiffUtil.ItemCallback<BaseModel>() {
        override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun onBindViewHolder(holder: Companion.BaseViewHolder<CharactersDetailsListItemBinding>, position: Int) {
        with(holder.binding) {
            root.rootView.setOnClickListener {
                val comicsItem = differ.currentList[position]
                listener?.invoke(comicsItem)
            }
            model = differ.currentList[position]
        }
    }

    override fun getLayout() = R.layout.characters_details_list_item

}
