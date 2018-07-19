package vn.tiki.android.githubsample.base.viewholder

import android.support.annotation.IdRes
import android.view.View
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import kotlin.LazyThreadSafetyMode.NONE

abstract class BaseViewHolderDelegate<T : ListModel> : SimpleViewHolderDelegate<T>()

fun <V : View> BaseViewHolderDelegate<*>.view(@IdRes id: Int): Lazy<V> = lazy(NONE) {
  itemView.findViewById<V>(id)
}
