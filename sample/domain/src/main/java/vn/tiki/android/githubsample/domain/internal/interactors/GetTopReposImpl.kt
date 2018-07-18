package vn.tiki.android.githubsample.domain.internal.interactors

import vn.tiki.android.githubsample.domain.entities.ListData
import vn.tiki.android.githubsample.domain.entities.Repo
import vn.tiki.android.githubsample.domain.interactors.GetTopRepos

class GetTopReposImpl : GetTopRepos {
  override fun execute(
    page: Int,
    onError: (String) -> Unit,
    onSuccess: (ListData<Repo>) -> Unit
  ) {

  }
}
