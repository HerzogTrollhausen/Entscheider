package model;

public class Item {
    private final String name;
    private final int maxCount;
    private final int minCount;
    private int count;

    Item(String name) {
        this.name = name;
        maxCount = 0;
        minCount = 0;
    }

    Item(String name, int minCount, int maxCount) {
        this.name = name;
        this.minCount = minCount;
        this.maxCount = maxCount;
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
