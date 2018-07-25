package vn.tiki.android.collection.sample.viewholder

import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collection.sample.R

data class TextFieldModel(val id: String, val error: String) : ListModel {

  var onUsernameChanged: ((String) -> Unit)? = null

  override fun getKey(): String {
    return "TextField $id"
  }

  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return {
      @Suppress("UNCHECKED_CAST")
      TextFieldViewHolderDelegate() as ViewHolderDelegate<T>
    }
  }
}

class TextFieldViewHolderDelegate : SimpleViewHolderDelegate<TextFieldModel>() {
  private lateinit var usernameLayout: TextInputLayout
  private lateinit var usernameEditText: EditText

  override fun layout(): Int {
    return R.layout.item_text_field
  }

  override fun bindView(view: View) {
    super.bindView(view)
    usernameLayout = view.findViewById(R.id.usernameTextInputLayout)
    usernameEditText = view.findViewById(R.id.usernameTextInput)
    usernameEditText.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        model.onUsernameChanged?.invoke(s?.toString() ?: "")
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }
    })
  }

  override fun bind(model: TextFieldModel) {
    super.bind(model)
    usernameLayout.error = model.error
  }
}
