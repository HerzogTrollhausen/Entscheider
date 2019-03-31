package model;

import java.util.LinkedList;

public class Person {
    private String name;
    private LinkedList<Item> items = new LinkedList<>();

    Person(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    void addItem(Item item) {
        items.add(item);
    }

    void markItems() {
        for(Item i : items) {
            i.increaseCount();
        }
    }
}
