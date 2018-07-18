package vn.tiki.android.collection

import android.support.annotation.IdRes
import android.view.View

abstract class SimpleViewHolderDelegate<T : ListModel> : ViewHolderDelegate<T> {

  val itemView: View
    get() = _itemView
  val model: T
    get() = _model

  private lateinit var _itemView: View
  private lateinit var _model: T
  private val onClicks = mutableMapOf<Int, () -> Unit>()

  override fun bindView(view: View) {
    _itemView = view
  }

  override fun bind(model: T) {
    this._model = model
  }

  override fun unbind() {
    // ignored
  }

  override fun clickableIds(): IntArray {
    return if (onClicks.isEmpty()) {
      intArrayOf()
    } else {
      onClicks.keys.toIntArray()
    }
  }

  override fun onClick(@IdRes id: Int) {
    onClicks[id]?.invoke()
  }

  protected fun onClick(@IdRes id: Int, onClick: () -> Unit) {
    onClicks[id] = onClick
  }
}
