import java.util.ArrayList;
import java.util.List;

/**
 * Klasa do przetwarzania otrzymanych liczb.
 * Odpowiada za dodawanie liczb do listy oraz obliczanie średniej z tych liczb.
 */
public class MessageProcessor {
    private final List<Integer> receivedNumbers;

    /**
     * Tworzy obiekt klasy MessageProcessor z początkową liczbą.
     *
     * @param initialNumber początkowa liczba do dodania do listy.
     */
    public MessageProcessor(int initialNumber) {
        this.receivedNumbers = new ArrayList<>();
        this.receivedNumbers.add(initialNumber);
    }

    /**
     * Dodaje nową wartość do listy przechowywanych liczb.
     * Oblicza sumę wszystkich liczb po dodaniu nowej.
     *
     * @param value wartość do dodania do listy.
     */
    public void addValue(int value) {
        receivedNumbers.add(value);
        int currentSum = receivedNumbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Przechowana wartość: " + value + ", Suma liczb: " + currentSum);
    }

    /**
     * Oblicza średnią z przechowywanych liczb.
     *
     * @return średnia z przechowywanych liczb.
     */
    public int calculateAverage() {
        int sum = receivedNumbers.stream().mapToInt(Integer::intValue).sum();
        return (int) Math.floor((double) sum / receivedNumbers.size());
    }
}
