import java.util.ArrayList;
import java.util.List;

public class MessageProcessor {
    private final List<Integer> receivedNumbers;

    public MessageProcessor(int initialNumber) {
        this.receivedNumbers = new ArrayList<>();
        this.receivedNumbers.add(initialNumber);
    }

    public void addNonZeroValue(int value) {
        receivedNumbers.add(value);
        System.out.println("Stored value: " + value);
    }

    public int calculateAverage() {
        int sum = receivedNumbers.stream().mapToInt(Integer::intValue).sum();
        return (int) Math.floor((double) sum / receivedNumbers.size());
    }
}
