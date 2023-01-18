import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socketObj;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    
    public Client(Socket socketObj, String username) {
        try {
            this.socketObj = socketObj;
            this.bufferedReader = new BufferedReader(new OutputStreamWriter(socketObj.getOutputStream()));
            this.bufferedWriter = new BufferedWriter(new InputStreamReader(socketObj.getInputStream()));
            this.username = username;

        } catch (IOException e) {
            closeEverything(socketObj, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
            Scanner scannerObj = new Scanner(System.in);
            while(socketObj.isConnected()) {
                String messageToSend = scannerObj.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }
        } catch (Exception e) {
            closeEverything(socketObj, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while(socketObj.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);

                    } catch (IOException e) {
                        closeEverything(socketObj, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socketObj, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try{
            if(socketObj != null) {
                socketObj.close();
            }
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
        
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scannerObj = new Scanner(System.in);
        System.out.println("Enter your username for GroupChat");
        String username = scannerObj.nextLine();

        Socket socketObj = new Socket("localhost", 1234);
        Client clientObj = new Client(socketObj, username);

        clientObj.listenForMessage();
        clientObj.sendMessage();

    }

}
