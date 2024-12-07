import java.io.IOException;
import java.net.*;

public class UDPHandler {
    private DatagramSocket socket;

    public UDPHandler(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
    }

    public UDPHandler(DatagramSocket socket) {
        this.socket = socket;
    }

    public Message getMessage() throws IOException {
        byte[] buffer = new byte[129];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        int index = Byte.toUnsignedInt(buffer[0]);
        int size = Byte.toUnsignedInt(buffer[1]);
        String content = new String(buffer, 2, size);

        return new Message(index, size, content, packet.getPort());
    }

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

    public void sendBroadcastMessage(int port, Message message) {
        try {
            sendMessage(InetAddress.getByName("255.255.255.255"), port, message);
        } catch (UnknownHostException e) {
            System.err.println("Nie można wysłać wiadomości rozgłoszeniowej: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }
}
