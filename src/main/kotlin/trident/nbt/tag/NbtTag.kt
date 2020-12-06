package trident.nbt.tag

import trident.nbt.NbtCompanion
import java.nio.ByteBuffer

typealias Nbt = NbtTag<out Any>

interface NbtTag<T : Any> {
    val name: String

    val value: T

    val sizeInBytes: Int

    val companion: NbtCompanion<out NbtTag<T>>

    fun ByteBuffer.serialize()

    fun copy(name: String = this.name, value: T = this.value): NbtTag<T>
}

val NbtTag<*>.prefix
    get() = "${if (name.isEmpty()) "" else "$name: "}${companion.type} = "
