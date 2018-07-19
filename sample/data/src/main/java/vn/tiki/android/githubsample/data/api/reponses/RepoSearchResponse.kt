package vn.tiki.android.githubsample.data.api.reponses

import com.google.gson.annotations.SerializedName

/**
 * Simple object to hold repo search responses. This is different from the Entity in the database
 * because we are keeping a search result in 1 row and denormalizing list of results into a single
 * column.
 */
class RepoSearchResponse(
  @SerializedName("total_count")
  val total: Int = 0,
  @SerializedName("items")
  val items: List<RepoResponse>
)
