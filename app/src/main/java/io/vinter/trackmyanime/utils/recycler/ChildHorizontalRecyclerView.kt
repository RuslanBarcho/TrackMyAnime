package io.vinter.trackmyanime.utils.recycler

import android.content.Context
import android.util.AttributeSet

import io.vinter.trackmyanime.utils.recycler.MainVerticalRecyclerView

class ChildHorizontalRecyclerView : MainVerticalRecyclerView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}
