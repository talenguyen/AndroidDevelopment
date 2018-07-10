package vn.tiki.android.pref

class Pref<T>(
  private val getter: Getter<T>,
  private val setter: Setter<T>,
  private val key: String,
  private val defaultValue: T
) {

  fun get(): T {
    return getter.invoke(key, defaultValue)
  }

  fun set(value: T) {
    setter.invoke(key, value)
  }
}
