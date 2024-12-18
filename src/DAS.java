import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Główna klasa aplikacji, która uruchamia tryb Master lub Slave w zależności od dostępności portu.
 * Obsługuje parametry wejściowe i decyduje, który tryb uruchomić.
 */
public class DAS {
    /**
     * Główna metoda programu.
     *
     * @param args parametry wejściowe: port i liczba
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Użycie: java DAS <port> <number>");
            return;
        }

        int port;
        int number;

        try {
            port = Integer.parseInt(args[0]);
            number = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Błąd: Podaj poprawne liczby jako parametry <port> i <number>.");
            return;
        }

        try {
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("Tryb: MASTER");
            socket.close();
            Master master = new Master(port, number);
            master.run();
        } catch (SocketException e) {
            System.out.println("Tryb: SLAVE");
            try {
                Slave slave = new Slave(port, number);
                slave.run();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Błąd podczas uruchamiania trybu slave.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
