package net.xrrocha.ogn.metadata

abstract class Type(open val name: String) {

  init {
    @Suppress("LeakingThisInConstructor")
    Types.newType(this)
  }
}

object Types {

  private val types = hashMapOf<String, Type>()

  fun newType(type: Type) = types.put(type.name, type)

  fun getType(typeName: String) = types[typeName]

  fun allTypes() = listOf(*types.values.toTypedArray())
}
