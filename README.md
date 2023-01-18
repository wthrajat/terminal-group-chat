# Terminal Group Chat

Allows multiple clients to connect to the server and exchange messages with each other in real-time (in terminal duh). The clients can also join or leave a group at any time. (Made for an experiment)

# Run:

1. Clone the repo and **cd** into it:
    ```
    git clone https://github.com/wthrajat/terminal-groupChat.git
    cd terminal-group-chat
    ```

2. Compile the code: 
    ```
    javac Server.java
    javac Client.java
    javac ClientHandler.java
    ```

3. Start the Server:
    ```
    java Server
    ```

4. Open another instance of the terminal and start the ClientHandler:
    ```
    java ClientHandler
    ```

5. Open another instance of the terminal and start the Client:
    ```
    java Client
    ```
    
6. Start messaging across the terminal


### Note

This is a local network (localhost) based group chat application, it will not work over internet.

I mean it can easily but not atm. Feel free to make a PR


