package org.mockito

import com.google.common.truth.Truth.assertThat
import org.junit.*

interface Developer {
  fun say(): String
}

class ExtensionsTest {

  @Test
  fun testMock() {
    val developer = mock<Developer>()
    assertThat(developer).isNotNull()
    assertThat(developer).isInstanceOf(Developer::class.java)
  }

  @Test
  fun testWhenReturn() {
    val message = "I am developer"
    val developer = mock<Developer> {
      on(say()) doReturn message
    }
    assertThat(developer.say()).isEqualTo(message)
  }
}
