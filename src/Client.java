import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    List<String> messageStorage;
    String userName;
    Main main;
    int messageCount;
    PrintStream out;

    public Client(Socket socket, List<String> messageStorage, Main main) {
        this.socket = socket;
        this.messageStorage = messageStorage;
        this.messageCount = 0;
        this.main = main;

        Thread thread = new Thread(this);
        thread.start();
    }

    public void refresh() {
        if (messageCount != messageStorage.size()) {
            for (int i = messageCount; i < messageStorage.size(); i++) {
                out.println(messageStorage.get(i));
                System.out.println(messageStorage.get(i));
            }
            messageCount = messageStorage.size();
        }
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            Scanner in = new Scanner(is);
            out = new PrintStream(os);

            out.print("Enter your name:> ");
            String uInput = in.nextLine();
            userName = uInput;

            // читаем из сети и пишем в сеть
            messageStorage.add("Welcome to our chat @" + userName + "!");
            main.refreshAll();
            String input = "";
            String fInput = "";
            do {
                input = in.nextLine();
                fInput = "@" + userName + ":> " + input;
                messageStorage.add(fInput);
                messageCount++;
                main.refreshAll();
            } while (!input.equals("bye"));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}