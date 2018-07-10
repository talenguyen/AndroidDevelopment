package vn.tiki.android.collection.sample.collectionx

import android.os.Handler
import vn.tiki.android.collectionx.ListData
import vn.tiki.android.collectionx.ListViewModel

class DemoCollectionXViewModel: ListViewModel<String>(
  { page, onError, onSuccess ->
    Handler().postDelayed(
      {
        when (page) {
          1 -> if (System.currentTimeMillis() % 2 == 0L) {
            onSuccess(firstPage())
          } else {
            onError(error())
          }
          else -> {
            if (System.currentTimeMillis() % 2 == 0L) {
              onSuccess(secondPage())
            } else {
              onError(error())
            }
          }
        }
      },
      500)
  },
  { onError, onSuccess ->
    Handler().postDelayed(
      {
        if (System.currentTimeMillis() % 2 == 0L) {
          onSuccess(firstPage())
        } else {
          onError(error())
        }
      },
      500
    )
  }
)

private fun error() = "server error"

private fun firstPage(): ListData<String> {

  return object: ListData<String> {
    override fun data(): List<String> = (1..20).map { "Item $it" }

    override fun lastPage(): Int = 2
  }
}

private fun secondPage(): ListData<String> {

  return object: ListData<String> {
    override fun data(): List<String> = (21..40).map { "Item $it" }

    override fun lastPage(): Int = 2
  }
}
