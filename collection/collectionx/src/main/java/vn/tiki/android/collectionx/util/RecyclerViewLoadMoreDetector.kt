package vn.tiki.android.collectionx.util

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener

class RecyclerViewLoadMoreDetector(private val callback: () -> Unit) : OnScrollListener() {

  override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)
    if (dx > 0 && !recyclerView!!.canScrollHorizontally(1)) {
      callback()
    }

    if (dy > 0 && !recyclerView!!.canScrollVertically(1)) {
      callback()
    }
  }
}
