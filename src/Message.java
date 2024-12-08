/**
 * Klasa reprezentująca wiadomość wysyłaną między procesami.
 * Zawiera informacje o indeksie, rozmiarze oraz treści wiadomości.
 */
public class Message {
    public int index;
    public int size;
    public String message;
    public int port;

    /**
     * Tworzy obiekt wiadomości.
     *
     * @param index   indeks wiadomości.
     * @param size    rozmiar wiadomości.
     * @param message treść wiadomości.
     */
    public Message(int index, int size, String message) {
        this.index = index;
        this.size = size;
        this.message = message;
    }

    /**
     * Tworzy obiekt wiadomości z dodatkowym portem.
     *
     * @param index   indeks wiadomości.
     * @param size    rozmiar wiadomości.
     * @param message treść wiadomości.
     * @param port    port, z którego wysłano wiadomość.
     */
    public Message(int index, int size, String message, int port) {
        this.index = index;
        this.size = size;
        this.message = message;
        this.port = port;
    }
}
