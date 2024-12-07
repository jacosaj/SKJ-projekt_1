public class Message {
    public int index;
    public int size;
    public String message;
    public int port;

    public Message(int index, int size, String message) {
        this.index = index;
        this.size = size;
        this.message = message;
    }

    public Message(int index, int size, String message, int port) {
        this.index = index;
        this.size = size;
        this.message = message;
        this.port = port;
    }
}
