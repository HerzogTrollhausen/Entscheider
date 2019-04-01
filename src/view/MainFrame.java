package view;

import javafx.util.Pair;
import model.Item;
import model.Starter;

import javax.swing.*;
import java.util.LinkedList;

public class MainFrame extends JFrame {
    private static MainFrame frame;

    static MainFrame getMainFrame() {
        return frame;
    }

    public static void showSelector() {
        showPanel(new PeopleSelection());
    }

    public static void showChooser() {
        showPanel(new ManualPeopleChooser());
    }

    private static void showPanel(JPanel panel) {
        if (frame != null) {
            frame.setContentPane(panel);
            frame.pack();
        } else {
            frame = new MainFrame(panel);
        }
    }

    public static void showUnmatchedItems(LinkedList<Pair<String, String>> items) {
        StringBuilder sb = new StringBuilder("Folgende Spiele werden in den "
                + "Listen der Spieler, aber nicht in der Liste der Spiele "
                + "gef\u00fchrt: ");
        for (Pair<String, String> p : items) {
            sb.append(System.lineSeparator());
            sb.append("Item ").append(p.getKey())
                    .append(" bei Spieler ").append(p.getValue());
        }
        JOptionPane.showMessageDialog(frame, sb.toString());
    }

    private MainFrame(JPanel contentPane) {
        setTitle("Entscheider");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();
        setVisible(true);
    }

    static void update() {
        if(frame != null) {
            frame.repaint();
            frame.revalidate();
            frame.pack();
        }
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
