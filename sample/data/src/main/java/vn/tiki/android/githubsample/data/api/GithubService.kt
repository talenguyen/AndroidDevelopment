package vn.tiki.android.githubsample.data.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import vn.tiki.android.githubsample.data.api.reponses.RepoSearchResponse

interface GithubService {
  @GET("search/repositories")
  fun searchRepos(
    @Query("q") q: String,
    @Query("sort") sort: String,
    @Query("order") order: String,
    @Query("page") page: Int,
    @Query("per_page") per_page: Int
  ): Deferred<RepoSearchResponse>
}
