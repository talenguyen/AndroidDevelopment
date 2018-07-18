package vn.tiki.android.collection.sample.collectionx

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import vn.tiki.android.collection.sample.R
import vn.tiki.android.collectionx.ListFragment.ListFragmentDelegate

class DemoCollectionXActivity : AppCompatActivity(), ListFragmentDelegate {

  override fun showActivityIndicator(isShown: Boolean) {
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_collectionx_demo)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, DemoCollectionXFragment())
        .commitNow()
    }
  }
}
