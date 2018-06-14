package net.xrrocha.ogn.metadata

import java.util.*

data class Enum(override val name: String,
                val values: SortedSet<String>) : BaseType(name)
