package vn.tiki.android.collection.sample

import android.view.View
import android.widget.TextView
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate

data class TextModel(val text: String) : ListModel {

  override fun getKey(): String {
    return "Text $text"
  }

  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return {
      @Suppress("UNCHECKED_CAST")
      TextViewHolderDelegate() as ViewHolderDelegate<T>
    }
  }
}

class TextViewHolderDelegate : SimpleViewHolderDelegate<TextModel>() {
  private lateinit var textView: TextView

  override fun layout(): Int {
    return android.R.layout.simple_list_item_1
  }

  override fun bindView(view: View) {
    super.bindView(view)
    textView = view as TextView
  }

  override fun bind(model: TextModel) {
    super.bind(model)
    textView.text = model.text
  }
}
