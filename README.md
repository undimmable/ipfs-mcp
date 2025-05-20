# IPFS & MCP Server Toolkit

A Kotlin-based toolkit providing easy integration with IPFS (InterPlanetary File System) and MCP (Model Context Protocol) server functionality. This project demonstrates secure file storage and retrieval using IPFS (including a secure wrapper), exposes server APIs for IPFS operations, and offers an MCP server implementation for broader protocol-based data management.

---

## Features

- Direct usage of a secure IPFS storage backend for upload, download, listing, and deletion of files.
- IPFS server: Exposes higher-level APIs for file manipulation via server-resources.
- MCP server: Demonstrates integration and usage of the Model Context Protocol.
- Easily extensible for advanced storage or protocol needs.

---

## Technologies Used

- **Kotlin JVM** 2.1+
- **IPFS Java HTTP Client**
- **MCP Kotlin SDK**
- **Ktor** for networking and serialization
- **SLF4J** for logging
- **JUnit** for testing

---

## Getting Started

### Prerequisites

- **Java 23 SDK** or higher
- Access to an IPFS node (token required as `IPFS_TOKEN` environment variable for SecureIpfsStorage)

### Installation

Clone this repository and build using Gradle:

```bash
 git clone <repo-url> cd <repo-directory> ./gradlew build
```

### Running the Application
```bash
IPFS_TOKEN=your_ipfs_token_here ./gradlew run
```


The main entry (`org.soma.MainKt`) demonstrates:

- Direct file operations against IPFS using a secure wrapper
- Launching and interacting with an IPFS server resource
- Starting and running an MCP server (good for protocol-based data workflows)

See printed console output for step-by-step demonstration and results.

---

## Project Structure

- `org.soma.MainKt` – Main demo entry point.
- `org.soma.server.IpfsServer` – Server API facade for IPFS operations.
- `org.soma.storages.SecureIpfsStorage` – Secure IPFS storage implementation.
- `org.soma.mcp.McpServer` – MCP protocol server.

---

## Development

- Build tasks and settings in `build.gradle.kts`.
- Unit tests supported via Kotlin test and JUnit.
- Digital artifacts handled via IPFS and MCP.

---

## Contributing

Contributions welcome! Please fork this repository and submit a pull request with your changes, or open issues for bug reports and feature proposals.

---

## License

See [LICENSE](license/LICENSE.txt) for details. (Apache 2.0 as per upstream dependencies.)

---

## Acknowledgements

- [Kotlin](https://kotlinlang.org/)
- [IPFS](https://ipfs.io/)
- [Ktor](https://ktor.io/)
- [Model Context Protocol (MCP)](https://github.com/modelcontext)

