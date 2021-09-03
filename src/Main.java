import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        List<String> messageStorage = new ArrayList<>();
        while(true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = server.accept();
            System.out.println("Client connected!");
            // создаем клиента на своей стороне
            Client client = new Client(socket,messageStorage);
            // запускаем поток
            Thread thread = new Thread(client);
            thread.start();
        }
    }
}
