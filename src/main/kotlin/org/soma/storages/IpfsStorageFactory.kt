package org.soma.storages

class IpfsStorageFactory {
    private val ipfsStorageMap: MutableMap<String?, SecureIpfsStorage> = HashMap<String?, SecureIpfsStorage>()

    fun create(id: String, token: String): SecureIpfsStorage {
        return ipfsStorageMap.computeIfAbsent(id) { key: String? -> SecureIpfsStorage(key!!, token) }
    }
}
