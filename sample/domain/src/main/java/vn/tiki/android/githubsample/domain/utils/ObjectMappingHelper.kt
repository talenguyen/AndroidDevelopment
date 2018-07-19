package vn.tiki.android.githubsample.domain.utils

import vn.tiki.android.githubsample.data.api.reponses.RepoResponse
import vn.tiki.android.githubsample.data.api.reponses.RepoSearchResponse
import vn.tiki.android.githubsample.data.api.reponses.UserResponse
import vn.tiki.android.githubsample.domain.ITEM_PER_PAGE
import vn.tiki.android.githubsample.domain.entities.ListData
import vn.tiki.android.githubsample.domain.entities.Paging
import vn.tiki.android.githubsample.domain.entities.Repo
import vn.tiki.android.githubsample.domain.entities.User

fun UserResponse.toUser() = User(login, avatarUrl, name)

fun RepoResponse.toRepo() = Repo(id, name, description, owner.toUser(), stars, forks)

fun RepoSearchResponse.toListData(page: Int): ListData<Repo> {
  val repos = items.map { it.toRepo() }
  val lastPage = total / ITEM_PER_PAGE
  return ListData(repos, Paging(page, lastPage, total))
}
