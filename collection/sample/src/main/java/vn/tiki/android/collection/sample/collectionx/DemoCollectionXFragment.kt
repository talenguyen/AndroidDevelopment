package vn.tiki.android.collection.sample.collectionx

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.sample.viewholder.UserModel
import vn.tiki.android.collectionx.ListFragment
import vn.tiki.android.collectionx.ListViewModel

class DemoCollectionXFragment : ListFragment<String>() {

  override val viewModel: ListViewModel<String>
    get() = ViewModelProviders.of(this).get(DemoCollectionXViewModel::class.java)

  override fun configureRecyclerView(recyclerView: RecyclerView) {
    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
  }

  override fun renderItems(items: List<String>): List<ListModel> {
    return items.map {
      UserModel(name = it).apply { onClick = { println("$it is clicked") } }
    }
  }
}
