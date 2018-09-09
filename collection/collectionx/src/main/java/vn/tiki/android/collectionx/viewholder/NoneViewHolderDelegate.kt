package vn.tiki.android.collectionx.viewholder

import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collectionx.R

object NoneModel : ListModel {

  private val key = NoneModel::class.java.canonicalName

  override fun getKey(): String = key

  @Suppress("UNCHECKED_CAST")
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return { NoneViewHolderDelegate() as ViewHolderDelegate<T> }
  }
}

class NoneViewHolderDelegate : SimpleViewHolderDelegate<NoneModel>() {
  override fun layout(): Int = R.layout.viewx_item_none
}
