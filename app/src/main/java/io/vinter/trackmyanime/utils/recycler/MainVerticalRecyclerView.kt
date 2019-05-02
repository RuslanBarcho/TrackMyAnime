package io.vinter.trackmyanime.utils.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration

import java.util.Objects

open class MainVerticalRecyclerView : RecyclerView {

    private var scrollPointerId = -1
    private var pointTouchX = 0
    private var pointTouchY = 0
    private var touchSlopType = 0

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun setScrollingTouchSlop(slop: Int) {
        super.setScrollingTouchSlop(slop)
        val vc = ViewConfiguration.get(context)
        when (slop) {
            RecyclerView.TOUCH_SLOP_DEFAULT -> touchSlopType = vc.scaledTouchSlop
            RecyclerView.TOUCH_SLOP_PAGING -> touchSlopType = vc.scaledPagingTouchSlop
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (e == null) {
            return false
        }

        val action = e.actionMasked
        val actionIndex = e.actionIndex

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                scrollPointerId = e.getPointerId(0)
                pointTouchX = Math.round(e.x + 0.5f)
                pointTouchY = Math.round(e.y + 0.5f)
                return super.onInterceptTouchEvent(e)
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                scrollPointerId = e.getPointerId(actionIndex)
                pointTouchX = Math.round(e.getX(actionIndex) + 0.5f)
                pointTouchY = Math.round(e.getY(actionIndex) + 0.5f)
                return super.onInterceptTouchEvent(e)
            }

            MotionEvent.ACTION_MOVE -> {
                val index = e.findPointerIndex(scrollPointerId)
                if (index < 0) {
                    return false
                }

                val x = Math.round(e.getX(index) + 0.5f)
                val y = Math.round(e.getY(index) + 0.5f)
                if (scrollState != RecyclerView.SCROLL_STATE_DRAGGING) {
                    val dx = x - pointTouchX
                    val dy = y - pointTouchY
                    var startScroll = false
                    if (Objects.requireNonNull<LayoutManager>(layoutManager).canScrollHorizontally() && Math.abs(dx) > touchSlopType && (layoutManager!!.canScrollVertically() || Math.abs(dx) > Math.abs(dy))) {
                        startScroll = true
                    }
                    if (layoutManager!!.canScrollVertically() && Math.abs(dy) > touchSlopType && (layoutManager!!.canScrollHorizontally() || Math.abs(dy) > Math.abs(dx))) {
                        startScroll = true
                    }
                    return startScroll && super.onInterceptTouchEvent(e)
                }
                return super.onInterceptTouchEvent(e)
            }
            else -> {
                return super.onInterceptTouchEvent(e)
            }
        }
    }

}
