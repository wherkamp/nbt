package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtLongArray(override val name: String, override val value: LongArray) : NbtTag<LongArray> {
    override val companion = Companion

    override val sizeInBytes
        get() = Int.SIZE_BYTES + value.size * Long.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        putInt(value.size)
        value.forEach { putLong(it) }
    }

    override fun copy(name: String, value: LongArray): NbtLongArray {
        return NbtLongArray(name, value.clone())
    }

    companion object : NbtCompanion<NbtLongArray> {
        override val type = NbtType.LONG_ARRAY

        override fun ByteBuffer.deserialize(name: String): NbtLongArray {
            return NbtLongArray(name, LongArray(int) { long })
        }
    }
}