package vn.tiki.android.collection.sample.collectionx

import android.os.Handler
import vn.tiki.android.collectionx.ListViewModel
import vn.tiki.android.collectionx.OnError
import vn.tiki.android.collectionx.OnSuccess

class DemoCollectionXViewModel : ListViewModel<List<String>>() {

  override val load: (page: Int, onError: OnError, onSuccess: OnSuccess<List<String>>) -> Unit
    get() = { page, onError, onSuccess ->
      Handler().postDelayed(
        {
          when (page) {
            1 -> if (System.currentTimeMillis() % 2 == 0L) {
              val (data, lastPage) = firstPage()
              onSuccess(data, lastPage)
            } else {
              onError(error())
            }
            else -> {
              if (System.currentTimeMillis() % 2 == 0L) {
                val (data, lastPage) = secondPage()
                onSuccess(data, lastPage)
              } else {
                onError(error())
              }
            }
          }
        },
        500)
    }

  override val refresh: (onError: OnError, onSuccess: OnSuccess<List<String>>) -> Unit
    get() = { onError, onSuccess ->
      Handler().postDelayed(
        {
          if (System.currentTimeMillis() % 2 == 0L) {
            val (data, lastPage) = firstPage()
            onSuccess(data, lastPage)
          } else {
            onError(error())
          }
        },
        500
      )
    }

  override val reducer: (state: List<String>, nextState: List<String>) -> List<String>
    get() = { state, nextState -> state + nextState }

}

private fun error() = "server error"

private fun firstPage(): Pair<List<String>, Int> = (1..20).map { "Item $it" } to 2

private fun secondPage(): Pair<List<String>, Int> = (21..40).map { "Item $it" } to 2
