package de.wyldstudios.extensions;

import de.wyldstudios.data.Settings;
import de.wyldstudios.studio.LoginPanel;
import de.wyldstudios.studio.Main;
import de.wyldstudios.studio.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.*;

public class SettingsWidget extends JDialog {
    public SettingsWidget() {
        setTitle("Einstellungen");
        setSize(500, 400);
        setLayout(null);
        setResizable(false);
        settings();
        setVisible(true);
        Main.setIcon();
    }

    public static void setUpListener(MenuItem mi) {
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsWidget settingsWidget = new SettingsWidget();
            }
        });
    }

    private void settings() {
        JLabel icon_note = new JLabel("Icon: ");
        JTextField icon = new JTextField("maus.jpg");
        JButton select_icon = new JButton("Auswählen");
        JLabel company_note = new JLabel("Firma: ");
        JTextField company = new JTextField("Wyld-Studios");
        JButton save = new JButton("Speichern");

        icon_note.setBounds(20, 20, 100, 30);
        icon.setBounds(100, 20, 150, 30);
        select_icon.setBounds(300, 20, 100, 30);
        company_note.setBounds(20, 60, 100, 30);
        company.setBounds(100, 60, 150, 30);
        save.setBounds(380, 300, 100, 40);

        icon.setText(Main.img.getDescription());

        save_settings(save, icon, company);
        getImage(select_icon, icon);

        this.add(icon_note);
        this.add(icon);
        this.add(select_icon);
        this.add(company_note);
        this.add(company);
        this.add(save);
    }

    private void save_settings(JButton save, JTextField path, JTextField company) {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.img = new ImageIcon(path.getText());
                Main.setIcon();

                UI.frame.setTitle(company.getText() + " Studio | " + LoginPanel.mitarbeiter.name + " | Zimmer " + LoginPanel.zimmer_s);

                Main.writeSettings(new Settings(Main.img, company.getText()));
            }
        });
    }

    private void getImage(JButton select, JTextField icon) {
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if (f.isDirectory()) {
                            return true;
                        } else {
                            return f.getName().toLowerCase().endsWith(".jpg");
                        }
                    }

                    @Override
                    public String getDescription() {
                        return "JPG Files (*.jpg)";
                    }
                });

                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if (f.isDirectory()) {
                            return true;
                        } else {
                            return f.getName().toLowerCase().endsWith(".png");
                        }
                    }

                    @Override
                    public String getDescription() {
                        return "PNG Files (*.png)";
                    }
                });

                int r = fileChooser.showOpenDialog(null);

                if(r == JFileChooser.APPROVE_OPTION) {
                    String d = fileChooser.getSelectedFile().toPath().toString();
                    icon.setText(d);
                }
            }
        });
    }
}
