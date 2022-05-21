package de.wyldstudios.studio;

import de.wyldstudios.data.Mitarbeiter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JFrame {
    int masterpassword = -1778893037;
    public JTextField username;
    public JTextField zimmer;
    public JPasswordField password;
    public static Mitarbeiter mitarbeiter;
    public static String zimmer_s;

    public LoginPanel(JFrame mainframe) {
        MenuBar menuBar = new MenuBar();
        Menu user = new Menu("Nutzer");
        MenuItem add_user = new MenuItem("Mitarbeiter hinzufügen");

        menuBar.add(user);
        user.add(add_user);

        add_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_mitarbeiter();
            }
        });

        setMenuBar(menuBar);
        mainframe.setVisible(false);
        setLayout(null);
        //setResizable(false);
        components();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Wyld-Studios Studio");
        setVisible(true);
    }

    private void components() {
        JLabel title = new JLabel("Studio");
        JLabel username_note = new JLabel("Nutzername: ");
        JLabel zimmer_note = new JLabel("Zimmer: ");
        JLabel password_note = new JLabel("Kennwort: ");
        username = new JTextField();
        zimmer = new JTextField();
        password = new JPasswordField();
        JButton submit = new JButton("Einloggen");
        JLabel false_password = new JLabel("Falscher Benutzername oder falsches Passwort");

        false_password.setForeground(Color.red);
        false_password.setBounds(830, 540, 300, 50);
        false_password.setVisible(false);

        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setBounds(850, 100, 500, 100);

        username_note.setBounds(770, 300, 100, 50);
        username.setBounds(860, 300, 200, 50);

        zimmer_note.setBounds(770, 400, 100, 50);
        zimmer.setBounds(860, 400, 200, 50);

        password_note.setBounds(770, 500, 100, 50);
        password.setBounds(860, 500, 200, 50);

        submit.setBounds(900, 600, 100, 50);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(username.getText().isBlank() || password.getText().isBlank() || zimmer.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Alle Textfelder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                prove_mitarbeiter(username.getText(), password.getText(), false_password, zimmer);
            }
        });

        this.add(false_password);
        this.add(title);
        this.add(submit);
        this.add(username_note);
        this.add(zimmer_note);
        this.add(password_note);
        this.add(username);
        this.add(zimmer);
        this.add(password);
    }

    private void add_mitarbeiter() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Mitarbeiter hinzufügen");

        JLabel username_note = new JLabel("Nutzername: ");
        JLabel password_note = new JLabel("Kennwort: ");
        JLabel name_note = new JLabel("Name: ");
        JLabel adminpassword_note = new JLabel("Masterkennwort: ");
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JTextField name = new JTextField();
        JPasswordField adminpassword = new JPasswordField();
        JLabel false_password = new JLabel("Falsches Masterpasswort");
        JButton submit = new JButton("Erstellen");

        false_password.setForeground(Color.red);
        false_password.setBounds(120, 440, 200, 50);
        false_password.setVisible(false);

        username.setBounds(100, 100, 200, 50);
        username_note.setBounds(20, 100, 80, 50);

        password.setBounds(100, 200, 200, 50);
        password_note.setBounds(30, 200, 70, 50);

        name.setBounds(100, 300, 200, 50);
        name_note.setBounds(50, 300, 50, 50);

        adminpassword.setBounds(100, 400, 200, 50);
        adminpassword_note.setBounds(1, 400, 100, 50);

        submit.setBounds(150, 500, 90, 50);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(adminpassword.getText() == "") {
                    false_password.setVisible(true);
                    return;
                }
                if(username.getText().isBlank() || password.getText().isBlank() || name.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Alle Textfelder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(adminpassword.getText().hashCode() == masterpassword) {
                    Mitarbeiter ma = new Mitarbeiter();
                    ma.username = username.getText();
                    ma.name = name.getText();
                    ma.password = password.getText().hashCode();

                    MitarbeiterWriter m = new MitarbeiterWriter();
                    m.writeMitarbeiter(ma);

                    false_password.setVisible(false);
                    JOptionPane.showMessageDialog(null, username.getText() + " wurde erfolgreich erstellt!", "Erstellt", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    false_password.setVisible(true);
                    return;
                }
            }
        });

        dialog.add(false_password);
        dialog.add(submit);
        dialog.add(username_note);
        dialog.add(password_note);
        dialog.add(name_note);
        dialog.add(adminpassword_note);
        dialog.add(username);
        dialog.add(password);
        dialog.add(name);
        dialog.add(adminpassword);
        dialog.setResizable(false);
        dialog.setSize(400, 700);
        dialog.setLayout(null);
        dialog.setVisible(true);
    }

    private void prove_mitarbeiter(String username, String password, JLabel false_password, JTextField zimmer) {
        MitarbeiterReader mr = new MitarbeiterReader();
        Mitarbeiter m = mr.getMitarbeiter(username);
        if (m == null) {
            JOptionPane.showMessageDialog(null, "Es wurde kein Mitarbeiter unter diesem Nutzernamen gefunden", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(m.password == password.hashCode()) {
            UI.frame.setVisible(true);
            this.setVisible(false);
            false_password.setVisible(false);
            Main.setIcon();
            mitarbeiter = m;
            zimmer_s = zimmer.getText();
            UI.frame.setTitle(Main.settings.company + " Studio | " + mitarbeiter.name + " | Zimmer " + zimmer_s);
        } else {
            false_password.setVisible(true);
            return;
        }
    }
}
