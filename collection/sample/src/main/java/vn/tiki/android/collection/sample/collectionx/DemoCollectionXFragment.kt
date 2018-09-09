package vn.tiki.android.collection.sample.collectionx

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.airbnb.mvrx.fragmentViewModel
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.sample.viewholder.userItem
import vn.tiki.android.collectionx.MvRxListFragment

class DemoCollectionXFragment : MvRxListFragment<List<String>>() {

  override val viewModel: DemoCollectionXViewModel by fragmentViewModel()

  override fun configureRecyclerView(recyclerView: RecyclerView) {
    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
  }

  override fun MutableList<ListModel>.render(data: List<String>) {
    data.forEach {
      userItem(it) {
        onClick = { println("$it is clicked") }
      }
    }
  }
}
