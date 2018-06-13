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

  private val types = HashMap<String, Type>()

  fun newType(type: Type) = types.put(type.name, type)
}
