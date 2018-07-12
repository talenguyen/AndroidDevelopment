package vn.tiki.android.androiddi

import android.app.Application
import android.content.Context
import vn.tiki.android.di.Module
import vn.tiki.android.di.ObjectGraph
import kotlin.LazyThreadSafetyMode.NONE

abstract class DiApplication : Application() {

  protected lateinit var objectGraph: ObjectGraph
    private set

  companion object {
    fun getObjectGraph(context: Context) = (context.applicationContext as DiApplication).objectGraph
  }

  override fun onCreate() {
    super.onCreate()
    objectGraph = ObjectGraph.create(getModules())
  }

  abstract fun getModules(): List<Module>

  protected inline fun <reified T> inject(): Lazy<T> = lazy(NONE) {
    objectGraph[T::class.java]
  }
}
