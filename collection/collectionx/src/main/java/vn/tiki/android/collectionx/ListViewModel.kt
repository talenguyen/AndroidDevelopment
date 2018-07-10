package vn.tiki.android.collectionx

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collectionx.Status.Error
import vn.tiki.android.collectionx.Status.Loading
import vn.tiki.android.collectionx.Status.Success

open class ListViewModel<T>(
  private val loadFunc: (Int, (String) -> Unit, (ListData<T>) -> Unit) -> Unit,
  private val refreshFunc: ((String) -> Unit, (ListData<T>) -> Unit) -> Unit
) : ViewModel() {

  private val _list = MutableLiveData<List<ListModel>>()
  private val _state = MutableLiveData<ListState<T>>()
  val state: LiveData<ListState<T>>
    get() = _state

  init {
    loadFirstPage()
  }

  fun loadFirstPage() {
    // Show load first loading
    _state.value = ListState(Loading)

    // Load first page
    loadFunc(
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
    loadFunc(
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

    refreshFunc(
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
