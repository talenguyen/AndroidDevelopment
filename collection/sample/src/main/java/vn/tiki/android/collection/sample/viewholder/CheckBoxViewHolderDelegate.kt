package vn.tiki.android.collection.sample.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collection.sample.R

data class CheckBoxModel(val id: Int, val isChecked: Boolean, val text: String) : ListModel {

  var onClick: ((CheckBoxModel) -> Unit)? = null
  override fun getKey(): String {
    return "vn.tiki.android.collection.sample.viewholder_$id"
  }

  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return {
      @Suppress("UNCHECKED_CAST")
      CheckBoxViewHolderDelegate() as ViewHolderDelegate<T>
    }
  }
}

class CheckBoxViewHolderDelegate : SimpleViewHolderDelegate<CheckBoxModel>() {
  private lateinit var nameTextView: TextView
  private lateinit var checkboxImageView: ImageView

  override fun layout(): Int {
    return R.layout.item_check_box
  }

  override fun bindView(view: View) {
    super.bindView(view)
    onClick(R.id.itemView) {
      model.onClick?.invoke(model)
    }
    nameTextView = view.findViewById(R.id.nameTextView)
    checkboxImageView = view.findViewById(R.id.checkboxImageView)
  }

  override fun bind(model: CheckBoxModel) {
    super.bind(model)
    nameTextView.text = model.text
    if (model.isChecked) {
      checkboxImageView.setImageResource(R.drawable.ic_check_box_black_24dp)
    } else {
      checkboxImageView.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp)
    }
  }
}
