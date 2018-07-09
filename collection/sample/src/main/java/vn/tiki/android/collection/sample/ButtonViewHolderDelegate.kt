package vn.tiki.android.collection.sample

import android.view.View
import android.widget.Button
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate

data class ButtonModel(val text: String, val onClick: () -> Unit) : ListModel {
  override fun getKey(): String {
    return "ButtonModel $text"
  }

  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return {
      @Suppress("UNCHECKED_CAST")
      ButtonViewHolderDelegate() as ViewHolderDelegate<T>
    }
  }
}

class ButtonViewHolderDelegate : SimpleViewHolderDelegate<ButtonModel>() {
  private lateinit var button: Button
  override fun layout(): Int {
    return R.layout.item_button
  }

  override fun bindView(view: View) {
    super.bindView(view)
    button = view.findViewById(R.id.itemView)
    onClick(R.id.itemView) {
      model.onClick()
    }
  }

  override fun bind(model: ButtonModel) {
    super.bind(model)
    button.text = model.text
  }
}
