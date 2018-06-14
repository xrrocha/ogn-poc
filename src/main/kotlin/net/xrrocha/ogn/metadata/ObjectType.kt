package net.xrrocha.ogn.metadata

data class ObjectType(override val name: String,
                      val attributes: List<Attribute>) : Type(name) {
  private val attributeMap = attributes.map { Pair(it.name, it) }.toMap()

  fun attribute(name: String) = attributeMap.get(name)
}

enum class Cardinality {
  ONE, MANY
}

data class Attribute(override val name: String,
                     val type: Type,
                     val cardinality: Cardinality,
                     val mandatory: Boolean = true) : Named
