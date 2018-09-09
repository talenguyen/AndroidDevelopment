package vn.tiki.android.collectionx

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import io.reactivex.Observable

data class Paging<T>(
  val data: T,
  val nextPage: Int
)

data class MvRxListState<T>(
  val getRequest: Async<Paging<T>> = Uninitialized,
  val fetchRequest: Async<Paging<T>> = Uninitialized,
  val currentPage: Int = 0,
  val nextPage: Int = 1,
  val data: T? = null
) : MvRxState

fun MvRxListState<*>.isLoading() = getRequest is Loading || fetchRequest is Loading
fun MvRxListState<*>.isFail() = getRequest is Fail || fetchRequest is Fail
fun MvRxListState<*>.isUninitialized() = getRequest is Uninitialized && fetchRequest is Uninitialized
fun MvRxListState<*>.isSuccess() = !isLoading() && !isFail()
fun MvRxListState<*>.isOnLastPage() = currentPage >= nextPage

abstract class MvRxListViewModel<T>(
  initialState: MvRxListState<T>,
  debug: Boolean
) : BaseMvRxViewModel<MvRxListState<T>>(initialState, debug) {

  protected abstract val get: (page: Int) -> Observable<Paging<T>>
  protected abstract val fetch: () -> Observable<Paging<T>>
  protected abstract val reducer: (state: T, nextState: T) -> T

  init {
    getNextPage()
  }

  fun getNextPage() = withState { state ->
    if (state.isLoading() || state.isOnLastPage()) return@withState

    val nextPage = state.currentPage + 1
    get(nextPage).execute {
      if (it is Success) {
        val paging: Paging<T> = it()
        copy(
          getRequest = it,
          data = if (state.data == null) paging.data else reducer(state.data, paging.data),
          currentPage = nextPage,
          nextPage = paging.nextPage
        )
      } else {
        copy(getRequest = it)
      }
    }
  }

  fun refresh() = withState { state ->
    if (state.fetchRequest is Loading) return@withState

    fetch().execute {
      if (it is Success) {
        val paging: Paging<T> = it()
        copy(
          fetchRequest = it,
          data = paging.data,
          currentPage = 1,
          nextPage = paging.nextPage
        )
      } else {
        copy(fetchRequest = it)
      }
    }
  }
}