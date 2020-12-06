package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtLong(override val name: String, override val value: Long) : NbtTag<Long> {
    override val companion = Companion

    override val sizeInBytes
        get() = Long.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        putLong(value)
    }

    override fun copy(name: String, value: Long): NbtLong {
        return NbtLong(name, value)
    }

    companion object : NbtCompanion<NbtLong> {
        override val type = NbtType.LONG

        override fun ByteBuffer.deserialize(name: String): NbtLong {
            return NbtLong(name, long)
        }
    }
}