import java.io.IOException;
import java.net.*;

/**
 * Klasa do obsługi komunikacji UDP.
 * Odpowiada za wysyłanie i odbieranie wiadomości za pomocą protokołu UDP.
 */
public class UDPHandler {
    private final DatagramSocket socket;

    /**
     * Tworzy obiekt klasy UDPHandler z gniazdem UDP na określonym porcie.
     *
     * @param port numer portu, na którym gniazdo ma nasłuchiwać.
     * @throws SocketException jeśli nie uda się utworzyć gniazda UDP.
     */
    public UDPHandler(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
    }

    /**
     * Tworzy obiekt klasy UDPHandler z już istniejącym gniazdem UDP.
     *
     * @param socket już utworzone gniazdo UDP.
     */
    public UDPHandler(DatagramSocket socket) {
        this.socket = socket;
    }

    /**
     * Odbiera wiadomość UDP z gniazda.
     *
     * @return obiekt klasy Message, który zawiera odebraną wiadomość.
     * @throws IOException jeśli wystąpi błąd podczas odbierania wiadomości.
     */
    public Message getMessage() throws IOException {
        byte[] buffer = new byte[129];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        int index = Byte.toUnsignedInt(buffer[0]);
        int size = Byte.toUnsignedInt(buffer[1]);
        String content = new String(buffer, 2, size);

        return new Message(index, size, content, packet.getPort());
    }

    /**
     * Wysyła wiadomość do określonego adresu i portu.
     *
     * @param address adres IP odbiorcy.
     * @param port    port odbiorcy.
     * @param message wiadomość do wysłania.
     */
    public void sendMessage(InetAddress address, int port, Message message) {
        try {
            byte[] buffer = new byte[message.size + 2];
            buffer[0] = (byte) message.index;
            buffer[1] = (byte) message.size;
            System.arraycopy(message.message.getBytes(), 0, buffer, 2, message.size);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Błąd podczas wysyłania wiadomości: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Wysyła wiadomość rozgłoszeniową na określony port.
     *
     * @param port    port, na który wysyłana jest wiadomość.
     * @param message wiadomość do wysłania.
     */
    public void sendBroadcastMessage(int port, Message message) {
        try {
            sendMessage(InetAddress.getByName("255.255.255.255"), port, message);
        } catch (UnknownHostException e) {
            System.err.println("Nie można wysłać wiadomości rozgłoszeniowej: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Zamyka gniazdo UDP.
     */
    public void close() {
        socket.close();
    }
}
