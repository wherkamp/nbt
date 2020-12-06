package trident.nbt.tag

import trident.nbt.*
import java.nio.ByteBuffer

class NbtString(override val name: String, override val value: String) : NbtTag<String> {
    override val companion = Companion

    override val sizeInBytes= Short.SIZE_BYTES + value.toByteArray().size

    override fun ByteBuffer.serialize() {
        putString(value)
    }

    override fun copy(name: String, value: String): NbtString {
        return NbtString(name, value)
    }

    companion object : NbtCompanion<NbtString> {
        override val type = NbtType.STRING

        override fun ByteBuffer.deserialize(name: String): NbtString {
            return NbtString(name, string)
        }
    }
}