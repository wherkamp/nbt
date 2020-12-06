package trident.nbt

import trident.nbt.tag.NbtTag
import java.nio.ByteBuffer

interface NbtCompanion<T : NbtTag<*>> {
    val type: NbtType

    fun ByteBuffer.deserialize(name: String): T
}