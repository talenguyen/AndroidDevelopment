package vn.tiki.android.collection

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ViewTypeManagerTest {

  private lateinit var tested: ViewTypeManager

  @Before
  fun setUp() {
    tested = ViewTypeManager()
  }

  @Test
  fun testGetViewType() {
    assertThat(tested.getViewType(String::class.java)).isEqualTo(1)
    assertThat(tested.getViewType(Int::class.java)).isEqualTo(2)
    assertThat(tested.getViewType(Int::class.java)).isEqualTo(2)
    assertThat(tested.getViewType(String::class.java)).isEqualTo(1)
  }
}
