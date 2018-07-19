package vn.tiki.android.githubsample.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import vn.tiki.android.di.Module

class AppModule : Module() {
  init {
    provide { linker ->
      object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
          return linker.factoryFor(modelClass)(linker)
        }
      } as ViewModelProvider.Factory
    }
  }
}
