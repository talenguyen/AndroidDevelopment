package vn.tiki.android.githubsample.domain.interactors

import vn.tiki.android.githubsample.domain.entities.ListData
import vn.tiki.android.githubsample.domain.entities.Repo

interface GetTopRepos {
  fun execute(
    page: Int,
    onError: (String) -> Unit,
    onSuccess: (ListData<Repo>) -> Unit
  )
}
