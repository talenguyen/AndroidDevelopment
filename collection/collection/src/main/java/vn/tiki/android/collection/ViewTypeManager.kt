package vn.tiki.android.collection

class ViewTypeManager {

  private val viewTypeMapping = mutableMapOf<Class<*>, Int>()

  fun getViewType(modelClass: Class<*>): Int {
    if (!viewTypeMapping.containsKey(modelClass)) {
      val viewType = viewTypeMapping.size + 1
      viewTypeMapping[modelClass] = viewType
    }
    return viewTypeMapping[modelClass]!!
  }
}
