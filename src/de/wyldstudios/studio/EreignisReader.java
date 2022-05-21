package de.wyldstudios.studio;

import de.wyldstudios.data.Ereignis;
import de.wyldstudios.data.User;
import de.wyldstudios.extensions.ContentWidget;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.chrono.JapaneseDate;
import java.util.ArrayList;
import java.util.Arrays;

public class EreignisReader extends JFrame {
    public EreignisReader() {
        setTitle("Ereignis laden");
        setResizable(false);
        setSize(600, 470);
        setLayout(null);
        setVisible(true);
        getUser(this);
        Main.setIcon();
    }

    private void getUser(JFrame frame) {
        JDialog getuser = new JDialog();
        getuser.setTitle("Konfiguration");

        JLabel id_note = new JLabel("ID: ");
        JTextField id = new JTextField();
        JButton submit = new JButton("OK");

        id_note.setBounds(50, 15, 30, 30);
        id.setBounds(100, 15, 100, 30);
        submit.setBounds(120, 50, 60, 30);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Alle Textfelder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = EntryReader.loadEntryPublic(id.getText());
                File directory = new File("ereignisse\\user_" + id.getText());
                getuser.setVisible(false);
                components(user, frame, directory);
                frame.repaint();
            }
        });

        getuser.add(id_note);
        getuser.add(id);
        getuser.add(submit);
        getuser.setResizable(false);
        getuser.setLayout(null);
        getuser.setSize(300, 150);
        getuser.setVisible(true);
    }

    private void components(User user, JFrame frame, File directory) {
        frame.setTitle("Ereignis laden: " + user.name);

        ArrayList<Ereignis> a = new ArrayList<Ereignis>();
        File[] listOfFiles = directory.listFiles();

        if(listOfFiles == null) {
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "Es existiert noch kein Verzeichnis für diesen Kunden", "Noch kein Verzeichnis", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                Ereignis p = (Ereignis) this.readEreignis(listOfFiles[i].getName(), user.id);
                a.add(p);
            }
        }

        Ereignis[] s = new Ereignis[a.size()];
        a.toArray(s);

        JList<Ereignis> events = new JList<Ereignis>(s);
        JScrollPane events_scroll = new JScrollPane(events);
        JButton close = new JButton("Abbrechen");
        events_scroll.setViewportView(events);
        events_scroll.setBounds(40, 10, 500, 300);
        close.setBounds(250, 350, 100, 50);

        frame.add(events_scroll);
        frame.add(close);
        events.repaint();
        frame.repaint();
        repaintEvent(events, events_scroll, frame);

        getContent(events, user.id);
        break_process(close, frame);
    }

    private void break_process(JButton close, JFrame frame) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
    }

    private void repaintEvent(JList e, JScrollPane a, JFrame f) {
        f.update(e.getGraphics());
        f.update(a.getGraphics());
        e.repaint();
        a.repaint();
        f.repaint();
        e.setVisible(false);
        a.setVisible(false);
        e.setVisible(true);
        a.setVisible(true);
    }

    public Ereignis readEreignis(String filename, String userid) {
        try (FileInputStream fis = new FileInputStream("ereignisse\\user_" + userid + "\\" + filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Ereignis ereignis = (Ereignis) ois.readObject();

            return ereignis;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new Ereignis(null, null, null, null);
    }

    private void getContent(JList list, String userid) {
        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    int index = list.locationToIndex(e.getPoint());
                    ListModel dlm = list.getModel();
                    Ereignis item = (Ereignis) dlm.getElementAt(index);;
                    list.ensureIndexIsVisible(index);
                    ContentWidget contentWidget = new ContentWidget(item, userid);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
