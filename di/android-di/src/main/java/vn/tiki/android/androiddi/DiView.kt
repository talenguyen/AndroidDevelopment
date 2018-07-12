package vn.tiki.android.androiddi

import android.view.View

inline fun <reified T> View.inject(): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
  val objectGraph = when (context) {
    is DiActivity -> (context as DiActivity).objectGraph ?: DiApplication.getObjectGraph(context!!)
    else -> DiApplication.getObjectGraph(context!!)
  }
  objectGraph[T::class.java]
}
