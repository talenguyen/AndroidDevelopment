package vn.tiki.android.di

import vn.tiki.android.di.internal.Linker
import vn.tiki.android.di.internal.SingletonFactory
import vn.tiki.android.di.internal.ValueFactory

open class Module {

  @PublishedApi
  internal val linker: Linker = Linker()

  inline fun <reified T> provide(noinline factory: (Linker) -> T) = linker.install(T::class.java, factory)

  inline fun <reified T> provideSingleton(noinline factory: (Linker) -> T) {
    linker.install(T::class.java, SingletonFactory.of(factory))
  }

  inline fun <reified T> provideValue(value: T) {
    linker.install(T::class.java, ValueFactory.of(value))
  }

  inline fun <reified T> get(linker: Linker): T {
    return linker.factoryFor(T::class.java)(linker)
  }
}
