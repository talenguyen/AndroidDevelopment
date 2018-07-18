package vn.tiki.android.collection

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup

class OnlyViewHolder<T : ListModel> private constructor(
  itemView: View,
  private val viewHolderDelegate: ViewHolderDelegate<T>,
  private val onListItemClick: OnListItemClick?
) : RecyclerView.ViewHolder(itemView), OnClickListener {

  private lateinit var _model: T

  init {
    viewHolderDelegate.bindView(itemView)
    viewHolderDelegate.clickableIds()
      .forEach {
        itemView.findViewById<View>(it).setOnClickListener(this)
      }
  }

  internal fun bind(model: T) {
    _model = model
    viewHolderDelegate.bind(model)
  }

  internal fun unbind() {
    viewHolderDelegate.unbind()
  }

  override fun onClick(v: View) {
    val position = adapterPosition
    if (position != RecyclerView.NO_POSITION) {
      viewHolderDelegate.onClick(v.id)
      onListItemClick?.invoke(v, position, _model)
    }
  }

  companion object {
    internal fun <T : ListModel> create(
      parent: ViewGroup,
      viewHolderDelegate: ViewHolderDelegate<T>,
      onListItemClick: OnListItemClick?
    ): OnlyViewHolder<T> {
      val inflater = LayoutInflater.from(parent.context)
      val view = inflater.inflate(viewHolderDelegate.layout(), parent, false)
      return OnlyViewHolder(view, viewHolderDelegate, onListItemClick)
    }
  }
}
