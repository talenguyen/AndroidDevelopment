package vn.tiki.android.collectionx.viewholder

import android.view.View
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collectionx.R

data class Retry(val layoutId: Int, val onClick: () -> Unit) : ListModel {

  override fun getKey(): String {
    return "vn.tiki.android.collectionx.viewholder.Retry:$onClick"
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return { RetryViewHolderDelegate(layoutId) as ViewHolderDelegate<T> }
  }
}

class RetryViewHolderDelegate(private val layoutId: Int) : SimpleViewHolderDelegate<Retry>() {

  override fun bindView(view: View) {
    super.bindView(view)
    onClick(R.id.itemView) { model.onClick() }
  }

  override fun layout(): Int = layoutId
}
