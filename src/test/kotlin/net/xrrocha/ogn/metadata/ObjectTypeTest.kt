package net.xrrocha.ogn.metadata

import kotlin.test.Test
import net.xrrocha.ogn.metadata.ValueTypes.IntegerVT
import net.xrrocha.ogn.metadata.ValueTypes.StringVT
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ObjectTypeTest {
  @Test
  fun retrievesAttributeByName() {
    val objectType = ObjectType("dummy",
        listOf(
            Attribute("id", IntegerVT, Cardinality.ONE),
            Attribute("name", StringVT, Cardinality.ONE)
        ))
    assertNotNull(objectType.attribute("id"))
    assertNotNull(objectType.attribute("name"))
    assertNull(objectType.attribute("nonExistent"))
  }
}