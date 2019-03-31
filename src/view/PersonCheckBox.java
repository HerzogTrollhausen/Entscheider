package view;

import model.Person;

import javax.swing.*;

class PersonCheckBox extends JCheckBox {
    private Person person;
    
    PersonCheckBox(Person person) {
        super(person.toString());
        this.person = person;
    }

    Person getPerson() {
        return person;
    }
}
