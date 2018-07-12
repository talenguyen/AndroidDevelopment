package vn.tiki.android.linking

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri

class Linking private constructor() {

  init {
    throw InstantiationError()
  }

  companion object {

    fun canOpen(context: Context, url: String): Boolean {
      val deepLinkIntent = deepLinkIntent(context, url)
      return canOpen(context, deepLinkIntent)
    }

    fun deepLinkIntent(context: Context, url: String): Intent {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.setPackage(context.packageName)
      intent.data = Uri.parse(url)
      return intent
    }

    fun open(context: Context, url: String) {
      val deepLinkIntent = deepLinkIntent(context, url)
      if (context !is Activity) {
        // Add this flag or throw exception: Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag.
        deepLinkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(deepLinkIntent)
    }

    private fun canOpen(context: Context, deepLinkIntent: Intent): Boolean {
      return deepLinkIntent.resolveActivity(context.packageManager) != null
    }
  }
}
