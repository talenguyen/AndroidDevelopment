package vn.tiki.android.collectionx.viewholder

import android.view.View
import android.widget.TextView
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collectionx.R

data class ErrorModel(val layoutId: Int, val text: String) : ListModel {

  var onClick: (() -> Unit)? = null

  private val key = "${ErrorModel::class.java.canonicalName}.$layoutId.$text"

  override fun getKey() = key

  @Suppress("UNCHECKED_CAST")
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return { ErrorViewHolderDelegate(layoutId) as ViewHolderDelegate<T> }
  }
}

inline fun MutableList<ListModel>.errorItem(layoutId: Int, text: String, initializer: ErrorModel.() -> Unit) {
  add(ErrorModel(layoutId, text).apply(initializer))
}

class ErrorViewHolderDelegate(private val layoutId: Int) : SimpleViewHolderDelegate<ErrorModel>() {

  private lateinit var errorMessageTextView: TextView

  override fun bindView(view: View) {
    super.bindView(view)
    errorMessageTextView = view.findViewById(R.id.errorMessageTextView)
    onClick(R.id.itemView) { model.onClick?.invoke() }
  }

  override fun layout(): Int = layoutId

  override fun bind(model: ErrorModel) {
    super.bind(model)
    errorMessageTextView.text = model.text
  }
}
