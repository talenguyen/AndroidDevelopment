package vn.tiki.android.di

@Suppress("MemberVisibilityCanBePrivate")

object TikiDi {

  @PublishedApi
  internal val objectGraphs = mutableListOf<ObjectGraph>()

  fun openScope(vararg modules: Module) {
    require(modules.isNotEmpty()) { "modules must not be empty" }

    val objectGraph: ObjectGraph = if (objectGraphs.isEmpty()) {
      ObjectGraph.create(modules.asList())
    } else {
      objectGraphs.last().plus(modules.asList())
    }

    objectGraphs.add(objectGraph)
  }

  fun closeScope() {
    if (objectGraphs.isEmpty()) {
      return
    }
    objectGraphs.removeAt(objectGraphs.size - 1)
  }

  inline fun <reified T> get(): T {
    val objectGraph = objectGraphs.lastOrNull()
      ?: throw IllegalStateException("no scope found. You must open scope first")

    return objectGraph[T::class.java]
  }

  inline fun <reified T> inject(): Lazy<T> = lazy { get<T>() }
}

fun module(block: Module.() -> Unit) = Module().apply(block)
