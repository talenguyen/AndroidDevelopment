package vn.tiki.android.githubsample.domain.internal.interactors

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import vn.tiki.android.githubsample.data.api.GithubService
import vn.tiki.android.githubsample.data.api.reponses.RepoSearchResponse
import vn.tiki.android.githubsample.domain.entities.ListData
import vn.tiki.android.githubsample.domain.entities.Repo
import vn.tiki.android.githubsample.domain.functional.awaitSafe
import vn.tiki.android.githubsample.domain.interactors.RefreshTopRepos
import vn.tiki.android.githubsample.domain.utils.getTopReopsByLanguague
import vn.tiki.android.githubsample.domain.utils.toListData

class RefreshTopReposImpl(private val githubService: GithubService) : RefreshTopRepos {

  override fun execute(
    language: String,
    onError: (String) -> Unit,
    onSuccess: (ListData<Repo>) -> Unit
  ) {

    val page = 1

    val fnR: (RepoSearchResponse) -> Unit = {
      onSuccess(it.toListData(page))
    }

    launch(UI) {
      getTopReopsByLanguague(githubService, language, page).awaitSafe().either(onError, fnR)
    }
  }
}
