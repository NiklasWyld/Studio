package de.wyldstudios.data;

import java.io.Serializable;

public class Ereignis implements Serializable {
    public String title;
    public String date;
    public String datetime;
    public String content;

    public Ereignis(String title, String date, String datetime, String content) {
        this.title = title;
        this.date = date;
        this.datetime = datetime;
        this.content = content;
    }

    @Override
    public String toString() {
        return this.date + ": " +  this.title;
    }
}
