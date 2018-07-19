package vn.tiki.android.githubsample.repositories

import android.os.Bundle
import vn.tiki.android.androiddi.DiActivity

class RepositoriesActivity : DiActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    plus(RepositoriesModule())

    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, RepositoriesFragment())
        .commitNow()
    }
  }
}
