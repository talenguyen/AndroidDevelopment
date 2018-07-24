package vn.tiki.android.collectionx

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.OnlyAdapter
import vn.tiki.android.collectionx.Status.Success
import vn.tiki.android.collectionx.util.RecyclerViewLoadMoreDetector
import vn.tiki.android.collectionx.viewholder.Error
import vn.tiki.android.collectionx.viewholder.Loading
import vn.tiki.android.collectionx.viewholder.None
import vn.tiki.android.collectionx.viewholder.Retry

abstract class ListFragment<T> : Fragment() {

  interface ListFragmentDelegate {
    fun showActivityIndicator(isShown: Boolean)
  }

  private var listFragmentDelegate: ListFragmentDelegate? = null

  private var swipeRefreshLayout: SwipeRefreshLayout? = null

  /**
   * Create [ListViewModel] class
   */
  protected abstract val viewModel: ListViewModel<T>

  /**
   * Sub-class must override this method to configure [RecyclerView]. e.g. setLayoutManager, addItemDecorator etc.
   * something else
   */
  protected abstract fun configureRecyclerView(recyclerView: RecyclerView)

  /**
   * Map given data to [ListModel] to be displayed in the [RecyclerView]
   * @param items
   * @return [List] of [ListModel] that will be rendered in the [RecyclerView]
   */
  protected abstract fun renderItems(items: List<T>): List<ListModel>

  /**
   * You can override this to return your desired layout for [Retry] view. Note: Your layout must declare @id/itemView
   * for root view
   */
  protected open fun getRetryLayout() = R.layout.viewx_item_error_retry

  /**
   * You can override this to return your desired layout for [Error] view. Note: Your layout must declare @id/itemView
   * for root view and <code>@id/errorMessageTextView</code> for the TextView.
   */
  protected open fun getErrorLayout() = R.layout.viewx_item_error

  /**
   * You can override this to return your desired layout for [Loading] view
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
    )
  }

  @Suppress("UNCHECKED_CAST")
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    swipeRefreshLayout = view?.findViewById(R.id.refreshLayout)
    swipeRefreshLayout?.setOnRefreshListener { viewModel.refresh() }

    val adapter = OnlyAdapter()

    val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
      configureRecyclerView(this)
      addOnScrollListener(RecyclerViewLoadMoreDetector {
        viewModel.loadNextPage()
      })
      this.adapter = adapter
    }

    viewModel.state.observe(this, Observer {
      it?.let {
        showLoadingIndicator(it.status == Status.Loading)

        val renderModels: List<ListModel> = if (it.data == null) {
          when (it.status) {
            Status.Loading -> listOf(renderLoading())
            Status.Error -> listOf(Error(
              layoutId = getErrorLayout(),
              text = it.error ?: "unknown error",
              onClick = { viewModel.loadFirstPage() }))
            else -> throw IllegalArgumentException("unknown state")
          }
        } else {
          mutableListOf<ListModel>().apply {
            // add None as the first item. This is a tricky solution for insert refresh error.
            add(None)

            // Show refresh error at the top
            if (it.refreshError != null) {
              add(renderRetry { viewModel.refresh() })
            }

            // Show list data at the middle
            addAll(renderItems(it.data))

            // Show load more indicator if current is not the last page
            if (it.status != Status.Error && it.isLastPage().not()) {
              add(renderLoading())
            }

            // Show load more error at the bottom
            if (it.error != null) {
              add(renderRetry { viewModel.loadNextPage(true) })
            }
          }
        }

        adapter.submitList(renderModels)

        // Scroll to top when first page is successfully loaded.
        if (it.status == Success && it.currentPage == 1) {
          recyclerView?.isNestedScrollingEnabled = false
          println("recyclerView?.isEnabled: ${recyclerView?.isEnabled}")
          recyclerView?.postDelayed({
            recyclerView.scrollToPosition(0)
            recyclerView.isNestedScrollingEnabled = true
            println("recyclerView?.isEnabled: ${recyclerView.isEnabled}")
          }, 2000)
        }

        // Enable refreshing
        swipeRefreshLayout?.isRefreshing = (swipeRefreshLayout?.isRefreshing ?: false) && it.status == Status.Loading
      }
    })
  }

  /**
   * Enable/Disable SwipeRefresh
   *
   * @param isEnabled <code>true</code> for enable or disable otherwise.
   */
  fun setRefreshEnabled(isEnabled: Boolean) {
    swipeRefreshLayout?.isEnabled = isEnabled
  }

  private fun showLoadingIndicator(isShown: Boolean) {
    listFragmentDelegate?.showActivityIndicator(isShown)
  }

  private fun renderRetry(onClick: () -> Unit) = Retry(getRetryLayout(), onClick)

  private fun renderLoading() = Loading(getLoadingLayout())
}
