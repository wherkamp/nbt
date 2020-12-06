package trident.nbt

import trident.nbt.tag.Nbt

object NbtComparator : Comparator<Nbt> {
    override fun compare(tag1: Nbt, tag2: Nbt): Int {
        val type1 = tag1.companion.type
        val type2 = tag2.companion.type

        fun compareNames() = tag1.name.toLowerCase().compareTo(tag2.name.toLowerCase())

        return when {
            type1 == NbtType.COMPOUND -> if (type2 == NbtType.COMPOUND) compareNames() else -1
            type2 == NbtType.COMPOUND -> 1
            type1 == NbtType.LIST -> if (type2 == NbtType.LIST) compareNames() else -1
            type2 == NbtType.LIST -> 1
            else -> compareNames()
        }
    }
}