package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtInt(override val name: String, override val value: Int) : NbtTag<Int> {
    override val companion = Companion

    override val sizeInBytes
        get() = Int.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        putInt(value)
    }

    override fun copy(name: String, value: Int): NbtInt {
        return NbtInt(name, value)
    }

    companion object : NbtCompanion<NbtInt> {
        override val type = NbtType.INT

        override fun ByteBuffer.deserialize(name: String): NbtInt {
            return NbtInt(name, int)
        }
    }
}