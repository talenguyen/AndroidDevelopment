package vn.tiki.android.collectionx.viewholder

import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collectionx.R

object None : ListModel {

  override fun getKey(): String {
    return "vn.tiki.android.collectionx.viewholder.None"
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return { NoneViewHolderDelegate() as ViewHolderDelegate<T> }
  }
}

class NoneViewHolderDelegate : SimpleViewHolderDelegate<None>() {
  override fun layout(): Int = R.layout.viewx_item_none
}
