package trident.nbt

import trident.nbt.tag.*

@DslMarker
private annotation class NbtDsl

@JvmName("listByte")
fun List<Byte>.toNbt(name: String): NbtList<NbtByte> {
    return NbtList(NbtType.BYTE, name, mapIndexed {
        index, byte -> NbtByte(index.toString(), byte)
    })
}

@JvmName("listByteArray")
fun List<ByteArray>.toNbt(name: String): NbtList<NbtByteArray> {
    return NbtList(NbtType.BYTE_ARRAY, name, mapIndexed {
        index, byteArray -> NbtByteArray(index.toString(), byteArray)
    })
}

@JvmName("listNbtCompound")
fun List<NbtCompound>.toNbt(name: String): NbtList<NbtCompound> {
    return NbtList(NbtType.COMPOUND, name, this)
}

@JvmName("listDouble")
fun List<Double>.toNbt(name: String): NbtList<NbtDouble> {
    return NbtList(NbtType.DOUBLE, name, mapIndexed {
        index, double -> NbtDouble(index.toString(), double)
    })
}

@JvmName("listFloat")
fun List<Float>.toNbt(name: String): NbtList<NbtFloat> {
    return NbtList(NbtType.FLOAT, name, mapIndexed {
        index, float -> NbtFloat(index.toString(), float)
    })
}

@JvmName("listInt")
fun List<Int>.toNbt(name: String): NbtList<NbtInt> {
    return NbtList(NbtType.INT, name, mapIndexed {
        index, int -> NbtInt(index.toString(), int)
    })
}

@JvmName("listIntArray")
fun List<IntArray>.toNbt(name: String): NbtList<NbtIntArray> {
    return NbtList(NbtType.INT_ARRAY, name, mapIndexed {
        index, intArray -> NbtIntArray(index.toString(), intArray)
    })
}

@JvmName("listList")
fun <T : Nbt> List<NbtList<T>>.toNbt(name: String): NbtList<NbtList<T>> {
    return NbtList(NbtType.BYTE, name, this)
}

@JvmName("listLong")
fun List<Long>.toNbt(name: String): NbtList<NbtLong> {
    return NbtList(NbtType.LONG, name, mapIndexed {
        index, long -> NbtLong(index.toString(), long)
    })
}

@JvmName("listLongArray")
fun List<LongArray>.toNbt(name: String): NbtList<NbtLongArray> {
    return NbtList(NbtType.BYTE_ARRAY, name, mapIndexed {
        index, longArray -> NbtLongArray(index.toString(), longArray)
    })
}

@JvmName("listShort")
fun List<Short>.toNbt(name: String): NbtList<NbtShort> {
    return NbtList(NbtType.LONG, name, mapIndexed {
        index, short -> NbtShort(index.toString(), short)
    })
}

@JvmName("listString")
fun List<String>.toNbt(name: String): NbtList<NbtString> {
    return NbtList(NbtType.LONG, name, mapIndexed {
        index, string -> NbtString(index.toString(), string)
    })
}

@NbtDsl
inline fun nbt(name: String, builder: NbtCompound.() -> Unit): NbtCompound {
    return NbtCompound(name, emptySet()).apply(builder)
}