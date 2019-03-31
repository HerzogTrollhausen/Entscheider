package model;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class ModelMain {
    private static HashMap<String, Item> items;
    private static LinkedList<Person> people;
    private static final String PEOPLE_FOLDER_PATH = "people";
    private static final String ITEM_FILE_PATH = "items.txt";
    private static LinkedList<Pair<String, String>> unmatchedItems;
    private File peopleFolder;
    private File itemFile;

    ModelMain(String path) throws IOException {
        peopleFolder = new File(path + "/" + PEOPLE_FOLDER_PATH);
        itemFile = new File(path + "/" + ITEM_FILE_PATH);
        unmatchedItems = new LinkedList<>();
        addItems();
        addPeople();
    }

    public static LinkedList<Person> getPeople() {
        return people;
    }

    private void addItems() throws IOException {
        items = new HashMap<>();
        for (String s : contentOfFile(itemFile)) {
            addItem(new Item(s));
        }
    }

    private void addItem(Item item) {
        items.put(item.getName(), item);
    }

    private void addPeople() throws IOException {
        people = new LinkedList<>();
        File[] files = peopleFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                String name = f.getName().split("\\.")[0];
                Person p = new Person(name);
                for (String s : contentOfFile(f)) {
                    Item i = items.get(s);
                    if (i != null) {
                        p.addItem(i);
                    } else {
                        unmatchedItems.add(new Pair<>(s, name));
                    }
                }
                people.add(p);
            }
        }
    }

    public static LinkedList<Pair<String, String>> getUnmatchedItems() {
        return unmatchedItems;
    }

    public static LinkedList<Item> getAvailableItems(LinkedList<Person> people) {
        for (Person p : people) {
            p.markItems();
        }
        LinkedList<Item> availableItems = new LinkedList<>();
        for (Map.Entry<String, Item> pair : items.entrySet()) {
            Item i = pair.getValue();
            if (i.hasCount(people.size())) {
                availableItems.add(i);
            }
        }
        return availableItems;
    }

    private static ArrayList<String> contentOfFile(File f) throws IOException {

        FileReader fileReader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Stream<String> result = bufferedReader.lines();
        if (result != null) {
            Object[] obs = result.toArray();
            bufferedReader.close();
            fileReader.close();
            if (obs.length > 0) {
                ArrayList<String> strings = new ArrayList<>(obs.length);
                for (Object ob : obs) {
                    strings.add((String) ob);
                }
                return strings;
            } else {
                throw new IOException();
            }
        } else {
            throw new IllegalStateException("result is null");
        }
    }
}
