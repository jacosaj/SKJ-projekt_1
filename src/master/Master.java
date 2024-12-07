package master;

import java.net.DatagramSocket;

public class Master {
    private int port;
    private int number;

    public Master(int port, int number) throws Exception {
        this.port = port;
        this.number = number;

        // Próba otwarcia portu
        DatagramSocket socket = new DatagramSocket(port);
        socket.close(); // Zamyka gniazdo po sprawdzeniu
    }

    public void start() {
        System.out.println("Tryb Master uruchomiony. Port: " + port + ", Liczba: " + number);
        // Implementacja logiki Master
    }
}
