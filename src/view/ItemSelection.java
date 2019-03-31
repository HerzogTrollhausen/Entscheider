package view;

import model.Item;
import model.Starter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;

class ItemSelection extends JPanel {

    private LinkedList<ItemCheckBox> checkBoxes = new LinkedList<>();

    ItemSelection(LinkedList<Item> items) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        for (Item i : items) {
            ItemCheckBox c = new ItemCheckBox(i);
            c.setSelected(true);
            checkBoxes.add(c);
            add(c);
        }

        JButton random = new JButton("Zuf\u00e4lliges Spiel ausw\u00e4hlen");
        random.addActionListener(e -> {
            ArrayList<Item> selectedItems = new ArrayList<>();
            for (ItemCheckBox c : checkBoxes) {
                if (c.isSelected()) {
                    selectedItems.add(c.getItem());
                }
            }
            if (selectedItems.size() > 0) {
                int rand = (int) (Math.random() * selectedItems.size());
                Item item = selectedItems.get(rand);
                JOptionPane.showMessageDialog(this,
                        "Das ausgew\u00e4hlte Spiel ist: " + item);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Kein Spiel ist ausgew\u00e4hlt.");
            }
        });
        add(random);

        JButton repeat = new JButton("Wiederholen");
        repeat.addActionListener(e -> Starter.reset());
        add(repeat);
    }
}
