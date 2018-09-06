package vn.tiki.android.di

import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.fail
import org.junit.*

class TikiDiTest {

  private val hiModule = module {
    provide { "Hi" }
  }

  private val helloModule = module {
    provide { "Hello" }
  }

  @Test
  fun `test open and close scope`() {
    var message: String? = null

    TikiDi.openScope(hiModule)
    message = TikiDi.inject()
    assertThat(message).isEqualTo("Hi")

    TikiDi.openScope(helloModule)
    message = TikiDi.inject()
    assertThat(message).isEqualTo("Hello")

    TikiDi.closeScope()
    message = TikiDi.inject()
    assertThat(message).isEqualTo("Hi")

    TikiDi.closeScope()
    try {
      TikiDi.inject<String>()
      fail()
    } catch (_: Exception) {
      // ignore
    }
  }
}