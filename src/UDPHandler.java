import java.net.*;
import java.io.*;

public class UDPHandler {
    private DatagramSocket socket;
    private byte[] buffer = new byte[129];

    public UDPHandler() {
        this.socket = socket;
    }

    // Odbieranie wiadomości UDP
    public Message receiveMessage() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        int index = Byte.toUnsignedInt(buffer[0]);
        int size = Byte.toUnsignedInt(buffer[1]);
        String messageText = new String(buffer, 2, size);

        return new Message(index, size, messageText);
    }

    // Wysyłanie wiadomości UDP
    public void sendMessage(InetAddress address, int port, Message message) throws Exception {
        if (message.size > 127)
            throw new Exception("Incorrect size");
        if (message.index > 127)
            throw new Exception("Incorrect index");

        byte[] buffer = new byte[message.size + 2];
        buffer[0] = (byte) message.index;
        buffer[1] = (byte) message.size;
        byte[] chars = message.message.getBytes();
        for (int i = 2; i < buffer.length; i++) {
            buffer[i] = chars[i - 2];
        }

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    // Zamknięcie gniazda
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
