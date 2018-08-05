package vn.tiki.android.collectionx

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import vn.tiki.android.collectionx.Status.Error
import vn.tiki.android.collectionx.Status.Loading
import vn.tiki.android.collectionx.Status.Success

abstract class ListViewModel<T> : ViewModel() {

  /**
   * Sub-class must implement load to load data by page.
   */
  protected abstract val load: (Int, (String) -> Unit, (ListData<T>) -> Unit) -> Unit

  /**
   * Sub-class must implement refresh to refresh data.
   */
  protected abstract val refresh: ((String) -> Unit, (ListData<T>) -> Unit) -> Unit

  private val _state = MutableLiveData<ListState<T>>()
  val state: LiveData<ListState<T>>
    get() = _state

  @Suppress("MemberVisibilityCanBePrivate")
  protected fun setState(nextState: ListState<T>) {
    _state.value = nextState
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
      { listData ->
        setState(ListState(
          Success,
          data = listData.data(),
          currentPage = 1,
          lastPage = listData.lastPage()
        ))
      }
    )
  }

  @Suppress("UNCHECKED_CAST")
  fun loadNextPage(force: Boolean = false) {
    val state = _state.value ?: return

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
      { listData ->
        setState(ListState(
          Success,
          data = data + listData.data(),
          currentPage = page,
          lastPage = listData.lastPage()
        ))
      }
    )
  }

  @Suppress("UNCHECKED_CAST")
  fun refresh() {
    val state = _state.value ?: return

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
      { listData ->
        setState(ListState(
          Success,
          data = listData.data(),
          currentPage = 1,
          lastPage = listData.lastPage()
        ))
      }
    )
  }
}
