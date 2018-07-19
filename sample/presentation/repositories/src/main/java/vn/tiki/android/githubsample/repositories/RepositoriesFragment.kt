package vn.tiki.android.githubsample.repositories

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.LinearLayoutManager
import androidx.lifecycle.viewModel
import vn.tiki.android.androiddi.inject
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collectionx.ListFragment
import vn.tiki.android.collectionx.ListViewModel
import vn.tiki.android.githubsample.domain.entities.Repo
import vn.tiki.android.githubsample.repositories.viewholder.RepoModel

class RepositoriesFragment : ListFragment<Repo>() {

  private val viewModelFactory: ViewModelProvider.Factory by inject()

  override val viewModel: ListViewModel<Repo>
    get() = viewModel<RepositoriesViewModel>(viewModelFactory)

  override fun configureRecyclerView(recyclerView: android.support.v7.widget.RecyclerView) {
    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
  }

  override fun renderItems(items: List<Repo>): List<ListModel> {
    return items.map { RepoModel(it) }
  }
}
