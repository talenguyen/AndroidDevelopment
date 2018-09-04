package vn.tiki.android.collectionx

enum class Status {
  Loading,
  Error,
  Success
}

data class ListState<T>(
  val status: Status,
  val error: String? = null,
  val refreshError: String? = null,
  val data: T? = null,
  val currentPage: Int = 0,
  val lastPage: Int = 0
)

fun ListState<*>.isLastPage() = currentPage == lastPage
