package vn.tiki.android.collectionx.viewholder

import android.view.View
import android.widget.TextView
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collectionx.R

data class Error(val layoutId: Int, val text: String, val onClick: () -> Unit) : ListModel {

  override fun getKey(): String {
    return "vn.tiki.android.collectionx.viewholder.Error:$text"
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return { ErrorViewHolderDelegate(layoutId) as ViewHolderDelegate<T> }
  }
}

class ErrorViewHolderDelegate(private val layoutId: Int) : SimpleViewHolderDelegate<Error>() {

  private lateinit var errorMessageTextView: TextView

  override fun bindView(view: View) {
    super.bindView(view)
    errorMessageTextView = view.findViewById(R.id.errorMessageTextView)
    onClick(R.id.itemView) { model.onClick() }
  }

  override fun layout(): Int = layoutId

  override fun bind(model: Error) {
    super.bind(model)
    errorMessageTextView.text = model.text
  }
}
