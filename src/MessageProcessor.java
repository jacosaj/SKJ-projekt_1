public class MessageProcessor {
    private int savedValue;

    public MessageProcessor() {
        this.savedValue = -1;  // Początkowa wartość
    }

    public void processMessage(Message message) {
        if (message.index != 0 && message.index != -1) {
            System.out.println("Odebrano wartość: " + message.index);
            savedValue = message.index;  // Zapamiętujemy wartość
        } else {
            System.out.println("Odebrano wartość 0 lub -1: " + message.index);
        }
    }

    public int getSavedValue() {
        return savedValue;
    }
}