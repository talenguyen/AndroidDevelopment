package vn.tiki.android.androiddi

import android.support.v7.app.AppCompatActivity
import vn.tiki.android.di.Module
import vn.tiki.android.di.ObjectGraph
import kotlin.LazyThreadSafetyMode.NONE

abstract class DiActivity : AppCompatActivity() {

  @PublishedApi
  internal var objectGraph: ObjectGraph? = null
    private set

  fun plus(module: Module) {
    objectGraph = DiApplication.getObjectGraph(this).plus(listOf(module))
  }

  protected inline fun <reified T> inject(): Lazy<T> = lazy(NONE) {
    val objectGraph = this.objectGraph ?: DiApplication.getObjectGraph(this)
    objectGraph[T::class.java]
  }
}
