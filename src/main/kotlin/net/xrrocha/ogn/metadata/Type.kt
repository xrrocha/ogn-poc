package net.xrrocha.ogn.metadata

interface Named {
  val name: String
}

abstract class Type(override val name: String) : Named {
  init {
    Types.newType(this)
  }
}

object Types {

  private val types = hashMapOf<String, Type>()

  fun newType(type: Type) = types.put(type.name, type)

  fun getType(typeName: String) = types[typeName]

  fun allTypes() = listOf(*types.values.toTypedArray())
}
