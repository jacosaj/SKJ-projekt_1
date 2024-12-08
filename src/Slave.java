import java.net.InetAddress;
import java.net.DatagramSocket;

/**
 * Klasa reprezentująca tryb Slave.
 * Odpowiada za wysyłanie wiadomości do procesu Master na określonym porcie.
 */
public class Slave {
    private int port;
    private int number;
    private UDPHandler udpHandler;

    /**
     * Tworzy obiekt klasy Slave.
     * Inicjalizuje gniazdo UDP i ustawia numer portu oraz liczbę, którą będzie wysyłać.
     *
     * @param port   numer portu, na który aplikacja ma wysyłać wiadomości.
     * @param number liczba, którą proces Slave ma wysłać do procesu Master.
     * @throws Exception jeśli wystąpi błąd podczas tworzenia gniazda UDP.
     */
    public Slave(int port, int number) throws Exception {
        this.port = port;
        this.number = number;

        // Tworzenie gniazdo UDP z losowym portem
        DatagramSocket socket = new DatagramSocket();
        this.udpHandler = new UDPHandler(socket);
    }

    /**
     * Uruchamia logikę trybu Slave.
     * Proces Slave wysyła wiadomość z wartością liczby do procesu Master.
     *
     * @throws Exception jeśli wystąpi błąd podczas wysyłania wiadomości.
     */
    public void run() throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        Message message = new Message(0, Integer.toString(number).length(), Integer.toString(number));

        // Wysłanie wiadomości
        udpHandler.sendMessage(localHost, port, message);
        System.out.println("Slave wysłał wiadomość: " + number);
    }
}
