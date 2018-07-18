package vn.tiki.android.githubsample.domain.entities

data class ListData<T>(
  val list: List<T>,
  val paging: Paging
)
