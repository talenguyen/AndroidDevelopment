package vn.tiki.android.githubsample.domain.interactors

import vn.tiki.android.githubsample.domain.entities.ListData
import vn.tiki.android.githubsample.domain.entities.Repo

interface RefreshTopRepos {
  fun execute(
    language: String,
    onError: (String) -> Unit,
    onSuccess: (ListData<Repo>) -> Unit
  )
}
