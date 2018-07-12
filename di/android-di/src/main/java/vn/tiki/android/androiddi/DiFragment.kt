package vn.tiki.android.androiddi

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import kotlin.LazyThreadSafetyMode.NONE

inline fun <reified T> Fragment.inject(): Lazy<T> = lazy(NONE) {
  val activity: FragmentActivity? = activity
  val objectGraph = when (activity) {
    is DiActivity -> activity.objectGraph ?: DiApplication.getObjectGraph(context!!)
    else -> DiApplication.getObjectGraph(context!!)
  }
  objectGraph[T::class.java]
}
