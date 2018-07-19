package vn.tiki.android.githubsample.domain

import vn.tiki.android.di.Module
import vn.tiki.android.githubsample.data.api.GithubService
import vn.tiki.android.githubsample.domain.interactors.GetTopRepos
import vn.tiki.android.githubsample.domain.interactors.RefreshTopRepos
import vn.tiki.android.githubsample.domain.internal.interactors.GetTopReposImpl
import vn.tiki.android.githubsample.domain.internal.interactors.RefreshTopReposImpl

class DomainModule : Module() {
  init {
    provide<GetTopRepos> { linker ->
      val githubService = get<GithubService>(linker)
      GetTopReposImpl(githubService)
    }
    provide<RefreshTopRepos> { linker ->
      val githubService = get<GithubService>(linker)
      RefreshTopReposImpl(githubService)
    }
  }
}
