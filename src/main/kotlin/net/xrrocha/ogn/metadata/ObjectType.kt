package net.xrrocha.ogn.metadata

data class ObjectType(override val name: String,
                      val attributes: List<Attribute>) : Type(name) {
  private val attributeMap by lazy {
    attributes.map { Pair(it.name, it) }.toMap()
  }

  fun attribute(name: String) = attributeMap[name]
}

enum class Cardinality {
  ONE, MANY
}

data class Attribute(val name: String,
                     val type: Type,
                     val cardinality: Cardinality,
                     val mandatory: Boolean = true)
