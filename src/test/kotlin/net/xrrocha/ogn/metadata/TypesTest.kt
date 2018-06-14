package net.xrrocha.ogn.metadata

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TypesTest {
  @Test
  fun addsTypes() {

    val allTypes = Types.allTypes()

    val dummyName = "dummyType"
    assertNull(Types.getType(dummyName))

    val dummyType = object: BaseType(dummyName){}
    assertEquals(dummyType, Types.getType(dummyName))
    assertEquals(allTypes.size + 1, Types.allTypes().size)
  }
}