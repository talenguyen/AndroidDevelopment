package vn.tiki.android.collection.sample.collectionx

import io.reactivex.Observable
import vn.tiki.android.collectionx.MvRxListState
import vn.tiki.android.collectionx.MvRxListViewModel
import vn.tiki.android.collectionx.Paging

class DemoCollectionXViewModel(
  initialState: MvRxListState<List<String>>
) : MvRxListViewModel<List<String>>(initialState, true) {
  override val get: (page: Int) -> Observable<Paging<List<String>>>
    get() = { page ->
      println("get: $page")
      Observable.fromCallable {
        if (System.currentTimeMillis() % 2 == 0L) {
          throw error()
        }
        when (page) {
          1 -> firstPage()
          else -> lastPage()
        }
      }
    }

  override val fetch: () -> Observable<Paging<List<String>>>
    get() = {
      Observable.fromCallable {
        if (System.currentTimeMillis() % 2 == 0L) {
          throw error()
        }
        firstPage()
      }
    }

  override val reducer: (state: List<String>, nextState: List<String>) -> List<String>
    get() = { state, nextState -> state + nextState }

  init {
    logStateChanges()
  }
}

private fun error() = RuntimeException("server error")
private fun firstPage() = Paging((1..20).map { "Item $it" }, 2)
private fun lastPage() = Paging((21..40).map { "Item $it" }, 2)
