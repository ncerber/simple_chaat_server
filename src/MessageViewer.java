import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MessageViewer extends Thread{
    List<String> messageStorage;
    int lastMessage;
    PrintStream printStream;

    public MessageViewer(List<String> messageStorage, PrintStream printStream) {
        this.messageStorage = messageStorage;
        this.printStream = printStream;
    }

    @Override
    public void run() {
        while (true){
            if (lastMessage<messageStorage.size()-1){
                for (int i = lastMessage; i < messageStorage.size(); i++) {
                    printStream.println(messageStorage.get(i));
                }
                lastMessage = messageStorage.size()-1;
            }
        }
    }
}
