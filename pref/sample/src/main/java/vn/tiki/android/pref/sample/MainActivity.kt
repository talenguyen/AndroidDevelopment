package vn.tiki.android.pref.sample

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import vn.tiki.android.pref.Pref
import vn.tiki.android.pref.stringPref

class MainActivity : AppCompatActivity() {

  private val sharedPreferences: SharedPreferences by lazy {
    PreferenceManager.getDefaultSharedPreferences(applicationContext)
  }
  private val prefString: Pref<String> by lazy {
    sharedPreferences.stringPref("stringPref", "not set")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    prefValueTextView.text = prefString.get()

    updateButton.setOnClickListener {
      val newValue = prefValueEditText.text.toString()
      prefString.set(newValue)
    }
  }
}
