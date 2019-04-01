package view;

import javafx.scene.input.KeyCode;
import model.Starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

class ManualPeopleChooser extends JPanel {
    private JPanel choosePanelPanel;
    private LinkedList<ChoosePanel> choosePanels = new LinkedList<>();

    ManualPeopleChooser() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        choosePanelPanel = new JPanel();
        choosePanelPanel.setLayout(new BoxLayout(choosePanelPanel, BoxLayout.PAGE_AXIS));
        addChoosePanel(new ChoosePanel(this));

        JButton ready = new JButton("Fertig");
        ready.addActionListener(e -> {
            LinkedList<String> strings = new LinkedList<>();
            for (ChoosePanel c : choosePanels) {
                String s = c.getText();
                if (!s.isEmpty()) {
                    strings.add(s);
                }
            }
            Starter.startWithNames(strings);
        });

        add(choosePanelPanel);
        add(ready);
    }

    private void addChoosePanel(ChoosePanel panel) {
        choosePanelPanel.add(panel);
        choosePanels.add(panel);
        MainFrame.update();
    }

    private void removeChoosePanel(ChoosePanel panel) {
        choosePanelPanel.remove(panel);
        choosePanels.remove(panel);
        MainFrame.update();
    }

    private class ChoosePanel extends JPanel {
        private JTextField field;

        ChoosePanel(ManualPeopleChooser parent) {

            field = new JTextField();
            field.setPreferredSize(new Dimension(200, 30));
            add(field);

            JButton button = new JButton("Hinzuf\u00fcgen");

            ActionListener remove = e -> parent.removeChoosePanel(this);

            ActionListener add = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setUneditable();
                    ChoosePanel panel = new ChoosePanel(parent);
                    parent.addChoosePanel(panel);
                    button.setText("Entfernen");
                    button.removeActionListener(this);
                    button.addActionListener(remove);
                    panel.requestFocus();
                }
            };
            field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        button.doClick();
                    }
                }
            });

            button.addActionListener(add);
            add(button);
        }

        private void setUneditable() {
            field.setEditable(false);
        }

        private String getText() {
            return field.getText().trim();
        }

        public void requestFocus() {
            field.requestFocus();
        }
    }
}
