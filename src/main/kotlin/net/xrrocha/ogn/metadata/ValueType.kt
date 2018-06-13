package net.xrrocha.ogn.metadata

import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface ValueTypeFormat<T> {
  fun format(t: T): String
  fun parse(s: String): T
}

data class ValueType<T>(override val name: String,
                        val format: ValueTypeFormat<T>)
  : Type(name), ValueTypeFormat<T> by format

object ValueTypes {

  val StringVT = ValueType(
      name = "String",
      format = object : ValueTypeFormat<String> {
        override fun format(t: String): String = t
        override fun parse(s: String): String = s
      })

  val IntegerVT = ValueType(
      name = "Integer",
      format = DecimalValueTypeFormat(
          pattern = "###,###,###,###",
          conversion = { it.toInt() }))

  val RealVT = ValueType(
      name = "Real",
      format = DecimalValueTypeFormat(
          pattern = "###,###,###,###.####",
          conversion = { it.toDouble() }))

  val DecimalVT = ValueType(
      name = "Decimal",
      format = DecimalValueTypeFormat(
          pattern = "###,###,###,###.####",
          conversion = { it as BigDecimal },
          bigDecimal = true))

  val DateTimeVT = ValueType(
      name = "DateTime",
      format = object : ValueTypeFormat<LocalDateTime> {
        val formatter = DateTimeFormatter.ISO_DATE_TIME

        override fun format(t: LocalDateTime): String = t.format(formatter)
        override fun parse(s: String): LocalDateTime = LocalDateTime.parse(s, formatter)
      }
  )
}

data class DecimalValueTypeFormat<T : Number>(val pattern: String,
                                              val conversion: (Number) -> T,
                                              val bigDecimal: Boolean = false) :
    ValueTypeFormat<T> {

  private val decimalFormat by lazy {
    val format = DecimalFormat(pattern)
    format.isParseBigDecimal = bigDecimal
    format
  }

  override fun format(t: T): String = decimalFormat.format(t)

  override fun parse(s: String): T = conversion(decimalFormat.parse(s))
}


