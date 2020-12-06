package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import java.nio.ByteBuffer

class NbtDouble(override val name: String, override val value: Double) : NbtTag<Double> {
    override val companion = Companion

    override val sizeInBytes
        get() = Long.SIZE_BYTES

    override fun ByteBuffer.serialize() {
        putDouble(value)
    }

    override fun copy(name: String, value: Double): NbtDouble {
        return NbtDouble(name, value)
    }

    companion object : NbtCompanion<NbtDouble> {
        override val type = NbtType.FLOAT

        override fun ByteBuffer.deserialize(name: String): NbtDouble {
            return NbtDouble(name, double)
        }
    }
}