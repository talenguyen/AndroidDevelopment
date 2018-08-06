package vn.tiki.android.collection.sample.viewholder

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.OnlyAdapter
import vn.tiki.android.collection.SimpleViewHolderDelegate
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.collection.sample.R

object CollectionModel : ListModel {

  override fun getKey(): String {
    return "vn.tiki.android.collection.sample.viewholder.CollectionModel"
  }

  override fun <T : ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T> {
    return {
      @Suppress("UNCHECKED_CAST")
      CollectionViewHolderDelegate() as ViewHolderDelegate<T>
    }
  }
}

class CollectionViewHolderDelegate : SimpleViewHolderDelegate<CollectionModel>() {
  lateinit var recyclerView: RecyclerView
  private val onlyAdapter = OnlyAdapter(isAutoReleaseAdapterOnDetach = false)
  override fun layout(): Int {
    return R.layout.item_collection
  }

  override fun bindView(view: View) {
    super.bindView(view)
    recyclerView = view as RecyclerView
    recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
  }

  override fun bind(model: CollectionModel) {
    super.bind(model)
    val models = (0..10).map { ButtonModel("Button $it") {} }
    println("CollectionViewHolderDelegate.bind")
    println(recyclerView.adapter)
    recyclerView.adapter = onlyAdapter
    (recyclerView.adapter as OnlyAdapter).submitList(models)
  }

  override fun unbind() {
    super.unbind()
    println("CollectionViewHolderDelegate.unbind")
    recyclerView.adapter = null
  }
}
