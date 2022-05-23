package de.wyldstudios.studio;

import de.wyldstudios.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Entry {
    public static void newEntry(JFrame mainframe) {
        // new Entry Frame
        JFrame frame = new JFrame("Neuer Eintrag");

        String[] genders = {"männlich", "weiblich"};

        // Components
        JLabel override_warning = new JLabel("Achten Sie beim eingeben der ID darauf, dass diese valide ist!");
        JLabel override_warning_1 = new JLabel("Ansonsten können Daten überschrieben werden!");
        JTextField name = new JTextField();
        JLabel name_info = new JLabel("Name:");
        JTextField id = new JTextField();
        JLabel id_info = new JLabel("ID:");
        JTextField birthday = new JTextField();
        JLabel birthday_info = new JLabel("Geburtsdatum:");
        JLabel gender_info = new JLabel("Geschlecht: ");
        JComboBox<String> gender = new JComboBox<>(genders);
        JTextArea information = new JTextArea();
        JScrollPane information_scroll = new JScrollPane(information);
        JLabel information_info = new JLabel("Informationen:");
        JButton get_informations = new JButton("Bestätigen");
        JButton close = new JButton("Abbrechen");

        // Components settings
        information_scroll.setViewportView(information);
        information.setLineWrap(true);
        information.setWrapStyleWord(true);

        name.setBounds(150, 100, 200, 50);
        name_info.setBounds(100, 100, 50, 50);
        id.setBounds(150, 200, 200, 50);
        id_info.setBounds(102, 200, 200, 50);
        birthday.setBounds(150, 300, 200, 50);
        birthday_info.setBounds(50, 300, 200, 50);
        gender_info.setBounds(50, 370, 200, 100);
        gender.setBounds(150, 400, 200, 50);
        information_scroll.setBounds(150, 500, 200, 100);
        information_info.setBounds(50, 500, 200, 100);
        get_informations.setBounds(150, 640, 200, 50);
        override_warning.setBounds(70, 1, 600, 20);
        override_warning_1.setBounds(100, 20, 600, 20);
        close.setBounds(150, 700, 200, 50);

        // Components functions
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endCurrentFrame(frame);
            }
        });

        get_informations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText().isBlank() || id.getText().isBlank() || birthday.getText().isBlank() || information.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Alle Textfelder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                writeInformations(frame, name.getText(), id.getText(), birthday.getText(), information.getText(), gender.getSelectedItem().toString());
            }
        });

        // Frame settings
        frame.add(gender);
        frame.add(gender_info);
        frame.add(birthday);
        frame.add(birthday_info);
        frame.add(id);
        frame.add(id_info);
        frame.add(name_info);
        frame.add(get_informations);
        frame.add(name);
        frame.add(close);
        frame.add(information_scroll);
        frame.add(information_info);
        frame.add(override_warning);
        frame.add(override_warning_1);
        frame.setLayout(null);
        frame.setSize(500, 850);
        frame.setResizable(false);
        frame.setVisible(true);
        Main.setIcon();
    }

    public static void writeInformations(JFrame frame, String name, String id, String old, String information, String gender) {
        frame.setVisible(false);

        File directory = new File("./users");
        if(!directory.exists()) {
            directory.mkdir();
        }

        try (FileOutputStream fos = new FileOutputStream("./users/user_" + id + ".wsuef");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Create user
            User user = new User(name, id, old, information, gender);

            // Write data to file
            oos.writeObject(user);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void endCurrentFrame(JFrame frame) {
        frame.setVisible(false);
    }
}
