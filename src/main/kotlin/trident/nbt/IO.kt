package trident.nbt

import trident.nbt.tag.Nbt
import trident.nbt.tag.NbtCompound
import java.io.*
import java.nio.ByteBuffer
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.InflaterInputStream
import java.util.zip.InflaterOutputStream

enum class Compression(
        private val makeIn: ((InputStream) -> InputStream)?,
        private val makeOut: ((OutputStream) -> OutputStream)?
) {
    NONE(null, null),
    GZIP(::GZIPInputStream, ::GZIPOutputStream),
    ZLIB(::InflaterInputStream, ::InflaterOutputStream);

    fun makeInputStream(inputStream: InputStream): InputStream {
        return makeIn?.invoke(inputStream) ?: inputStream
    }

    fun makeOutputStream(outputStream: OutputStream): OutputStream {
        return makeOut?.invoke(outputStream) ?: outputStream
    }

    companion object {
        private val values = values()

        operator fun get(id: Int) = values.getOrNull(id) ?: throw IllegalArgumentException("Invalid compression method ID: $id.")
    }
}

fun BufferedInputStream.read(compression: Compression = Compression.NONE): Nbt {
    val bytes = compression.makeInputStream(this).readBytes()

    return ByteBuffer.wrap(bytes).readNbt()
}

fun read(file: File, compression: Compression = Compression.NONE): NbtCompound {
    return file.inputStream().buffered().read(compression) as NbtCompound
}

fun NbtCompound.write(outputStream: BufferedOutputStream, compression: Compression = Compression.NONE) {
    val tagCompoundSize = name.toByteArray().size + sizeInBytes
    val byteBuffer = ByteBuffer.allocate(Byte.SIZE_BYTES + Short.SIZE_BYTES + tagCompoundSize)
    val byteArray = byteBuffer.also { writeRoot(it) }.array()

    compression.makeOutputStream(outputStream).use {
        it.write(byteArray)
    }
}

fun NbtCompound.write(file: File, compression: Compression = Compression.NONE) {
    write(file.outputStream().buffered(), compression)
}

private fun ByteBuffer.readNbt(): Nbt {
    val id = NbtType[get().toInt()]
    val name = string

    return if (id == NbtType.COMPOUND) {
        NbtCompound.run { deserialize(name) }
    } else {
        id.read(this, name)
    }
}
