package com.irfanirawansukirman.extensions.widget

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.irfanirawansukirman.extensions.R
import com.irfanirawansukirman.extensions.util.DividerItemDecoration
import com.irfanirawansukirman.extensions.util.ItemOffsetDecoration

fun RecyclerView.setLinearList(
    orientation: Int = RecyclerView.VERTICAL,
    isReverse: Boolean = false,
    withDecoration: Boolean = false
) {
    layoutManager = LinearLayoutManager(context, orientation, isReverse)
    removeItemDecorations()
    if (withDecoration) {
        addItemDecoration(
            DividerItemDecoration(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_divider_gray
                ), true, false
            )
        )
    }
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    isNestedScrollingEnabled = false
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = android.view.View.DRAWING_CACHE_QUALITY_HIGH
}

fun RecyclerView.setGridList(
    numberOfColumns: Int = 2,
    itemSpacing: Int = 8.toPx(),
    withDecoration: Boolean = false,
    showFirstDecoration: Boolean = false,
    showLastDecoration: Boolean = false
) {
    layoutManager = GridLayoutManager(context, numberOfColumns)
    removeItemDecorations()
    if (withDecoration) {
        addItemDecoration(
            ItemOffsetDecoration(
                itemSpacing.toPx()
            )
        )
    }
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    isNestedScrollingEnabled = false
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = android.view.View.DRAWING_CACHE_QUALITY_HIGH
}

fun RecyclerView.setStaggeredList(
    numberOfColumns: Int,
    orientation: Int = StaggeredGridLayoutManager.VERTICAL
) {
    layoutManager = StaggeredGridLayoutManager(numberOfColumns, orientation)
    itemAnimator = DefaultItemAnimator()
    isNestedScrollingEnabled = false
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = android.view.View.DRAWING_CACHE_QUALITY_HIGH
}

/**
 * @param columnWidth - in dp
 */
fun RecyclerView.setfitColumnsGrid(
    columnWidth: Int
): Int {
    val displayMetrics = context.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / columnWidth).toInt()
}

fun <T : RecyclerView> T.removeItemDecorations() {
    while (itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
}