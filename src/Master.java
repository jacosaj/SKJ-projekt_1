import java.io.IOException;
import java.net.SocketException;

/**
 * Klasa reprezentująca tryb Master.
 * Odpowiada za odbieranie i przetwarzanie wiadomości, obliczanie średnich oraz przesyłanie wyników.
 */
public class Master {
    private int port;
    private UDPHandler udpHandler;
    private MessageProcessor messageProcessor;

    /**
     * Tworzy obiekt klasy Master.
     *
     * @param port          numer portu, na którym aplikacja nasłuchuje.
     * @param initialNumber początkowa wartość do przetwarzania.
     * @throws SocketException jeśli nie można utworzyć gniazda UDP.
     */
    public Master(int port, int initialNumber) throws SocketException {
        this.port = port;
        this.udpHandler = new UDPHandler(port);
        this.messageProcessor = new MessageProcessor(initialNumber); // Używamy MessageProcessor
    }

    /**
     * Uruchamia logikę trybu Master.
     * Master odbiera wiadomości, przetwarza je, oblicza średnią i rozgłasza wyniki.
     *
     * @throws IOException jeśli wystąpi błąd podczas odbierania lub wysyłania wiadomości.
     */
    public void run() throws IOException {
        System.out.println("Master działa na porcie: " + port);

        while (true) {
            Message message = udpHandler.getMessage();
            int receivedValue = Integer.parseInt(message.message);

            if (receivedValue == 0) {
                // Oblicz średnią za pomocą MessageProcessor
                int average = messageProcessor.calculateAverage();
                System.out.println("Średnia: " + average);
                broadcastMessage(Integer.toString(average));
            } else if (receivedValue == -1) {
                System.out.println("Otrzymano -1. Zamykanie aplikacji.");
                broadcastMessage("-1");
                udpHandler.close();
                break;
            } else {
                // Dodaj liczbę do MessageProcessor
                messageProcessor.addValue(receivedValue);
            }
        }
    }

    /**
     * Rozsyła wiadomość do wszystkich klientów.
     *
     * @param message treść wiadomości do rozesłania.
     * @throws IOException jeśli wystąpi błąd podczas wysyłania wiadomości.
     */
    private void broadcastMessage(String message) throws IOException {
        Message broadcastMessage = new Message(0, message.length(), message);
        udpHandler.sendBroadcastMessage(port, broadcastMessage);
    }
}
