package net.xrrocha.ogn.metadata

import net.xrrocha.ogn.metadata.ValueTypes.IntegerVT
import net.xrrocha.ogn.metadata.ValueTypes.StringVT
import net.xrrocha.ogn.metadata.ValueTypes.RealVT
import net.xrrocha.ogn.metadata.ValueTypes.DecimalVT
import net.xrrocha.ogn.metadata.ValueTypes.DateTimeVT
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.DAYS
import kotlin.test.Test
import kotlin.test.assertEquals

class StringVTTest {
  @Test
  fun formatsString() {
    val string = "string"
    assertEquals(string, StringVT.format(string))
  }

  @Test
  fun parsesString() {
    val string = "string"
    assertEquals(string, StringVT.format(string))
  }
}

class IntegerVTTest {
  @Test
  fun formatsInteger() {
    val value = 1234
    val formattedValue = "1,234"
    assertEquals(formattedValue, IntegerVT.format(value))
  }

  @Test
  fun parsesInteger() {
    val value = 1234
    val formattedValue = "1,234"
    val unformattedValue = "1234"

    assertEquals(value, IntegerVT.parse(unformattedValue))
    assertEquals(value, IntegerVT.parse(formattedValue))
  }
}

class RealVTTest {
  @Test
  fun formatsReal() {
    val value = 1234.56
    val formattedValue = "1,234.56"
    assertEquals(formattedValue, RealVT.format(value))
  }

  @Test
  fun parsesReal() {
    val value = 1234.56
    val formattedValue = "1,234.56"
    val unformattedValue = "1234.56"

    assertEquals(value, RealVT.parse(unformattedValue))
    assertEquals(value, RealVT.parse(formattedValue))
  }
}

class DecimalVTTest {
  @Test
  fun formatsDecimal() {
    val value = BigDecimal("1234.56")
    val formattedValue = "1,234.56"
    assertEquals(formattedValue, DecimalVT.format(value))
  }

  @Test
  fun parsesDecimal() {
    val value = BigDecimal("1234.56")
    val formattedValue = "1,234.56"
    val unformattedValue = "1234.56"

    assertEquals(value, DecimalVT.parse(unformattedValue))
    assertEquals(value, DecimalVT.parse(formattedValue))
  }
}

class DateTimeVTTest {
  @Test
  fun formatsDateTime() {
    val value = LocalDateTime.of(
        2018, 6, 13,
        20, 5
    )
    val formattedValue = "2018-06-13T20:05:00"
    assertEquals(formattedValue, DateTimeVT.format(value))
  }

  @Test
  fun parsesDateTime() {
    val value = LocalDateTime.of(
        2018, 6, 13,
        20, 5
    )
    val formattedValue = "2018-06-13T20:05:00"
    assertEquals(value, DateTimeVT.parse(formattedValue))

    val truncatedFormattedValue = "2018-06-13"
    val truncatedValue = value.truncatedTo(DAYS)
    assertEquals(truncatedValue, DateTimeVT.parse(truncatedFormattedValue))
  }
}