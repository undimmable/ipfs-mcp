package org.soma.storages

import io.ipfs.api.IPFS
import io.ipfs.api.NamedStreamable
import io.ipfs.multihash.Multihash
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

open class SecureIpfsStorage {
    private val ipfs: IPFS
    private val id: String

    constructor(token: String) {
        this.id = "default"
        this.ipfs = IPFS("/ip4/127.0.0.1/tcp/5001")
    }

    constructor(id: String, token: String) {
        this.id = id
        this.ipfs = IPFS("/ip4/127.0.0.1/tcp/5001")
    }

    constructor(
        id: String,
        host: String,
        port: Int,
        pinningToken: String
    ) {
        this.id = id
        this.ipfs = IPFS(host + ":" + port)
    }

    open fun upload(fileName: String, content: String): String {
        try {
            val file = NamedStreamable.ByteArrayWrapper(fileName, content.toByteArray(StandardCharsets.UTF_8))
            val addResult = ipfs.add(file).get(0)
            return addResult.hash.toString()
        } catch (e: IOException) {
            throw RuntimeException("Failed to upload file to IPFS", e)
        }
    }

    open fun download(hash: String): String {
        try {
            val fileHash = Multihash.fromBase58(hash)
            val data = ipfs.cat(fileHash)
            return String(data, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            throw RuntimeException("Failed to download file from IPFS", e)
        }
    }

    open fun delete(hash: String): Boolean {
        try {
            val fileHash = Multihash.fromBase58(hash)
            ipfs.pin.rm(fileHash)
            return true
        } catch (e: IOException) {
            throw RuntimeException("Failed to delete file from IPFS", e)
        }
    }

    open fun listFiles(): List<String> {
        try {
            val pins = ipfs.pin.ls(IPFS.PinType.all)
            return pins.keys.stream()
                .map<String?> { obj: Multihash? -> obj.toString() }
                .collect(Collectors.toList())
        } catch (e: IOException) {
            throw RuntimeException("Failed to list files from IPFS", e)
        }
    }

    fun getFileAsOutputStream(ipfsPath: String): OutputStream {
        return ByteArrayOutputStream()
    }
}
