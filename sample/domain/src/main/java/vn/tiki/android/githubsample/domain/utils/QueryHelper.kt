package vn.tiki.android.githubsample.domain.utils

import vn.tiki.android.githubsample.data.api.GithubService

fun getTopReopsByLanguague(
  githubService: GithubService,
  language: String,
  page: Int
) = githubService.searchRepos(
  "language:$language",
  "stars",
  "desc",
  page,
  10
)
