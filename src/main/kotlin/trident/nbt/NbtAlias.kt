package trident.nbt

import trident.nbt.tag.Nbt

interface NbtAlias<T : Nbt> {
    val nbt: T
}