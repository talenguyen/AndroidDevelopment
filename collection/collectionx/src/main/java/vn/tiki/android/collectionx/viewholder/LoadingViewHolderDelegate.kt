@file:Suppress("NOTHING_TO_INLINE")

package vn.tiki.android.collectionx.viewholder

import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate

data class Loading(private val layoutId: Int) : ListModel {

  var onClick: (() -> Unit)? = null

  private val key = "${Loading::class.java.canonicalName}.$layoutId"

  override fun getKey() = key

  @Suppress("UNCHECKED_CAST")
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return { LoadingViewHolderDelegate(layoutId) as ViewHolderDelegate<T> }
  }
}

inline fun MutableList<ListModel>.loadingItem(layoutId: Int) {
  add(Loading(layoutId))
}

class LoadingViewHolderDelegate(private val layoutId: Int) : SimpleViewHolderDelegate<Loading>() {
  override fun layout(): Int = layoutId
}
