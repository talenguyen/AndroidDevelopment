package vn.tiki.android.collection.sample

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class ExampleApplication : Application() {

  lateinit var refWatcher: RefWatcher

  companion object {
    fun get(context: Context): ExampleApplication = context.applicationContext as ExampleApplication
  }

  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    refWatcher = LeakCanary.install(this)
    // Normal app init code...
  }
}