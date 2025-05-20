package org.soma.server

import org.soma.storages.SecureIpfsStorage
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IpfsResourceTest {
    private lateinit var mockStorage: TestSecureIpfsStorage
    private lateinit var ipfsResource: IpfsResource

    @BeforeTest
    fun setUp() {
        // Create a test implementation of SecureIpfsStorage
        mockStorage = TestSecureIpfsStorage()
        ipfsResource = IpfsResource(mockStorage)
    }

    @Test
    fun `test upload delegates to storage`() {
        val fileName = "test.txt"
        val content = "Hello, IPFS!"
        val expectedHash = "test-hash-123"

        // Configure mock to return expected hash
        mockStorage.uploadResult = expectedHash

        // Call the method under test
        val result = ipfsResource.upload(fileName, content)

        // Verify the result
        assertEquals(expectedHash, result)

        // Verify the mock was called with correct parameters
        assertEquals(fileName, mockStorage.lastUploadedFileName)
        assertEquals(content, mockStorage.lastUploadedContent)
    }

    @Test
    fun `test download delegates to storage`() {
        val hash = "test-hash-123"
        val expectedContent = "Downloaded content"

        // Configure mock to return expected content
        mockStorage.downloadResult = expectedContent

        // Call the method under test
        val result = ipfsResource.download(hash)

        // Verify the result
        assertEquals(expectedContent, result)

        // Verify the mock was called with correct parameters
        assertEquals(hash, mockStorage.lastDownloadedHash)
    }

    @Test
    fun `test delete delegates to storage`() {
        val hash = "test-hash-123"
        val expectedResult = true

        // Configure mock to return expected result
        mockStorage.deleteResult = expectedResult

        // Call the method under test
        val result = ipfsResource.delete(hash)

        // Verify the result
        assertEquals(expectedResult, result)

        // Verify the mock was called with correct parameters
        assertEquals(hash, mockStorage.lastDeletedHash)
    }

    @Test
    fun `test listFiles delegates to storage`() {
        val expectedFiles = listOf("hash1", "hash2", "hash3")

        // Configure mock to return expected files
        mockStorage.listFilesResult = expectedFiles

        // Call the method under test
        val result = ipfsResource.listFiles()

        // Verify the result
        assertEquals(expectedFiles, result)

        // Verify the mock was called
        assertTrue(mockStorage.listFilesCalled)
    }

    /**
     * Test implementation of SecureIpfsStorage for testing IpfsResource
     */
    private class TestSecureIpfsStorage : SecureIpfsStorage("test-id", "") {
        // For upload method
        var uploadResult: String = ""
        var lastUploadedFileName: String? = null
        var lastUploadedContent: String? = null

        // For download method
        var downloadResult: String = ""
        var lastDownloadedHash: String? = null

        // For delete method
        var deleteResult: Boolean = false
        var lastDeletedHash: String? = null

        // For listFiles method
        var listFilesResult: List<String> = emptyList()
        var listFilesCalled: Boolean = false

        override fun upload(fileName: String, content: String): String {
            lastUploadedFileName = fileName
            lastUploadedContent = content
            return uploadResult
        }

        override fun download(hash: String): String {
            lastDownloadedHash = hash
            return downloadResult
        }

        override fun delete(hash: String): Boolean {
            lastDeletedHash = hash
            return deleteResult
        }

        override fun listFiles(): List<String> {
            listFilesCalled = true
            return listFilesResult
        }
    }
}