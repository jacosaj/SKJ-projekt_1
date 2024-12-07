package slave;

import java.net.*;

public class Slave {
    private int port;
    private int number;

    public Slave(int port, int number) {
        this.port = port;
        this.number = number;
    }

    public void run() throws Exception {
        // Tworzymy gniazdo UDP w trybie slave
        UDPHandler handler = new UDPHandler();

        // Tworzymy wiadomość z numerem
        Message message = new Message(1, 4, String.valueOf(number));

        // Wysyłamy wiadomość do procesu master na określony port
        InetAddress address = InetAddress.getLocalHost();
        handler.sendMessage(address, port, message);

        // Zamykamy gniazdo po wysłaniu wiadomości
        handler.close();
    }
}


