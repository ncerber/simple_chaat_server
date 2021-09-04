import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private ServerSocket server;
    List<String> messageStorage = new ArrayList<>();
    List<Client> clients = new ArrayList<>();

    public Main() throws IOException {
        server = new ServerSocket(1234);
    }

    public void runServer() throws IOException{
        while(true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = server.accept();
            System.out.println("Client connected!");
            // создаем клиента на своей стороне
            clients.add(new Client(socket,messageStorage,this));
        }
    }

    public void refreshAll(){
        for (Client client : clients) {
            client.refresh();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().runServer();
    }
}
