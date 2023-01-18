import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.sound.midi.VoiceStatus;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandler = new ArrayList<>();
    private ArrayList<ClientHandler> clientHandlers;
    private Socket socketObj;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

public ClientHandler(Socket socketObj, ArrayList<ClientHandler> clientHandlers) {
    
    try {
        this.socketObj = socketObj;
        this.clientHandlers = clientHandlers;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socketObj.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socketObj.getOutputStream()));

        this.clientUsername = bufferedReader.readLine();
        clientHandler.add(this);
        broadcastMessage("Server: " + clientUsername + " has entered the chat! Say Hi!");
    } 
    catch (IOException e) {

        closeEverything(socketObj, bufferedReader, bufferedWriter);

    
    }
}

    @Override
    public void run() {

        String messageFromClient;
        while(socketObj.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);

            } catch (Exception e) {
                closeEverything(socketObj, bufferedReader, bufferedWriter);
                break;
            }
        }

    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if(!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                                }
            }
            catch (IOException e) {
                closeEverything(socketObj, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandler.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat! :(");

    }

    public void closeEverything(Socket socketObj, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socketObj != null) {
                socketObj.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
