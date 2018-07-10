package vn.tiki.android.collectionx.viewholder

import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate

data class Loading(private val layoutId: Int) : ListModel {

  override fun getKey(): String {
    return "vn.tiki.android.collectionx.viewholder.Loading$layoutId"
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return { LoadingViewHolderDelegate(layoutId) as ViewHolderDelegate<T> }
  }
}

class LoadingViewHolderDelegate(private val layoutId: Int) : SimpleViewHolderDelegate<Loading>() {
  override fun layout(): Int = layoutId
}
