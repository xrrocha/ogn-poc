package net.xrrocha.ogn.metadata

interface Type {
  val name: String
}

abstract class BaseType(override val name: String) : Type {

  init {
    @Suppress("LeakingThisInConstructor")
    Types.newType(this)
  }
}

object Types {

  private val types = hashMapOf<String, BaseType>()

  fun newType(type: BaseType) = types.put(type.name, type)

  fun getType(typeName: String) = types[typeName]

  fun allTypes() = listOf(*types.values.toTypedArray())
}
