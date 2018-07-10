package vn.tiki.android.collectionx

enum class Status {
  Loading,
  Error,
  Success
}

interface ListData<T> {
  fun data(): List<T>
  fun lastPage(): Int
}

data class ListState<T>(
  val status: Status,
  val error: String? = null,
  val refreshError: String? = null,
  val data: List<T>? = null,
  val currentPage: Int = 0,
  val lastPage: Int = 0
)

fun ListState<*>.isLastPage() = currentPage == lastPage
