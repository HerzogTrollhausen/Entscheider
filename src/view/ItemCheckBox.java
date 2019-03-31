package view;

import model.Item;

import javax.swing.*;

class ItemCheckBox extends JCheckBox {
    private Item item;

    ItemCheckBox(Item item) {
        super(item.toString());
        this.item = item;
    }

    Item getItem() {
        return item;
    }
}
