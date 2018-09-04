package vn.tiki.android.collection.sample.viewholder

import android.view.View
import android.widget.TextView
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collection.sample.R

data class UserModel(val name: String) : ListModel {

  var onClick: (() -> Unit)? = null

  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return {
      @Suppress("UNCHECKED_CAST")
      UserViewHolderDelegate() as ViewHolderDelegate<T>
    }
  }

  override fun getKey(): String {
    return "vn.tiki.android.collection.sample.viewholder.UserModel$name"
  }
}

inline fun MutableList<ListModel>.userItem(name: String, initializer: UserModel.() -> Unit) {
  add(UserModel(name).apply(initializer))
}

class UserViewHolderDelegate : SimpleViewHolderDelegate<UserModel>() {

  private lateinit var nameTextView: TextView

  override fun bindView(view: View) {
    super.bindView(view)
    nameTextView = view.findViewById(R.id.nameTextView)
    onClick(R.id.itemView) {
      model.onClick?.invoke()
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
