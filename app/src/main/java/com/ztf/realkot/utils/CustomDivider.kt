package com.ztf.realkot.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author ztf
 * @date 2019/9/3
 */
class CustomDivider(
    context: Context, //
    private val mOrientation: Int
) : RecyclerView.ItemDecoration() {
    private var mPaint: Paint? = null
    private var mDivider: Drawable? = null
    private var mDividerHeight = 1
    private var mLeftOffset: Int = 0
    private var mRightOffset: Int = 0
    private var showLast: Boolean = false
    private var mDividerColor: Int = 0
    private var mStart = 0
    private var mEnd = 0

    init {

        require(!(mOrientation != LinearLayoutManager.VERTICAL && mOrientation != LinearLayoutManager.HORIZONTAL)) { "orientation error" }
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    constructor(
        context: Context,
        orientation: Int,
        dividerHeight: Int,
        dividerColor: Int
    ) : this(context, orientation) {

        mDividerColor = dividerColor
        mDividerHeight = dividerHeight

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = dividerColor
        mPaint!!.style = Paint.Style.FILL
    }

    constructor(context: Context, orientation: Int, drawableId: Int) : this(context, orientation) {
        mDivider = ContextCompat.getDrawable(context, drawableId)
        mDividerHeight = mDivider!!.intrinsicHeight
    }

    fun setHorizontalOffset(left: Int, right: Int) {
        mLeftOffset = left
        mRightOffset = right
    }

    fun setShowLast(showLast: Boolean) {
        this.showLast = showLast
    }

    fun setDividerStartAndEnd(start: Int, end: Int) {
        mStart = start
        mEnd = end
    }

    fun setmDividerHeight(height: Int) {
        mDividerHeight = height
    }

    fun setDividerColor(color: Int) {
        mDividerColor = color
        if (mPaint != null) {
            mPaint!!.color = color
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, mDividerHeight)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDivider(c, parent)
        } else {
            startDrawH(c, parent)
        }
    }

    private fun drawVerticalDivider(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + mLeftOffset
        val right = parent.measuredWidth - parent.paddingRight + mRightOffset
        val childSize = parent.childCount

        if (childSize <= 0) {
            return
        }
        val first = mStart
        val last = childSize - mEnd - if (showLast) 0 else 1
        if (last <= 0) {
            return
        }
        for (i in first until last) {
            startDrawV(canvas, parent, left, right, i, mDividerHeight)
        }
    }

    private fun startDrawV(canvas: Canvas, parent: RecyclerView, left: Int, right: Int, i: Int, height: Int) {
        val child = parent.getChildAt(i) ?: return

        val layoutParams = child.layoutParams as RecyclerView.LayoutParams
        val top = child.bottom + layoutParams.bottomMargin
        val bottom = top + height
        if (mDivider != null) {
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
        }
        if (mPaint != null) {
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint!!)
        }
    }

    private fun startDrawH(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.measuredHeight - parent.paddingBottom
        val childSize = parent.childCount
        for (i in 0..childSize) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + layoutParams.rightMargin
            val right = left + mDividerHeight

            if (mDivider != null) {
                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(canvas)
            }
            if (mPaint != null) {
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }
        }
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
        private val DIVIDER_COLOR = Color.parseColor("#efefef")
    }
}
