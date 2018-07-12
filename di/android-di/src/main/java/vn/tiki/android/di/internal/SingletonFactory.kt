package vn.tiki.android.androiddi.internal

class SingletonFactory<out T> private constructor(private val factory: Factory<T>) :
  Factory<T> {

  companion object {
    fun <T> of(factory: Factory<T>): SingletonFactory<T> {
      return SingletonFactory(factory)
    }
  }

  private var instance: T? = null

  override fun invoke(linker: Linker): T {
    if (instance == null) {
      instance = factory(linker)
    }
    return instance!!
  }
}
