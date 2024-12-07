package slave;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPHandler {
    private DatagramSocket socket;

    public UDPHandler() throws SocketException {
        // Tworzymy gniazdo UDP na losowym porcie
        this.socket = new DatagramSocket();
    }

    // Metoda do odbierania wiadomości
    public Message getMessage() throws IOException {
        Message message;
        byte[] buffer = new byte[129];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        int index = Byte.toUnsignedInt(buffer[0]);
        int size = Byte.toUnsignedInt(buffer[1]);
        message = new Message(index, size, new String(buffer, 2, size));
        message.port = packet.getPort();
        return message;
    }

    // Metoda do wysyłania wiadomości na zadany port (np. port master)
    public void sendMessage(InetAddress address, int port, Message message) throws Exception {
        if (message.size > 127)
            throw new Exception("Incorrect size");
        if (message.index > 127)
            throw new Exception("Incorrect index");

        // Upewniamy się, że message.size jest zgodne z długością wiadomości
        byte[] chars = message.message.getBytes();
        if (message.size < chars.length) {
            throw new Exception("Message size is too small for the given message content");
        }

        byte[] buffer = new byte[message.size + 2];
        buffer[0] = (byte) message.index;
        buffer[1] = (byte) message.size;

        // Wypełniamy buffer od indeksu 2
        for (int i = 0; i < chars.length; i++) {
            buffer[i + 2] = chars[i];
        }

        // Jeśli wiadomość jest mniejsza niż podany rozmiar, wypełniamy resztę zerami
        for (int i = chars.length + 2; i < buffer.length; i++) {
            buffer[i] = 0;
        }

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }


    // Zamykanie gniazda
    public void close() {
        socket.close();
    }
    }