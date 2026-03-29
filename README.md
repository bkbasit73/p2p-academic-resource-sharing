# P2P Academic Resource Sharing Network

## Course
CPAN-226 – Networking & Telecommunications

## Author
Abdul Basit

## Project Description
This project is a Java-based Peer-to-Peer (P2P) Academic Resource Sharing Network. Each peer can act as both a client and a server, allowing users to share and download academic files using Java socket programming.

## Features

### Core Features
- Peer-to-peer communication using TCP sockets
- File sharing and file request system
- File transfer over TCP
- Multi-threaded server for handling multiple clients
- File integrity verification using SHA-256 hashing

### Advanced Features
- Packet loss simulation at application level
- Automatic retry mechanism for failed transfers
- Logging system with timestamps

## Technologies Used
- Java
- TCP Sockets
- Multithreading
- SHA-256 Hashing
- File I/O

## Project Structure

p2p-academic-resource-sharing/
├── PeerServer.java
├── PeerClient.java
├── HashUtil.java
├── LoggerUtil.java
├── FileManager.java
├── TransferHandler.java
├── downloads/
├── shared_files/
├── README.md
└── .gitignore


## How to Run

### Compile

javac *.java


### Run Server

java PeerServer


### Run Client

java PeerClient


## Output
- Files are downloaded into the `downloads` folder
- Logs are saved in `log.txt`

## Conclusion
This project demonstrates key networking concepts including socket communication, concurrency, data integrity, and fault toleranc