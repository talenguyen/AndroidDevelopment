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

  fun loadFirstPage() {
    if (_state.value != null) {
      // Don't need to reload first page
      return
    }

    // Show load first loading
    _state.value = ListState(Loading)

    // Load first page
    load(
      1,
      { error ->
        _state.value = ListState(Error, error = error)
      },
      { listData ->
        _state.value = ListState(
          Success,
          data = listData.data(),
          currentPage = 1,
          lastPage = listData.lastPage()
        )
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
    _state.value = state.copy(
      status = Loading,
      error = null,
      refreshError = null
    )

    // Load next page
    val page = state.currentPage + 1
    load(
      page,
      { error ->
        _state.value = state.copy(
          status = Error,
          error = error
        )
      },
      { listData ->
        _state.value = ListState(
          Success,
          data = data + listData.data(),
          currentPage = page,
          lastPage = listData.lastPage()
        )
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
    _state.value = state.copy(
      status = Loading,
      error =  null,
      refreshError = null
    )

    refresh(
      { error ->
        _state.value = state.copy(
          status = Error,
          refreshError = error
        )
      },
      { listData ->
        _state.value = ListState(
          Success,
          data = listData.data(),
          currentPage = 1,
          lastPage = listData.lastPage()
        )
      }
    )
  }
}
