package trident.nbt

import trident.nbt.tag.*
import trident.nbt.tag.NbtCompound.Companion.deserialize
import java.nio.ByteBuffer

enum class NbtType(private val string: String) {
    BYTE("Byte"),
    SHORT("Short"),
    INT("Int"),
    LONG("Long"),
    FLOAT("Float"),
    DOUBLE("Double"),
    BYTE_ARRAY("ByteArray"),
    STRING("String"),
    LIST("List"),
    COMPOUND("Compound"),
    INT_ARRAY("IntArray"),
    LONG_ARRAY("LongArray"),
    BOOLEAN("Boolean");

    inline val id
        get() = ordinal + 1

    override fun toString() = name

    fun read(byteBuffer: ByteBuffer, name: String): Nbt = when (this) {
        BYTE -> NbtByte.run { byteBuffer.deserialize(name) }
        SHORT -> NbtShort.run { byteBuffer.deserialize(name) }
        INT -> NbtInt.run { byteBuffer.deserialize(name) }
        LONG -> NbtInt.run { byteBuffer.deserialize(name) }
        FLOAT -> NbtFloat.run { byteBuffer.deserialize(name) }
        DOUBLE -> NbtDouble.run { byteBuffer.deserialize(name) }
        BYTE_ARRAY -> NbtByteArray.run { byteBuffer.deserialize(name) }
        STRING -> NbtString.run { byteBuffer.deserialize(name) }
        LIST -> NbtList.run { byteBuffer.deserialize(name) }
        COMPOUND -> NbtCompound.run { byteBuffer.deserialize(name) }
        INT_ARRAY -> NbtIntArray.run { byteBuffer.deserialize(name) }
        LONG_ARRAY -> NbtLongArray.run { byteBuffer.deserialize(name) }
        BOOLEAN -> NbtBoolean.run { byteBuffer.deserialize(name) }
    }

    companion object {
        operator fun get(id: Int) = values().getOrNull(id - 1) ?: throw IllegalArgumentException("Invalid tag ID: $id.")
    }
}