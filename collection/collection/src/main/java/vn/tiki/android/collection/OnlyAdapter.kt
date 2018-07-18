package vn.tiki.android.collection

import android.support.v4.util.ArrayMap
import android.support.v7.util.DiffUtil
import android.support.v7.util.DiffUtil.ItemCallback
import android.view.ViewGroup

private val DEFAULT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListModel>() {
  override fun areItemsTheSame(oldItem: ListModel?, newItem: ListModel?): Boolean {
    return oldItem?.getKey() == newItem?.getKey()
  }

  override fun areContentsTheSame(oldItem: ListModel?, newItem: ListModel?): Boolean {
    return oldItem == newItem
  }
}

class OnlyAdapter(
  diffCallback: ItemCallback<ListModel> = DEFAULT_DIFF_CALLBACK,
  private val onListItemClick: OnListItemClick? = null
) : ListAdapter<ListModel, OnlyViewHolder<ListModel>>(diffCallback) {

  private val viewTypeManager = ViewTypeManager()
  private val viewHolderDelegateFactoryMap = ArrayMap<Int, () -> ViewHolderDelegate<ListModel>>()

  override fun getItemViewType(position: Int): Int {
    val model = getItem(position)
    val viewType = viewTypeManager.getViewType(model.javaClass)
    viewHolderDelegateFactoryMap[viewType] = model.getViewHolderDelegateFactory()
    return viewType
  }

  override fun onBindViewHolder(holder: OnlyViewHolder<ListModel>, position: Int) {
    val item = getItem(position)
    holder.bind(item)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlyViewHolder<ListModel> {
    val viewHolderDelegateFactory = viewHolderDelegateFactoryMap[viewType]
    val viewHolderDelegate: ViewHolderDelegate<ListModel> = viewHolderDelegateFactory!!.invoke()
    return OnlyViewHolder.create(parent, viewHolderDelegate, onListItemClick)
  }

  override fun onViewRecycled(holder: OnlyViewHolder<ListModel>) {
    super.onViewRecycled(holder)
    holder.unbind()
  }
}
