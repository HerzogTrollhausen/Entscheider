package model;

import javafx.util.Pair;

import java.io.*;
import java.net.URL;
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
    private String peopleURLName;
    private URL itemURL;

    private ModelMain() {
        unmatchedItems = new LinkedList<>();
    }

    ModelMain(String path) throws IOException {
        this();
        peopleFolder = new File(path + "/" + PEOPLE_FOLDER_PATH);
        itemFile = new File(path + "/" + ITEM_FILE_PATH);
        addItems(false);
        addPeople();
    }

    ModelMain(String URL, LinkedList<String> names) throws IOException {
        this();
        itemURL = new URL(URL + "/" + ITEM_FILE_PATH);
        peopleURLName = URL + "/" + PEOPLE_FOLDER_PATH;
        addItems(true);
        addPeople(names);
    }

    public static LinkedList<Person> getPeople() {
        return people;
    }

    private void addItems(boolean online) throws IOException {
        items = new HashMap<>();
        ArrayList<String> strings;
        if (online) {
            strings = contentOfFile(itemURL);
        } else {
            strings = contentOfFile(itemFile);
        }
        for (String s : strings) {
            addItem(new Item(s));
        }
    }

    private void addItem(Item item) {
        items.put(item.getName(), item);
    }

    /**
     * Adds people from File
     *
     * @throws IOException When the filepath is not correct.
     */
    private void addPeople() throws IOException {
        people = new LinkedList<>();
        File[] files = peopleFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                String name = f.getName().split("\\.")[0];
                Person p = new Person(name);
                addItemsToPeople(p, contentOfFile(f));
                people.add(p);
            }
        }
    }

    private void addItemsToPeople(Person p, ArrayList<String> content) {
        for (String s : content) {
            Item i = items.get(s);
            if (i != null) {
                p.addItem(i);
            } else {
                unmatchedItems.add(new Pair<>(s, p.toString()));
            }
        }
    }

    private void addPeople(LinkedList<String> names) throws IOException {
        people = new LinkedList<>();
        for (String s : names) {
            Person p = new Person(s);
            addItemsToPeople(p, contentOfFile(new URL(peopleURLName
                    + "/" + s + ".txt")));
            people.add(p);
        }
    }

    static LinkedList<Pair<String, String>> getUnmatchedItems() {
        return unmatchedItems;
    }

    public static LinkedList<Item> getAvailableItems(LinkedList<Person> people) {
        int numberOfPeople = people.size();
        for (Person p : people) {
            p.markItems();
        }
        LinkedList<Item> availableItems = new LinkedList<>();
        for (Map.Entry<String, Item> pair : items.entrySet()) {
            Item i = pair.getValue();
            if (i.hasCount(numberOfPeople)) {
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

    private static ArrayList<String> contentOfFile(URL u) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
        String line;
        while ((line = in.readLine()) != null) {
            strings.add(line);
        }
        in.close();
        return strings;
    }
}
