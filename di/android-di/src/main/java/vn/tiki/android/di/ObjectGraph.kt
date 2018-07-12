package vn.tiki.android.androiddi

import vn.tiki.android.androiddi.internal.Linker

class ObjectGraph private constructor(private val linker: Linker) {

  companion object {

    fun create(modules: List<Module>): ObjectGraph {

      if (modules.isEmpty()) {
        throw IllegalArgumentException("modules must not empty")
      }

      return when (modules.size) {
        1 -> ObjectGraph(modules[0].linker)
        else -> {
          val linker = makeLinker(modules)

          ObjectGraph(linker)
        }
      }
    }

    internal fun makeLinker(modules: List<Module>): Linker {
      return modules
        .map { it.linker }
        .reduce { acc, linker -> acc + linker }
    }
  }

  operator fun <T> get(key: Class<T>): T {
    return linker.factoryFor(key)(linker)
  }

  fun plus(modules: List<Module>): ObjectGraph {
    val newLinker = linker + makeLinker(modules)
    return ObjectGraph(newLinker)
  }

  fun dump() {
    linker.dump()
  }
}
