package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtByte(override val name: String, override val value: Byte) : NbtTag<Byte> {
    override val companion = Companion

    override val sizeInBytes
        get() = Byte.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        put(value)
    }

    override fun copy(name: String, value: Byte): NbtByte {
        return NbtByte(name, value)
    }

    companion object : NbtCompanion<NbtByte> {
        override val type = NbtType.BYTE

        override fun ByteBuffer.deserialize(name: String): NbtByte {
            return NbtByte(name, get())
        }
    }
}