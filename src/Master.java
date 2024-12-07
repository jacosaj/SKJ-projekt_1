import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Master {
    private int port;
    private int initialNumber;
    private UDPHandler udpHandler;
    private List<Integer> receivedNumbers;

    public Master(int port, int initialNumber) throws SocketException {
        this.port = port;
        this.initialNumber = initialNumber;
        this.udpHandler = new UDPHandler(port);
        this.receivedNumbers = new ArrayList<>();
        this.receivedNumbers.add(initialNumber); // Dodajemy początkową wartość
    }

    public void run() throws IOException {
        System.out.println("Master działa na porcie: " + port);

        while (true) {
            Message message = udpHandler.getMessage();
            int receivedValue = Integer.parseInt(message.message);

            if (receivedValue == 0) {
                int average = calculateAverage();
                System.out.println("Średnia: " + average);
                broadcastMessage(Integer.toString(average));
            } else if (receivedValue == -1) {
                System.out.println("Otrzymano -1. Zamykanie aplikacji.");
                broadcastMessage("-1");
                udpHandler.close();
                break;
            } else {
                System.out.println("Otrzymano liczbę: " + receivedValue);
                receivedNumbers.add(receivedValue);
            }
        }
    }

    private int calculateAverage() {
        int sum = receivedNumbers.stream().mapToInt(Integer::intValue).sum();
        return (int) Math.floor((double) sum / receivedNumbers.size());
    }

    private void broadcastMessage(String message) throws IOException {
        Message broadcastMessage = new Message(0, message.length(), message);
        udpHandler.sendBroadcastMessage(port, broadcastMessage);
    }
}
