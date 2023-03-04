import DBObjectModels.Patiekalas;
import DBObjectModels.Uzsakymas;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintFunctions {
    public void PrintMeniu(Connection con) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT * FROM meniu_tipas;");
            ResultSet rs =  stmt.executeQuery(string);
            printListTop(37);
            while(rs.next()) {
                int meniuID = rs.getInt("MeniuID");
                String pavadinimas = rs.getString("Pavadinimas");
                String s = String.format("|%-3d|%-32s|", meniuID, pavadinimas);
                System.out.println(s);
            }
            printListTop(37);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
    }
    private void printListTop(int times) {
        for(int i = 0; i < times; i++){
            System.out.print('-');
        }
        System.out.print('\n');
    }
    public void PrintPatiekaluMeniu(Connection con, int meniuID) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT * FROM Patiekalas WHERE MeniuID = %d AND turimasKiekis >= 0", meniuID);
            ResultSet rs =  stmt.executeQuery(string);
            printListTop(63);
            while(rs.next()) {
                int patiekaloID = rs.getInt("PatiekaloID");
                String pavadinimas = rs.getString("Pavadinimas");
                Double kaina = rs.getDouble("Kaina");
                String s = String.format("|%-3d|%-50s|%-8.2f|", patiekaloID, pavadinimas, kaina);
                System.out.println(s);
            }
            printListTop(63);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
    }
    public void PrintUzsakymuInfo(Connection con) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT UzsakymoID, padavejoID FROM Uzsakymas WHERE busena='Pradetas';");
            ResultSet rs =  stmt.executeQuery(string);
            printListTop(27);
            System.out.println("| uzsakymoID | padavejoID |");
            printListTop(27);
            while(rs.next()) {
                int uzsakymoID = rs.getInt("uzsakymoID");
                int padavejoID = rs.getInt("padavejoID");
                String s = String.format("|%-12d|%-12d|", uzsakymoID, padavejoID);
                System.out.println(s);
            }
            printListTop(27);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
    }
    public void PrintMenuKiekis(Connection con) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT patiekaloID, meniuID, pavadinimas, turimasKiekis FROM Patiekalas;");
            ResultSet rs =  stmt.executeQuery(string);
            printListTop(85);
            System.out.println(String.format("|%-13s|%-9s|%-50s|%-8s|", "PatiekaloID", "MeniuID", "Pavadinimas","Kiekis"));
            printListTop(85);
            while(rs.next()) {
                int patiekaloID = rs.getInt("PatiekaloID");
                int meniuID = rs.getInt("MeniuID");
                String pavadinimas = rs.getString("Pavadinimas");
                int kiekis = rs.getInt("TurimasKiekis");
                String s = String.format("|%-13d|%-9d|%-50s|%-8d|", patiekaloID, meniuID, pavadinimas, kiekis);
                System.out.println(s);
            }
            printListTop(85);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened." );
        }
    }
    public void PrintBaigtiUzsakymai(ArrayList<Uzsakymas> baigtiUzsakymai) {
        printListTop(54);
        System.out.println(String.format("|%-12s|%-12s|%-26s|", "UzsakymoID", "PadavejoID", "Uzsakymo pradzios data"));
        printListTop(54);
        for(Uzsakymas uz : baigtiUzsakymai){
            int uzsakymoID = uz.getUzsakymoID();
            int padavejoID = uz.getPadavejoID();
            Timestamp data = uz.getTime();
            String s = String.format("|%-12d|%-12d|%-26s|", uzsakymoID, padavejoID, data.toString());
            System.out.println(s);
        }
        printListTop(54);
    }
    public void PrintFiltruotiPatiekalai(ArrayList<Patiekalas> filtruotiPatiekalai) {
        printListTop(87);
        System.out.println(String.format("|%-13s|%-9s|%-50s|%-10s|", "PatiekaloID", "MeniuID", "Pavadinimas", "Kaina"));
        printListTop(87);
        for(Patiekalas p : filtruotiPatiekalai){
            int patiekaloID = p.getPatiekaloID();
            int meniuID = p.getMeniuID();
            String pavadinimas= p.getPavadinimas();
            Double kaina = p.getKaina();
            System.out.println(String.format("|%-13d|%-9d|%-50s|%-10.2f|)", patiekaloID, meniuID, pavadinimas, kaina));
        }
        printListTop(87);
    }
}
