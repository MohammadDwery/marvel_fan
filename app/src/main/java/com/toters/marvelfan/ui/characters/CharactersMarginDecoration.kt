package com.toters.marvelfan.ui.characters

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class CharactersMarginDecoration(context: Context, @DimenRes resId: Int) : RecyclerView.ItemDecoration() {

    private val margin = context.resources.getDimensionPixelSize(resId)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val doubleMargin = margin * 2
        val halfMargin = margin / 2

        if (itemPosition == RecyclerView.NO_POSITION) return

        val lastPosition = state.itemCount - 1

        when {
            itemPosition%2 == 0 -> outRect.set(margin, halfMargin, halfMargin, halfMargin)
            else -> outRect.set(halfMargin, halfMargin, margin, halfMargin)
        }
    }
}
