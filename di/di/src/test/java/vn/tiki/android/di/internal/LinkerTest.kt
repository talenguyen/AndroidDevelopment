package vn.tiki.android.di.internal

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LinkerTest {

  private class A
  private class B

  private val aFactory: (Linker) -> A = { A() }
  private val bFactory: (Linker) -> B = { B() }

  @Test
  fun `plus should merged`() {

    val linker1 = Linker(
        mutableMapOf(
            A::class.java to aFactory
        ))

    val linker2 = Linker(
        mutableMapOf(
            B::class.java to bFactory
        ))

    val linker3 = linker1 + linker2

    assertThat(linker3.factories.size).isEqualTo(2)
    assertThat(linker3.factoryFor(A::class.java)).isEqualTo(aFactory)
    assertThat(linker3.factoryFor(B::class.java)).isEqualTo(bFactory)
  }

  @Test
  fun `plus should replace`() {

    val newAFactory: (Linker) -> A = { A() }

    val linker1 = Linker(
        mutableMapOf(
            A::class.java to aFactory
        ))

    val linker2 = Linker(
        mutableMapOf(
            A::class.java to newAFactory,
            B::class.java to bFactory
        ))

    val linker3 = linker1 + linker2

    assertThat(linker3.factories.size).isEqualTo(2)
    assertThat(linker3.factoryFor(A::class.java)).isEqualTo(newAFactory)
    assertThat(linker3.factoryFor(B::class.java)).isEqualTo(bFactory)
  }
}