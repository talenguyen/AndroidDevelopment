package vn.tiki.android.collectionx

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.withState
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.OnlyAdapter
import vn.tiki.android.collectionx.util.RecyclerViewLoadMoreDetector
import vn.tiki.android.collectionx.viewholder.ErrorModel
import vn.tiki.android.collectionx.viewholder.LoadingModel
import vn.tiki.android.collectionx.viewholder.NoneModel
import vn.tiki.android.collectionx.viewholder.RetryModel
import vn.tiki.android.collectionx.viewholder.errorItem
import vn.tiki.android.collectionx.viewholder.loadingItem
import vn.tiki.android.collectionx.viewholder.retryItem

abstract class MvRxListFragment<T> : BaseMvRxFragment() {

  interface ListFragmentDelegate {
    fun showActivityIndicator(isShown: Boolean)
  }

  private var listFragmentDelegate: ListFragmentDelegate? = null

  protected lateinit var swipeRefreshLayout: SwipeRefreshLayout
  protected lateinit var recyclerView: RecyclerView

  /**
   * Create [ListViewModel] class
   */
  protected abstract val viewModel: MvRxListViewModel<T>

  /**
   * Sub-class must override this method to configure [RecyclerView]. e.g. setLayoutManager, addItemDecorator etc.
   * something else
   */
  protected abstract fun configureRecyclerView(recyclerView: RecyclerView)

  /**
   * Map given data to [ListModel] to be displayed in the [RecyclerView]
   * @param data
   */
  protected abstract fun MutableList<ListModel>.render(data: T)

  /**
   * You can override this to return your desired layout for [RetryModel] view. Note: Your layout must declare @id/itemView
   * for root view
   */
  protected open fun getRetryLayout() = R.layout.viewx_item_error_retry

  /**
   * You can override this to return your desired layout for [ErrorModel] view. Note: Your layout must declare @id/itemView
   * for root view and <code>@id/errorMessageTextView</code> for the TextView.
   */
  protected open fun getErrorLayout() = R.layout.viewx_item_error

  /**
   * You can override this to return your desired layout for [LoadingModel] view
   */
  protected open fun getLoadingLayout() = R.layout.viewx_item_loading

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is ListFragmentDelegate) {
      listFragmentDelegate = context
    }
  }

  override fun onDetach() {
    super.onDetach()
    listFragmentDelegate = null
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(
      R.layout.viewx_fragment_list,
      container,
      false
    ).apply {
      swipeRefreshLayout = findViewById(R.id.refreshLayout)
      swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
      recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
        configureRecyclerView(this)
        addOnScrollListener(RecyclerViewLoadMoreDetector {
          viewModel.getNextPage()
        })
        adapter = OnlyAdapter()
      }
    }
  }

  @Suppress("UNCHECKED_CAST")
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
  }

  private fun showLoadingIndicator(isShown: Boolean) {
    listFragmentDelegate?.showActivityIndicator(isShown)
  }

  override fun invalidate() {
    withState(viewModel) { state ->
      if (state.isUninitialized()) {
        return@withState
      }
      val getRequest = state.getRequest
      val fetchRequest = state.fetchRequest

      showLoadingIndicator(state.isLoading())

      val renderModels: List<ListModel> = if (state.data == null) {
        mutableListOf<ListModel>().apply {
          when (getRequest) {
            is Loading -> loadingItem(getLoadingLayout())
            is Fail -> errorItem(
              layoutId = getErrorLayout(),
              text = getRequest.error.message ?: "unknown error"
            ) {
              onClick = { viewModel.getNextPage() }
            }
            else -> throw IllegalArgumentException("unknown listState")
          }
        }
      } else {
        mutableListOf<ListModel>().apply {
          // add NoneModel as the first item. This is a tricky solution for insert refresh error.
          add(NoneModel)

          // Show refresh error at the top
          if (fetchRequest is Fail) {
            retryItem(getRetryLayout()) {
              onClick = {
                swipeRefreshLayout.isRefreshing = true
                viewModel.refresh()
              }
            }
          }

          // Show list data at the middle
          render(state.data)

          val isFail = getRequest is Fail || fetchRequest is Fail
          // Show load more indicator if current is not the last page
          if (!isFail && !state.isOnLastPage()) {
            loadingItem(getLoadingLayout())
          }

          // Show load more error at the bottom
          if (getRequest is Fail) {
            retryItem(getRetryLayout()) {
              onClick = { viewModel.getNextPage() }
            }
          }
        }
      }

      (recyclerView.adapter as OnlyAdapter).submitList(renderModels)

      // Scroll to top when first page is successfully loaded.
      if (state.isSuccess() && state.currentPage == 1) {
        recyclerView.post {
          recyclerView.scrollToPosition(0)
        }
      }

      // Enable refreshing
      swipeRefreshLayout.isRefreshing = swipeRefreshLayout.isEnabled && fetchRequest is Loading
    }
  }
}