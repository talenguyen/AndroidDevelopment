package vn.tiki.android.githubsample.base

import vn.tiki.android.androiddi.DiApplication
import vn.tiki.android.di.Module
import vn.tiki.android.githubsample.data.DataModule
import vn.tiki.android.githubsample.domain.DomainModule

class BaseApplication : DiApplication() {
  override fun getModules(): List<Module> {
    return listOf(
      DataModule(),
      DomainModule(),
      AppModule()
    )
  }
}
