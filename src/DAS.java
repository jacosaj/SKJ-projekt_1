//import master.Master;
import slave.Slave;

public class DAS {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java DAS <port> <number>");
            System.exit(1);
        }

        try {
            // Pobieranie argumentów z wiersza poleceń
            int port = Integer.parseInt(args[0]);
            int number = Integer.parseInt(args[1]);

            // Tworzenie instancji slave
            Slave slave = new Slave(port, number);
            slave.run();

        } catch (NumberFormatException e) {
            System.out.println("Invalid port or number format.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}