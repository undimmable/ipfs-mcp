package org.soma.server

import org.soma.storages.IpfsStorageFactory
import org.soma.storages.SecureIpfsStorage

/**
 * IPFS server implementation.
 * This server provides access to IPFS storage.
 */
class IpfsServer {
    private val ipfsStorageFactory = IpfsStorageFactory()

    /**
     * Starts the IPFS server.
     */
    fun start() {
        println("IPFS server started.")
    }

    /**
     * Creates a new IPFS resource with the given ID.
     *
     * @param resourceId The ID of the resource to create.
     * @return The created IPFS resource.
     */
    fun createResource(resourceId: String): IpfsResource {
        val storage = ipfsStorageFactory.create(resourceId, System.getenv("IPFS_TOKEN") ?: "")
        return IpfsResource(storage)
    }
}

/**
 * IPFS resource implementation.
 * This class provides access to IPFS storage operations.
 *
 * @param storage The IPFS storage to use.
 */
class IpfsResource(private val storage: SecureIpfsStorage) {
    /**
     * Uploads a file to IPFS.
     *
     * @param fileName The name of the file to upload.
     * @param content The content of the file to upload.
     * @return The hash of the uploaded file.
     */
    fun upload(fileName: String, content: String): String {
        return storage.upload(fileName, content)
    }

    /**
     * Downloads a file from IPFS.
     *
     * @param hash The hash of the file to download.
     * @return The content of the downloaded file.
     */
    fun download(hash: String): String {
        return storage.download(hash)
    }

    /**
     * Deletes a file from IPFS.
     *
     * @param hash The hash of the file to delete.
     * @return True if the file was deleted, false otherwise.
     */
    fun delete(hash: String): Boolean {
        return storage.delete(hash)
    }

    /**
     * Lists all files in IPFS.
     *
     * @return A list of file hashes.
     */
    fun listFiles(): List<String> {
        return storage.listFiles()
    }
}
