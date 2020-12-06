package trident.nbt.tag

import trident.nbt.*
import java.nio.ByteBuffer

class NbtCompound(
        override var name: String,
        value: Iterable<Nbt>
) : NbtTag<Set<Nbt>> {
    override val value = value.toMutableSet()

    override val sizeInBytes
        get() = value.sumBy { tag ->
            Byte.SIZE_BYTES + (Short.SIZE_BYTES + name.toByteArray().size) + tag.sizeInBytes
        } + Byte.SIZE_BYTES

    override val companion = Companion

    override fun ByteBuffer.serialize() {
        value.forEach { tag ->
            put(tag.companion.type.id.toByte())
            putString(name)

            tag.apply {
                serialize()
            }
        }

        put(0.toByte())
    }

    override fun copy(name: String, value: Set<Nbt>): NbtCompound {
        return NbtCompound(name, value.map { it.copy() }.toSet())
    }

    internal fun writeRoot(byteBuffer: ByteBuffer) {
        byteBuffer.put(NbtType.COMPOUND.id.toByte())
        byteBuffer.putString(name)

        byteBuffer.serialize()
    }

    override fun toString() = buildString {
        append("$prefix{")

        if (value.isNotEmpty()) {
            appendLine()
            append('\t')
            appendln(
                    value.sortedWith(NbtComparator).joinToString(",\n\t") { tag ->
                        when (tag.companion.type) {
                            NbtType.COMPOUND, NbtType.LIST -> tag.toString().replace("\n", "\n\t")
                            else -> tag.toString()
                        }
                    }
            )
        }

        append("}")
    }

    operator fun Nbt.unaryPlus() {
        this@NbtCompound.value.add(this)
    }

    operator fun String.minus(value: Byte) {
        this@NbtCompound.value.add(NbtByte(this, value))
    }

    operator fun String.minus(value: ByteArray) {
        this@NbtCompound.value.add(NbtByteArray(this, value))
    }

    operator fun String.minus(value: Double) {
        this@NbtCompound.value.add(NbtDouble(this, value))
    }

    operator fun String.minus(value: Float) {
        this@NbtCompound.value.add(NbtFloat(this, value))
    }

    operator fun String.minus(value: Int) {
        this@NbtCompound.value.add(NbtInt(this, value))
    }

    operator fun String.minus(value: IntArray) {
        this@NbtCompound.value.add(NbtIntArray(this, value))
    }

    operator fun String.minus(value: Long) {
        this@NbtCompound.value.add(NbtLong(this, value))
    }

    operator fun String.minus(value: LongArray) {
        this@NbtCompound.value.add(NbtLongArray(this, value))
    }

    operator fun String.minus(value: Short) {
        this@NbtCompound.value.add(NbtShort(this, value))
    }

    operator fun String.minus(value: String) {
        this@NbtCompound.value.add(NbtString(this, value))
    }

    operator fun String.minus(boolean: Boolean) {
        this@NbtCompound.value.add(NbtBoolean(this, boolean))
    }

    inline fun <reified T : Any> NbtCompound.get(tag: NbtCompanion<out NbtTag<T>>, name: String): T {
        if (true is T) return (value.find { it.name == name }!!.value as T == 1) as T

        return value.find { it.name == name }!!.value as T
    }

    companion object : NbtCompanion<NbtCompound> {
        override val type = NbtType.COMPOUND

        override fun ByteBuffer.deserialize(name: String): NbtCompound {
            val value = mutableSetOf<Nbt>()

            var nextId: Int
            while (true) {
                nextId = get().toInt()

                if (nextId == 0) break

                val nextName = string
                val nextTag = NbtType[nextId].read(this, nextName)

                value.add(nextTag)
            }

            return NbtCompound(name, value)
        }
    }
}