package vn.tiki.android.androiddi

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ObjectGraphTest {

  private class A
  private class B

  @Test
  fun `makeLinker should merge Linker`() {
    val module1 = Module()
        .apply {
          provide { A() }
        }

    val module2 = Module()
        .apply {
          provide { B() }
        }

    val linker = ObjectGraph.makeLinker(
        listOf(
            module1,
            module2
        )
    )
    assertThat(linker.factories.size).isEqualTo(2)
    assertThat(linker.factoryFor(A::class.java).invoke(linker)).isInstanceOf(A::class.java)
    assertThat(linker.factoryFor(B::class.java).invoke(linker)).isInstanceOf(B::class.java)
  }
}
