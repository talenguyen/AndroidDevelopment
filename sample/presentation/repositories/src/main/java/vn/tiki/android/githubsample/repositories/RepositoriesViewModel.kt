package vn.tiki.android.githubsample.repositories

import vn.tiki.android.collectionx.ListViewModel
import vn.tiki.android.githubsample.base.util.asListData
import vn.tiki.android.githubsample.domain.entities.Repo
import vn.tiki.android.githubsample.domain.interactors.GetTopRepos
import vn.tiki.android.githubsample.domain.interactors.RefreshTopRepos

class RepositoriesViewModel(
  getTopRepos: GetTopRepos,
  refreshTopRepos: RefreshTopRepos
) : ListViewModel<Repo>(
  loadFunc = { page, onError, onSuccess ->
    getTopRepos.execute(
      "kotlin",
      page,
      onError,
      onSuccess = { onSuccess(it.asListData()) }
    )
  },
  refreshFunc = { onError, onSuccess ->
    refreshTopRepos.execute(
      "kotlin",
      onError,
      onSuccess = { onSuccess(it.asListData()) }
    )
  }
)
