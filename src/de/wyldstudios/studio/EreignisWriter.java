package de.wyldstudios.studio;

import de.wyldstudios.data.Ereignis;
import de.wyldstudios.data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class EreignisWriter extends JFrame {
    public EreignisWriter() {
        setTitle("Ereignis erstellen");
        setResizable(false);
        setSize(800, 600);
        setLayout(null);
        components();
        setVisible(true);
        Main.setIcon();
    }

    private void components() {
        JLabel id_note = new JLabel("ID:");
        JTextField id = new JTextField();
        JLabel title_note = new JLabel("Titel:");
        JTextField title = new JTextField();
        JTextArea content = new JTextArea();
        JScrollPane content_scroll = new JScrollPane(content);
        JButton create = new JButton("Erstellen");
        JButton close = new JButton("Abbrechen");

        content_scroll.setViewportView(content);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);

        id_note.setBounds(250, 25, 50, 30);
        id.setBounds(300, 20, 150, 40);
        title_note.setBounds(250, 85, 50, 30);
        title.setBounds(300, 80, 150, 40);
        content_scroll.setBounds(10, 140, 760, 300);
        create.setBounds(300, 450, 150, 40);
        close.setBounds(300, 500, 150, 40);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeEvent(id, title, content);
            }
        });

        this.add(id_note);
        this.add(id);
        this.add(title_note);
        this.add(title);
        this.add(content_scroll);
        this.add(create);
        this.add(close);

        close_window(close, this);
    }

    private void close_window(JButton close, JFrame frame) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
    }

    private void writeEvent(JTextField id, JTextField title, JTextArea content) {
        if(id.getText().isBlank() || title.getText().isBlank() || content.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Alle Textfelder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File directory1 = new File(".\\ereignisse");
        if(!directory1.exists()) {
            directory1.mkdir();
        }

        File directory = new File(".\\ereignisse\\user_" + id.getText());
        if(!directory.exists()) {
            directory.mkdir();
        }

        DateFormat formatter = new SimpleDateFormat("hh-mm-ss");
        String time = formatter.format(new Date());
        String datetime = LocalDate.now().toString() + "--" + time;

        try (FileOutputStream fos = new FileOutputStream("ereignisse\\user_" + id.getText() + "\\" + datetime + ".wser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Create user
            Ereignis ereignis = new Ereignis(title.getText(), LocalDate.now().toString(), datetime, content.getText());

            // Write data to file
            oos.writeObject(ereignis);
            this.setVisible(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
