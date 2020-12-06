package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtByteArray(override val name: String, override val value: ByteArray) : NbtTag<ByteArray> {
    override val companion = Companion

    override val sizeInBytes
        get() = Int.SIZE_BYTES + value.size

    override fun ByteBuffer.serialize() {
        putInt(value.size)
        value.forEach { put(it) }
    }

    override fun copy(name: String, value: ByteArray): NbtByteArray {
        return NbtByteArray(name, value.clone())
    }

    companion object : NbtCompanion<NbtByteArray> {
        override val type = NbtType.LONG_ARRAY

        override fun ByteBuffer.deserialize(name: String): NbtByteArray {
            return NbtByteArray(name, ByteArray(int) { get() })
        }
    }
}