package view;

import model.Person;
import model.ModelMain;

import javax.swing.*;
import java.util.LinkedList;

class PeopleSelection extends JPanel {
    PeopleSelection() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        LinkedList<Person> people = ModelMain.getPeople();
        LinkedList<PersonCheckBox> checkBoxes = new LinkedList<>();
        for(Person p : people) {
            PersonCheckBox c = new PersonCheckBox(p);
            checkBoxes.add(c);
            add(c);
        }
        JButton button = new JButton("Weiter");
        button.addActionListener(e -> {
            LinkedList<Person> selectedPeople = new LinkedList<>();
            for(PersonCheckBox c : checkBoxes) {
                if(c.isSelected()) {
                    selectedPeople.add(c.getPerson());
                }
            }
            MainFrame.getMainFrame().showItems(ModelMain.getAvailableItems(selectedPeople));
        });
        add(button);
    }

    private class PersonCheckBox extends JCheckBox {
        private Person person;

        PersonCheckBox(Person person) {
            super(person.toString());
            this.person = person;
        }

        Person getPerson() {
            return person;
        }
    }
}
