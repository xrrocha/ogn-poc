package net.xrrocha.ogn.metadata

data class ObjectType(override val name: String,
                      val attributes: List<Attribute>) : Type(name)

enum class Cardinality {
  ONE, MANY
}

data class Attribute(override val name: String,
                     val type: Type,
                     val cardinality: Cardinality,
                     val mandatory: Boolean) : Named
