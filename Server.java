import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.net.Socket;

public class Server {

    private ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
    private ServerSocket ssObj;
    public Server(ServerSocket ssObj) {
        this.ssObj = ssObj;
    }

    public void startServer() {
        try {
            
            while(!ssObj.isClosed()) {
                Socket socketObj = ssObj.accept();

                System.out.println("A user has been connected!");
                ClientHandler clientHandler = new ClientHandler(socketObj, clientHandlers);

                Thread thread = new Thread(clientHandler);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void closeServerSocket() {
            
            try {
                if(ssObj != null) {
                    ssObj.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    public static void main(String[] args) throws IOException {
        ServerSocket ssObj = new ServerSocket(1234);
        Server serverObj = new Server(ssObj);
        
    }
    
    
    }
    