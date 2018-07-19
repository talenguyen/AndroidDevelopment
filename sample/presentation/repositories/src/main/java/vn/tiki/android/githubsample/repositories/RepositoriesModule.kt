package vn.tiki.android.githubsample.repositories

import vn.tiki.android.di.Module
import vn.tiki.android.githubsample.domain.interactors.GetTopRepos
import vn.tiki.android.githubsample.domain.interactors.RefreshTopRepos

class RepositoriesModule : Module() {
  init {
    provide { linker ->
      val getTopRepos = get<GetTopRepos>(linker)
      val refreshTopRepos = get<RefreshTopRepos>(linker)
      RepositoriesViewModel(getTopRepos, refreshTopRepos)
    }
  }
}
