package de.wyldstudios.studio;

import de.wyldstudios.data.Mitarbeiter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MitarbeiterReader {
    public Mitarbeiter getMitarbeiter(String username) {
        try (FileInputStream fis = new FileInputStream("./mitarbeiter/mitarbeiter_" + username + ".wsmf");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Mitarbeiter mitarbeiter = (Mitarbeiter) ois.readObject();

            return mitarbeiter;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
