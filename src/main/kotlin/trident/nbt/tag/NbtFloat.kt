package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtFloat(override val name: String, override val value: Float) : NbtTag<Float> {
    override val companion = Companion

    override val sizeInBytes
        get() = Int.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        putFloat(value)
    }

    override fun copy(name: String, value: Float): NbtFloat {
        return NbtFloat(name, value)
    }

    companion object : NbtCompanion<NbtFloat> {
        override val type = NbtType.FLOAT

        override fun ByteBuffer.deserialize(name: String): NbtFloat {
            return NbtFloat(name, float)
        }
    }
}