package org.soma

import org.soma.mcp.McpServer
import org.soma.server.IpfsServer
import org.soma.storages.SecureIpfsStorage

/**
 * Main entry point for the application.
 * Demonstrates direct IPFS storage usage, IPFS server, and MCP server functionality.
 */
fun main() {
    // Demonstrate direct IPFS storage usage
    println("=== Direct IPFS Storage Usage ===")
    val ipfs = SecureIpfsStorage(System.getenv("IPFS_TOKEN") ?: "")
    println("Uploading file to IPFS...")
    val hash = ipfs.upload("test.txt", "test")
    println("File uploaded with hash: $hash")

    println("Downloading file from IPFS...")
    val content = ipfs.download(hash)
    println("File content: $content")

    println("Listing files in IPFS...")
    val files = ipfs.listFiles()
    println("Files: $files")

    println("Deleting file from IPFS...")
    val deleted = ipfs.delete(hash)
    println("File deleted: $deleted")

    // Demonstrate IPFS server usage
    println("\n=== IPFS Server Usage ===")
    val ipfsServer = IpfsServer()
    ipfsServer.start()

    val resource = ipfsServer.createResource("test-resource")
    println("Created IPFS resource")

    println("Uploading file via IPFS server...")
    val serverHash = resource.upload("server-test.txt", "server test content")
    println("File uploaded with hash: $serverHash")

    println("Downloading file via IPFS server...")
    val serverContent = resource.download(serverHash)
    println("File content: $serverContent")

    println("Listing files via IPFS server...")
    val serverFiles = resource.listFiles()
    println("Files: $serverFiles")

    println("Deleting file via IPFS server...")
    val serverDeleted = resource.delete(serverHash)
    println("File deleted: $serverDeleted")

    // Start the MCP server
    println("\n=== MCP Server ===")
    println("Starting MCP server...")
    val mcpServer = McpServer()
    mcpServer.start()
}
