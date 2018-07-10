package vn.tiki.android.collection.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import vn.tiki.android.collection.sample.collection.DemoActivity
import vn.tiki.android.collection.sample.collectionx.DemoCollectionXActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    demoCollectionButton.setOnClickListener {
      startActivity(Intent(this, DemoActivity::class.java))
    }

    demoCollectionXButton.setOnClickListener {
      startActivity(Intent(this, DemoCollectionXActivity::class.java))
    }
  }
}
