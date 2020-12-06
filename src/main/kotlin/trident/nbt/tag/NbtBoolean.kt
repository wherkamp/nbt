package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtBoolean(override val name: String, override val value: Boolean) : NbtTag<Boolean> {
    override val companion = Companion

    override val sizeInBytes
        get() = Int.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        put((if (value) 1 else 0).toByte())
    }

    override fun copy(name: String, value: Boolean): NbtBoolean {
        return NbtBoolean(name, value)
    }

    companion object : NbtCompanion<NbtBoolean> {
        override val type = NbtType.BOOLEAN

        override fun ByteBuffer.deserialize(name: String): NbtBoolean {
            return NbtBoolean(name, get() == 1.toByte())
        }
    }
}