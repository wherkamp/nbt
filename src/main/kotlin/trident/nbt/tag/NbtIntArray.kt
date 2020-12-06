package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtIntArray(override val name: String, override val value: IntArray) : NbtTag<IntArray> {
    override val companion = Companion

    override val sizeInBytes
        get() = Int.SIZE_BYTES + value.size * Int.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        putInt(value.size)
        value.forEach { putInt(it) }
    }

    override fun copy(name: String, value: IntArray): NbtIntArray {
        return NbtIntArray(name, value.clone())
    }

    companion object : NbtCompanion<NbtIntArray> {
        override val type = NbtType.INT_ARRAY

        override fun ByteBuffer.deserialize(name: String): NbtIntArray {
            return NbtIntArray(name, IntArray(int) { int })
        }
    }
}