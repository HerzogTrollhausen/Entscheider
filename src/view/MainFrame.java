package view;

import javafx.util.Pair;
import model.Item;
import model.ModelMain;
import model.Starter;

import javax.swing.*;
import java.util.LinkedList;

public class MainFrame extends JFrame {
    private static MainFrame frame;

    static MainFrame getMainFrame() {
        if (frame == null) {
            frame = new MainFrame();
        }
        return frame;
    }

    public static void reset() {
        if (frame != null) {
            frame.setStartingContentPane();
        } else {
            frame = new MainFrame();
        }
        frame.showUnmatchedItems(ModelMain.getUnmatchedItems());
    }

    private void showUnmatchedItems(LinkedList<Pair<String, String>> items) {
        StringBuilder sb = new StringBuilder("Folgende Spiele werden in den "
                + "Listen der Spieler, aber nicht in der Liste der Spiele "
                + "geführt: ");
        for (Pair<String, String> p : items) {
            sb.append(System.lineSeparator());
            sb.append("Item ").append(p.getKey())
                    .append(" bei Spieler ").append(p.getValue());
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void setStartingContentPane() {
        setContentPane(new PeopleSelection());
        pack();
    }

    private MainFrame() {
        setTitle("Entscheider");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setStartingContentPane();
        setVisible(true);
    }

    void showItems(LinkedList<Item> items) {
        if (items == null || items.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Es gibt leider keine verf\u00fcgbaren Spiele.");
            Starter.reset();
        } else {
            setContentPane(new ItemSelection(items));
            revalidate();
            pack();
        }
    }

}
