package de.wyldstudios.studio;

import de.wyldstudios.data.Mitarbeiter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

public class MitarbeiterWriter {
    public void writeMitarbeiter(Mitarbeiter mitarbeiter) {
        File f = new File("./mitarbeiter");
        if (!f.exists()) {
            new File("./mitarbeiter").mkdir();
        }

        try (FileOutputStream fos = new FileOutputStream("./mitarbeiter/mitarbeiter_" + mitarbeiter.username + ".wsmf");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Write data to file
            oos.writeObject(mitarbeiter);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
