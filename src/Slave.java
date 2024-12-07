import java.net.InetAddress;
import java.net.DatagramSocket;

public class Slave {
    private int port;
    private int number;
    private UDPHandler udpHandler;

    public Slave(int port, int number) throws Exception {
        this.port = port;
        this.number = number;

        // Tworzymy gniazdo UDP z losowym portem
        DatagramSocket socket = new DatagramSocket();
        this.udpHandler = new UDPHandler(socket);
    }

    public void run() throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        Message message = new Message(0, Integer.toString(number).length(), Integer.toString(number));

        // Wysłanie wiadomości
        udpHandler.sendMessage(localHost, port, message);
        System.out.println("Slave wysłał wiadomość: " + number);
    }
}
