import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    List<String> messageStorage;
    String userName;

    public Client(Socket socket, List<String> messageStorage) {
        this.socket = socket;
        this.messageStorage = messageStorage;
    }

    public void run() {
        int messageCount = messageStorage.size();
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            Scanner in = new Scanner(is);
            PrintStream out = new PrintStream(os);

            out.print("Enter your name:> ");
            String uInput = in.nextLine();
//            while (uInput.length()==0) {
                userName =uInput;
//                uInput = in.nextLine();
//            }

            // читаем из сети и пишем в сеть
            out.println("Welcome to our chat @"+userName+"!");
            messageStorage.add("Welcome to our chat @"+userName+"!");
            messageCount++;
            String input = in.nextLine();
            String fInput = "";
            MessageViewer messageViewer = new MessageViewer(messageStorage,out);
            messageViewer.start();
            while (!input.equals("bye")) {
                fInput = "@"+userName+":> "+input;
//                out.println(fInput);
                messageStorage.add(fInput);
//                messageCount++;
//                if(messageCount!=messageStorage.size()){
//                    for (int i = messageCount-1; i < messageStorage.size(); i++) {
//                        out.println(messageStorage.get(i));
//                    }
//                }
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}