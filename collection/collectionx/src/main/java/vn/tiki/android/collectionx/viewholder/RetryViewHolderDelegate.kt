package vn.tiki.android.collectionx.viewholder

import android.view.View
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collectionx.R

data class Retry(val layoutId: Int) : ListModel {

  var onClick: (() -> Unit)? = null

  private val key = "${Retry::class.java.canonicalName}.$layoutId"

  override fun getKey() = key

  @Suppress("UNCHECKED_CAST")
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return { RetryViewHolderDelegate(layoutId) as ViewHolderDelegate<T> }
  }
}

inline fun MutableList<ListModel>.retryItem(layoutId: Int, initializer: Retry.() -> Unit) {
  add(Retry(layoutId).apply(initializer))
}

class RetryViewHolderDelegate(private val layoutId: Int) : SimpleViewHolderDelegate<Retry>() {

  override fun bindView(view: View) {
    super.bindView(view)
    onClick(R.id.itemView) { model.onClick?.invoke() }
  }

  override fun layout(): Int = layoutId
}
