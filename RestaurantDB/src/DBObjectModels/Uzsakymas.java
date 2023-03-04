package DBObjectModels;

import java.sql.Time;
import java.sql.Timestamp;

public class Uzsakymas {
    private int uzsakymoID;
    private int padavejoID;

    private Timestamp time;

    public Uzsakymas(int uzsakymoID, int padavejoID, Timestamp time){
        this.uzsakymoID = uzsakymoID;
        this.padavejoID = padavejoID;
        this.time = time;
    }
    public int getUzsakymoID() {
        return uzsakymoID;
    }

    public void setUzsakymoID(int uzsakymoID) {
        this.uzsakymoID = uzsakymoID;
    }

    public int getPadavejoID() {
        return padavejoID;
    }

    public void setPadavejoID(int padavejoID) {
        this.padavejoID = padavejoID;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

}
