# P2P Academic Resource Sharing Network

## Project Overview

This project is a Java-based Peer-to-Peer (P2P) Academic Resource Sharing Network developed for the CPAN226 Network Programming course.

The system allows users to share and request academic files over a network. Each node (peer) can act as both a client and a server. The project demonstrates core networking concepts such as socket programming, file transfer, multi-threading, data integrity verification, and fault tolerance.

---

## Features

### Core Features

- Peer-to-Peer communication using Java sockets
- File sharing from a local directory
- File request system using filename
- File transfer over TCP protocol
- Multi-threaded server to handle multiple clients simultaneously
- File integrity verification using SHA-256 hashing
- Basic error handling for connection and file issues

---

### Advanced Features

- Packet loss simulation during file transfer
- Automatic retry mechanism for failed downloads
- Integrity verification after retry to ensure data correctness

---

## Technologies Used

- Java
- Java Sockets (TCP)
- Multi-threading
- SHA-256 Hashing
- Visual Studio Code
- Git & GitHub

---

## Project Structure

p2p-academic-resource-sharing/
│
├── Server.java
├── Client.java
├── ClientHandler.java
├── HashUtil.java
├── notes.txt
├── .gitignore
└── README.md


---

## How to Run the Project

### Step 1: Compile all Java files

### Step 2: Run the Server

### Step 3: Run the Client (in a new terminal)

---

## Expected Output

### Server Output
Multi-threaded server started...
Waiting for clients on port 6000
New client connected: /127.0.0.1
Client requested file: notes.txt
Simulating packet loss (connection closed)
New client connected: /127.0.0.1
Client requested file: notes.txt
File sent successfully


### Client Output


Connected to server (Attempt 1)
File corrupted, retrying...
Connected to server (Attempt 2)
File received successfully
Integrity check passed


---

## Key Concepts Demonstrated

- Socket Programming (TCP communication)
- Client-Server Architecture
- Multi-threading for handling multiple clients
- File streaming using input/output streams
- Data integrity verification using SHA-256 hashing
- Fault tolerance using retry mechanism
- Simulation of packet loss in network communication

---

## Author

Abdul Basit  
Computer Programming & Analysis  
Humber College  

---

## Course Information

CPAN226 – Network Programming