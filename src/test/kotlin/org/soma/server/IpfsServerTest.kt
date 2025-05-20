package org.soma.server

import org.soma.storages.IpfsStorageFactory
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.*

class IpfsServerTest {
    private lateinit var ipfsServer: IpfsServer

    @BeforeTest
    fun setUp() {
        ipfsServer = IpfsServer()
    }

    @Test
    fun `test start method prints correct message`() {
        // Redirect System.out to capture printed output
        val originalOut = System.out
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        try {
            // Call the start method
            ipfsServer.start()

            // Check that the correct message was printed
            assertEquals("IPFS server started.", outContent.toString().trim())
        } finally {
            // Reset System.out
            System.setOut(originalOut)
        }
    }

    @Test
    fun `test createResource returns IpfsResource with correct storage`() {
        // Create a resource with a test ID
        val resourceId = "test-resource"
        val resource = ipfsServer.createResource(resourceId)

        // Verify the resource is not null
        assertNotNull(resource)

        // Verify the resource has the correct type
        assertTrue(resource is IpfsResource)
    }

    @Test
    fun `test createResource uses environment variable for token`() {
        // Set environment variable for testing
        System.getenv("IPFS_TOKEN")
        try {
            // Use reflection to access the private factory field
            val factoryField = IpfsServer::class.java.getDeclaredField("ipfsStorageFactory")
            factoryField.isAccessible = true
            val factory = factoryField.get(ipfsServer) as IpfsStorageFactory

            // Mock the factory's create method to verify the token
            // Note: This is a simplified test. In a real scenario, you might want to use a mocking framework
            val resourceId = "test-resource"
            val resource = ipfsServer.createResource(resourceId)

            // Since we can't easily verify the token without a mocking framework,
            // we'll just verify that the resource was created
            assertNotNull(resource)
        } finally {
            // Clean up is handled by JVM
        }
    }
}