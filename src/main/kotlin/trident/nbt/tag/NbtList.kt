package trident.nbt.tag

import trident.nbt.NbtCompanion
import trident.nbt.NbtType
import trident.nbt.tag.NbtList.Companion.deserialize
import java.nio.ByteBuffer

class NbtList<T : NbtTag<*>>(val type: NbtType, override val name: String, override val value: List<T>) : NbtTag<List<T>> {
    override val companion = object : NbtCompanion<NbtList<T>> {
        override val type = NbtType.LIST

        override fun ByteBuffer.deserialize(name: String): NbtList<T> {
            val id = NbtType[get().toInt()]
            return NbtList(id, name, List(int) { id.read(this, it.toString()) as T })
        }
    }

    override val sizeInBytes
        get() = Byte.SIZE_BYTES + Int.SIZE_BYTES + value.sumBy { it.sizeInBytes }

    override fun ByteBuffer.serialize() {
        put(type.id.toByte())
        putInt(value.size)
        value.forEach { it.run { serialize() } }
    }

    override fun copy(name: String, value: List<T>): NbtList<T> {
        return NbtList(type, name, value.toList())
    }

    companion object : NbtCompanion<NbtList<*>> {
        override val type = NbtType.LIST

        override fun ByteBuffer.deserialize(name: String): NbtList<*> {
            val id = NbtType[get().toInt()]
            return NbtList(id, name, List(int) { id.read(this, it.toString()) })
        }
    }

    init {
        require(value.all { it.companion.type == type }) {
            "All values of list must be the same type."
        }
    }
}