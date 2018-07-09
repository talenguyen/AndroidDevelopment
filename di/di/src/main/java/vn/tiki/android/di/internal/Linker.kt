package vn.tiki.android.di.internal

class Linker internal constructor(internal val factories: MutableMap<Class<*>, Factory<*>> = mutableMapOf()) {

  fun <T> install(key: Class<T>, factory: Factory<T>) {
    factories[key] = factory
  }

  @Suppress("UNCHECKED_CAST")
  fun <T> factoryFor(key: Class<T>): Factory<T> {
    return if (factories.containsKey(key)) {
      factories[key] as Factory<T>
    } else {
      throw IllegalArgumentException("no factory for ${key.name}")
    }
  }

  operator fun plus(other: Linker): Linker {
    val mergedFactories = this.factories + other.factories
    return when (mergedFactories) {
      is MutableMap -> Linker(mergedFactories)
      else -> Linker(mergedFactories.toMutableMap())
    }
  }

  internal fun dump() {
    for (key in factories.keys) {
      println("Found: factory for $key")
    }
  }
}

