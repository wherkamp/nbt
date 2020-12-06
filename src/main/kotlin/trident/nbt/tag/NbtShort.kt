package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtShort(override val name: String, override val value: Short) : NbtTag<Short> {
    override val companion = Companion

    override val sizeInBytes
        get() = Short.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        putShort(value)
    }

    override fun copy(name: String, value: Short): NbtShort {
        return NbtShort(name, value)
    }

    companion object : NbtCompanion<NbtShort> {
        override val type = NbtType.SHORT

        override fun ByteBuffer.deserialize(name: String): NbtShort {
            return NbtShort(name, short)
        }
    }
}