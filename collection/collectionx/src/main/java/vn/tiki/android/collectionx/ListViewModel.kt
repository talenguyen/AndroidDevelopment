package vn.tiki.android.collectionx

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import vn.tiki.android.collectionx.Status.Error
import vn.tiki.android.collectionx.Status.Loading
import vn.tiki.android.collectionx.Status.Success

typealias OnSuccess<T> = (data: T, lastPate: Int) -> Unit
typealias OnError = (message: String) -> Unit

abstract class ListViewModel<T> : ViewModel() {

  /**
   * Sub-class must implement load to load data by page.
   */
  protected abstract val load: (page: Int, onError: OnError, onSuccess: OnSuccess<T>) -> Unit

  /**
   * Sub-class must implement refresh to refresh data.
   */
  protected abstract val refresh: (onError: OnError, onSuccess: OnSuccess<T>) -> Unit

  /**
   * Sub-class must implement reducer to produce next listState when load more success. e.g. to add new list to the current
   * list
   */
  protected abstract val reducer: (state: T, nextState: T) -> T

  private val _listState = MutableLiveData<ListState<T>>()

  val listState: LiveData<ListState<T>>
    get() = _listState

  val state: LiveData<T>
    get() = Transformations.map(_listState) { it.data }

  @Suppress("MemberVisibilityCanBePrivate")
  protected fun setState(nextState: ListState<T>) {
    _listState.value = nextState
  }

  fun loadFirstPage() {
    // Show load first loading
    setState(ListState(Loading))

    // Load first page
    load(
      1,
      { error ->
        setState(ListState(Error, error = error))
      },
      { data, lastPage ->
        setState(ListState(
          Success,
          data = data,
          currentPage = 1,
          lastPage = lastPage
        ))
      }
    )
  }

  @Suppress("UNCHECKED_CAST")
  fun loadNextPage(force: Boolean = false) {
    val state = _listState.value ?: return

    if (state.status == Loading) {
      return // don't auto load more when loading
    }

    if (state.status == Error && !force) {
      return // don't auto load more when retry is show
    }

    val data = state.data ?: return // don't auto load more when data is not loaded already

    if (state.isLastPage()) {
      return // don't auto load more when currently in the last page
    }

    // Show load more loading
    setState(state.copy(
      status = Loading,
      error = null,
      refreshError = null
    ))

    // Load next page
    val page = state.currentPage + 1
    load(
      page,
      { error ->
        setState(state.copy(
          status = Error,
          error = error
        ))
      },
      { newData, lastPage ->
        setState(ListState(
          Success,
          data = reducer(data, newData),
          currentPage = page,
          lastPage = lastPage
        ))
      }
    )
  }

  @Suppress("UNCHECKED_CAST")
  fun refresh() {
    val state = _listState.value ?: return

    if (state.status == Loading) {
      return // don't refresh when loading
    }

    if (state.data == null) {
      return // don't refresh when data is not loaded already
    }

    // Show load more loading
    setState(state.copy(
      status = Loading,
      error = null,
      refreshError = null
    ))

    refresh(
      { error ->
        setState(state.copy(
          status = Error,
          refreshError = error
        ))
      },
      { newData, lastPage ->
        setState(ListState(
          Success,
          data = newData,
          currentPage = 1,
          lastPage = lastPage
        ))
      }
    )
  }
}
