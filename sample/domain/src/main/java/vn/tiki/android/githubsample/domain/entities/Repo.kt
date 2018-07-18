package vn.tiki.android.githubsample.domain.entities

/**
 * Using name/owner_login as primary key instead of id since name/owner_login is always available
 * vs id is not.
 */
data class Repo(
  val id: Int,
  val name: String,
  val description: String?,
  val owner: User,
  val stars: Int,
  val forks: Int
)
