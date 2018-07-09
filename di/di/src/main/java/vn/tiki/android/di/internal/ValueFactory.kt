package vn.tiki.android.di.internal

class ValueFactory<out T> private constructor(private val instance: T) : Factory<T> {

  companion object {

    fun <T> of(instance: T): ValueFactory<T> {
      return ValueFactory(instance)
    }
  }

  override fun invoke(linker: Linker): T {
    return instance
  }
}
