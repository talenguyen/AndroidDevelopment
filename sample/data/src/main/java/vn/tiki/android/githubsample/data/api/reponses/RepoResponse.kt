package vn.tiki.android.githubsample.data.api.reponses

import com.google.gson.annotations.SerializedName

class RepoResponse(
  val id: Int,
  @field:SerializedName("name")
  val name: String,
  @field:SerializedName("full_name")
  val fullName: String,
  @field:SerializedName("description")
  val description: String?,
  @field:SerializedName("owner")
  val owner: UserResponse,
  @field:SerializedName("stargazers_count")
  val stars: Int,
  @field:SerializedName("forks_count")
  val forks: Int
) 
