package net.xrrocha.ogn.metadata

import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQuery

interface ValueTypeFormat<T> {
  fun format(value: T): String
  fun parse(string: String): T
}

data class ValueType<T>(override val name: String,
                        val format: ValueTypeFormat<T>)
  : Type(name), ValueTypeFormat<T> by format

object ValueTypes {

  val StringVT = ValueType(
      name = "String",
      format = object : ValueTypeFormat<String> {
        override fun format(value: String): String = value
        override fun parse(string: String): String = string
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
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T'HH:mm:ss]")

        override fun format(value: LocalDateTime): String = value.format(formatter)
        override fun parse(string: String): LocalDateTime {
          @Suppress("UNCHECKED_CAST")
          val temporalAccessor = formatter.parseBest(string,
              TemporalQuery<LocalDateTime> { LocalDateTime.from(it) },
              TemporalQuery<LocalDate> { LocalDate.from(it) })

          return when (temporalAccessor) {
            is LocalDateTime -> temporalAccessor
            is LocalDate -> temporalAccessor.atStartOfDay()
            else ->
              throw IllegalStateException("Unexpected type: ${temporalAccessor.javaClass.name}")
          }

        }
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

  override fun format(value: T): String = decimalFormat.format(value)

  override fun parse(string: String): T = conversion(decimalFormat.parse(string))
}


