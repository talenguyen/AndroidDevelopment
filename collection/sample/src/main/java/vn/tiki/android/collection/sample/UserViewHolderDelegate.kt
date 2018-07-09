package vn.tiki.android.collection.sample

import android.view.View
import android.widget.TextView
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate

data class UserModel(val name: String, val onClick: () -> Unit) : ListModel {
  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return {
      @Suppress("UNCHECKED_CAST")
      UserViewHolderDelegate() as ViewHolderDelegate<T>
    }
  }

  override fun getKey(): String {
    return "UserModel $name"
  }
}

class UserViewHolderDelegate : SimpleViewHolderDelegate<UserModel>() {

  private lateinit var nameTextView: TextView

  override fun bindView(view: View) {
    super.bindView(view)
    nameTextView = view.findViewById(R.id.nameTextView)
    onClick(R.id.itemView) {
      model.onClick()
    }
  }

  override fun layout(): Int {
    return R.layout.item_text_image
  }

  override fun bind(model: UserModel) {
    super.bind(model)
    nameTextView.text = model.name
  }
}
