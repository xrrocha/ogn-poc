package net.xrrocha.ogn.metadata

import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQuery
import java.util.regex.Pattern

interface ValueTypeFormat<T> {
  fun format(value: T): String
  fun parse(string: String): T
}

interface Validator<T> {
  fun validate(value: T)
}

data class NullValidator<T>(val errorMessage: String? = null) : Validator<T> {
  override fun validate(value: T) {}
}

interface ValueType<T> : Type, ValueTypeFormat<T>, Validator<T>

data class SimpleValueType<T>(override val name: String,
                              val format: ValueTypeFormat<T>,
                              val validator: Validator<T> = NullValidator())
  : BaseType(name),
    ValueType<T>,
    ValueTypeFormat<T> by format,
    Validator<T> by validator

data class RegexString(override val name: String,
                       val regex: String,
                       val errorMessage: String) : ValueType<String> {

  val pattern by lazy { Pattern.compile(regex) }

  override fun format(value: String): String = value

  override fun parse(string: String): String = string

  override fun validate(value: String) {
    if (!pattern.matcher(value).matches()) {
      throw IllegalArgumentException(errorMessage);
    }
  }
}

object ValueTypes {

  // TODO(ricardo) Add UUID value type

  val StringVT = SimpleValueType(
      name = "String",
      format = object : ValueTypeFormat<String> {
        override fun format(value: String): String = value
        override fun parse(string: String): String = string
      })

  val IntegerVT = SimpleValueType(
      name = "Integer",
      format = DecimalValueTypeFormat(
          pattern = "###,###,###,###",
          conversion = { it.toInt() }))

  val RealVT = SimpleValueType(
      name = "Real",
      format = DecimalValueTypeFormat(
          pattern = "###,###,###,###.####",
          conversion = { it.toDouble() }))

  val DecimalVT = SimpleValueType(
      name = "Decimal",
      format = DecimalValueTypeFormat(
          pattern = "###,###,###,###.####",
          conversion = { it as BigDecimal },
          bigDecimal = true))

  val DateTimeVT = SimpleValueType(
      name = "DateTime",
      format = object : ValueTypeFormat<LocalDateTime> {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T'HH:mm:ss]")

        override fun format(value: LocalDateTime): String = value.format(formatter)

        override fun parse(string: String): LocalDateTime {
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


