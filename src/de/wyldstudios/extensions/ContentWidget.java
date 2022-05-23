package de.wyldstudios.extensions;

import de.wyldstudios.data.Ereignis;
import de.wyldstudios.studio.EreignisReader;
import de.wyldstudios.studio.EreignisWriter;
import de.wyldstudios.studio.UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ContentWidget extends JDialog {
    public ContentWidget(Ereignis ereignis, String userid) {
        setTitle(ereignis.date + ": " + ereignis.title);
        setSize(500, 450);
        setLayout(null);
        setResizable(false);
        components(ereignis, userid);
        setVisible(true);
    }

    private void components(Ereignis ereignis, String userid) {
        JTextArea content = new JTextArea(ereignis.content);
        JScrollPane content_scroll = new JScrollPane(content);
        JButton ok = new JButton("OK");
        JButton delete = new JButton("Löschen");

        content_scroll.setViewportView(content);
        content_scroll.setBounds(15, 10, 450, 330);
        ok.setBounds(150, 350, 60, 30);
        delete.setBounds(250, 350, 100, 30);

        safeDelete(ereignis, delete, this, userid);
        safeSave(this, ok, ereignis, userid, content);

        this.add(content_scroll);
        this.add(ok);
        this.add(delete);
    }

    private void safeDelete(Ereignis ereignis, JButton delete, JDialog d, String userid) {
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(d,
                        "Bist du sicher, dass du " + ereignis.title + " löschen möchtest?", "Löschen?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    File file = new File("./ereignisse/" + "user_" + userid + "/" + ereignis.datetime + ".wser");
                    if (!file.delete()) {
                        JOptionPane.showMessageDialog(null, "Es ist ein Fehler bei Löschen von " + ereignis.title + " aufgetreten", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                    d.setVisible(false);
                    UI.er.setVisible(false);
                } else {
                    return;
                }
            }
        });
    }

    private void safeSave(JDialog d, JButton ok, Ereignis ereignis, String userid, JTextArea field) {
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EreignisWriter.writeEventOut(userid, field.getText(), ereignis);
                d.setVisible(false);
            }
        });
    }
}
