package de.wyldstudios.studio;

import de.wyldstudios.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class EntryReader {
    public static void readEntry() {
        // Read entry frame
        JFrame frame = new JFrame("Eintrag laden");

        // Components
        JTextField id = new JTextField();
        JLabel id_info = new JLabel("ID:");
        JButton load = new JButton("Laden");
        JLabel information_name = new JLabel("Name: NaN");
        JLabel information_birthday = new JLabel("Geburtsdatum: NaN");
        JLabel information_gender = new JLabel("Geschlecht: NaN");
        JLabel information_note = new JLabel("Informationen:");
        JTextArea information_information = new JTextArea("NaN");
        JScrollPane information_information_scroll = new JScrollPane(information_information);
        JLabel information_false_user = new JLabel("Eintrag nicht gefunden");
        JButton close = new JButton("Abbrechen");

        // Components settings
        information_information_scroll.setViewportView(information_information);
        information_information.setLineWrap(true);
        information_information.setWrapStyleWord(true);
        information_information.setEditable(false);

        information_false_user.setForeground(Color.red);
        information_false_user.setVisible(false);

        id.setBounds(150, 100, 200, 50);
        id_info.setBounds(102, 100, 200, 50);
        load.setBounds(150, 420, 200, 50);
        information_name.setBounds(150, 200, 200, 50);
        information_birthday.setBounds(150, 220, 200, 50);
        information_gender.setBounds(150, 240, 200, 50);
        information_note.setBounds(150, 260, 200, 50);
        information_information_scroll.setBounds(150, 300, 200, 100);
        information_false_user.setBounds(180, 140, 200, 50);
        close.setBounds(150, 480, 200, 50);

        // Components functions
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Entry.endCurrentFrame(frame);
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEntry(frame, id.getText(), information_name, information_birthday, information_information, information_false_user, information_gender);
            }
        });

        // Frame settings
        frame.add(close);
        frame.add(information_false_user);
        frame.add(id);
        frame.add(id_info);
        frame.add(information_name);
        frame.add(information_birthday);
        frame.add(information_note);
        frame.add(information_gender);
        frame.add(information_information_scroll);
        frame.add(load);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(500, 600);
        frame.setVisible(true);
        Main.setIcon();
    }

    public static void loadEntry(JFrame frame, String id, JLabel information_name, JLabel information_old, JTextArea information_information, JLabel false_user, JLabel information_gender) {
        try (FileInputStream fis = new FileInputStream("users\\user_" + id + ".wsuef");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            information_name.setText("Name: " + user.name);
            information_old.setText("Geburtsdatum: " + user.old);
            information_gender.setText("Geschlecht: " + user.gender);
            information_information.setText(user.information);

        } catch (IOException | ClassNotFoundException ex) {
            false_user.setVisible(true);
            ex.printStackTrace();
        }
    }

    public static User loadEntryPublic(String id) {
        try (FileInputStream fis = new FileInputStream("users\\user_" + id + ".wsuef");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            return user;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new User();
    }

    public static User loadEntryPublicWithoutArgs(String id) {
        try (FileInputStream fis = new FileInputStream("users\\" + id);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            return user;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new User();
    }
}