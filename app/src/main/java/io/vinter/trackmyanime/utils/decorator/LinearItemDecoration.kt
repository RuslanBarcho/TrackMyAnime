package io.vinter.trackmyanime.utils.decorator

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View

class LinearItemDecoration(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int) : this(context.resources.getDimensionPixelSize(itemOffsetId))

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildLayoutPosition(view) == 0){
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset)
        } else {
            outRect.set(0, mItemOffset, mItemOffset, mItemOffset)
        }
    }
}
