package model;

public class Item {
    private final String name;
    private final int maxCount;
    private final int minCount;
    private int count;

    Item(String input) throws NumberFormatException {
        String[] inputs = input.split("\\|");
        name = inputs[0];
        if (inputs.length > 1) {
            minCount = Integer.parseInt(inputs[1]);
            if (inputs.length > 2) {
                maxCount = Integer.parseInt(inputs[2]);
            } else {
                maxCount = 0;
            }
        } else {
            minCount = 0;
            maxCount = 0;
        }
    }

    boolean hasCount(int r) {
        return r == count && r >= minCount && (r <= maxCount || maxCount == 0);
    }

    void increaseCount() {
        count++;
    }

    String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
