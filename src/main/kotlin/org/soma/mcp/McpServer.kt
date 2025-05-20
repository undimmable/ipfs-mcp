package org.soma.mcp

import io.ktor.utils.io.streams.asInput
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.buffered
import org.soma.storages.IpfsStorageFactory
import org.soma.storages.SecureIpfsStorage

/**
 * Model Context Protocol (MCP) server implementation for IPFS storage.
 * This server provides access to IPFS storage through the MCP protocol.
 */
class McpServer {
    private val server = Server(
        serverInfo = Implementation(
            name = "ipfs-mcp-server", version = "1.0.0"
        ), options = ServerOptions(
            capabilities = ServerCapabilities(
                resources = ServerCapabilities.Resources(
                    subscribe = true, listChanged = true
                ), tools = ServerCapabilities.Tools(
                    listChanged = true
                )
            )
        )
    )

    private val transport = StdioServerTransport(
        System.`in`.asInput(),
        System.out.asSink().buffered()
    )

    private val ipfsStorageFactory = IpfsStorageFactory()

    /**
     * Starts the MCP server.
     */
    fun start() {
        runBlocking<Unit> {
            // Connect the server to the transport
            server.connect(transport)

            // Start the server
            println("MCP server for IPFS started.")
            // The server.connect() method starts the server, no need to call start()
        }
    }
}
