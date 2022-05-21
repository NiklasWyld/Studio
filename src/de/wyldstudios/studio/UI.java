package de.wyldstudios.studio;

import de.wyldstudios.data.User;
import de.wyldstudios.extensions.SettingsWidget;
import de.wyldstudios.extensions.Wartezimmer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class UI {
    public static JFrame frame = new JFrame();
    public static EreignisReader er;
    public static Wartezimmer wartezimmer = new Wartezimmer();

    public static void startApp(String windowname) {
        // Settings for frame
        wartezimmer.setVisible(false);
        frame.setTitle(windowname);
        frame.setLayout(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setResizable(false);
        frame.setVisible(false);

        // Call setupUI method
        Main.setIcon();
        setupUI(frame);
        end_listener(frame);
    }

    public static void end_listener(JFrame frame) {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Bist du sicher, dass du Wyld-Studios Studio beenden willst?", "Studio beenden?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void setupUI(JFrame frame) {
        JLabel title = new JLabel("Studio");

        JButton new_entry = new JButton("Neuer Eintrag");
        JButton load_entry = new JButton("Eintrag laden");
        JButton new_event = new JButton("Neues Ereignis");
        JButton load_event = new JButton("Ereignis laden");
        JButton end_app = new JButton("Beenden");

        // MenuBar

        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("Studio");
        Menu management = new Menu("Verwaltung");

        MenuItem file_wartezimmer = new MenuItem("Wartezimmer");
        MenuItem file_settings = new MenuItem("Einstellungen");
        MenuItem file_logout = new MenuItem("Abmelden");
        MenuItem file_close = new MenuItem("Beenden");

        MenuItem management_list = new MenuItem("Kundenliste");
        MenuItem management_delete = new MenuItem("Eintrag löschen");

        // MenuBar Settings

        file.add(file_wartezimmer);
        file.add(file_settings);
        file.add(file_logout);
        file.add(file_close);

        management.add(management_list);
        management.add(management_delete);

        menuBar.add(file);
        menuBar.add(management);

        frame.setMenuBar(menuBar);
        frame.validate();
        frame.repaint();

        // Set other things

        title.setFont(new Font("Arial", Font.BOLD, 50));

        // Set bounds of components

        title.setBounds(850, 100, 500, 100);
        new_entry.setBounds(820, 500, 200, 40);
        load_entry.setBounds(820, 550, 200, 40);
        new_event.setBounds(820, 600, 200, 40);
        load_event.setBounds(820, 650, 200, 40);
        end_app.setBounds(820, 700, 200, 40);

        // Add components to frame

        frame.add(load_entry);
        frame.add(title);
        frame.add(new_entry);
        frame.add(new_event);
        frame.add(load_event);
        frame.add(end_app);

        // Call logic methods

        endApp(frame, file_close);
        delete_user(frame, management_delete);
        user_list(management_list);
        logout(frame, file_logout);
        SettingsWidget.setUpListener(file_settings);
        loadWartezimmer(file_wartezimmer);

        setLogicOfEndApp(frame, end_app);
        setLogicOfNewEntry(frame, new_entry);
        setLogicOfLoadEntry(frame, load_entry);
        setLogicOfNewEvent(new_event);
        setLogicOfLoadEvent(load_event);
    }

    public static void setLogicOfEndApp(JFrame frame, JButton end_app) {
        end_app.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Bist du sicher, dass du Wyld-Studios Studio beenden willst?", "Studio beenden?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void setLogicOfNewEntry(JFrame frame, JButton new_entry) {
        new_entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Entry.newEntry(frame);
            }
        });
    }

    public static void setLogicOfLoadEntry(JFrame frame, JButton load_entry) {
        load_entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntryReader.readEntry();
            }
        });
    }

    public static void endApp(JFrame frame, MenuItem mi) {
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Bist du sicher, dass du Wyld-Studios Studio beenden willst?", "Studio beenden?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void logout(JFrame mainframe, MenuItem mi) {
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for(Window win : windows) {
                    win.setVisible(false);
                }
                Main.loginPanel.setVisible(true);
                Main.loginPanel.username.setText("");
                Main.loginPanel.password.setText("");
                Main.loginPanel.zimmer.setText("");
            }
        });
    }

    public static void delete_user(JFrame frame, MenuItem mi) {
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog get_user = new JDialog();
                get_user.setTitle("Eintrag löschen");

                JLabel user_id_note = new JLabel("ID:");
                JTextField user_id = new JTextField();
                JButton submit = new JButton("Bestätigen");
                JLabel false_info = new JLabel("Eintrag nicht gefunden");

                false_info.setForeground(Color.red);
                false_info.setVisible(false);

                false_info.setBounds(90, 10, 130, 40);
                user_id_note.setBounds(50, 50, 200, 50);
                user_id.setBounds(100, 50, 100, 50);
                submit.setBounds(100, 120, 100, 20);

                get_user.add(user_id_note);
                get_user.add(user_id);
                get_user.add(submit);
                get_user.add(false_info);

                delete_user_final(submit, false_info, get_user, user_id);

                get_user.setResizable(false);
                get_user.setLayout(null);
                get_user.setSize(300, 200);
                get_user.setVisible(true);
            }
        });
    }

    public static void delete_user_final(JButton submit, JLabel false_info, JDialog get_user, JTextField f) {
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = f.getText();
                if (id.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Das Textfeld muss ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                User user = EntryReader.loadEntryPublic(id);
                if (delete_user_profe(get_user, user)) {
                    try {
                        Files.deleteIfExists(Paths.get("users\\user_" + id + ".wsuef"));
                        false_info.setText("Erfolgreich gelöscht");
                        false_info.setVisible(true);
                    } catch (IOException ioException) {
                        false_info.setText("Eintrag nicht gefunden");
                        false_info.setVisible(true);
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    public static boolean delete_user_profe(JDialog dialog, User user) {
        if (JOptionPane.showConfirmDialog(dialog,
                "Bist du sicher, dass du diesen Eintrag löschen möchtest? Name: " + user.name, "Bist du sicher?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    public static void user_list(MenuItem mi) {
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog user_list = new JDialog();
                user_list.setTitle("Kundenliste");

                ArrayList<String> a = new ArrayList<String>();

                File folder = new File("users");
                File[] listOfFiles = folder.listFiles();

                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        User p = (User) EntryReader.loadEntryPublicWithoutArgs(listOfFiles[i].getName());
                        a.add(p.name + " - " + p.id);
                    }
                }

                String[] s = new String[a.size()];
                a.toArray(s);

                JList list = new JList(s);
                JScrollPane list_scroll = new JScrollPane(list);
                list_scroll.setViewportView(list);

                list_scroll.setBounds(35, 50, 300, 500);

                user_list.add(list_scroll);
                user_list.setResizable(false);
                user_list.setSize(400, 630);
                user_list.setLayout(null);
                user_list.setVisible(true);
            }
        });
    }

    public static void setLogicOfNewEvent(JButton new_event) {
        new_event.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EreignisWriter ew = new EreignisWriter();
            }
        });
    }

    public static void setLogicOfLoadEvent(JButton load_event) {
        load_event.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        er = new EreignisReader();
                    }
                }
        );
    }

    public static void loadWartezimmer(MenuItem mi) {
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wartezimmer.setVisible(true);
            }
        });
    }
}

// x, y, width, height