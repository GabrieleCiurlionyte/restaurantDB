import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class DBFunctions {

    Validation val = new Validation();
    SQLFunctions sql = new SQLFunctions();
    PrintFunctions print = new PrintFunctions();
    public Connection connect_to_db(String dbname, String username, String password)
    {
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, username, password);
            if(connection != null) {
                System.out.println("Connection established.");
            }
            else {
                System.out.println("Connection failed.");
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return connection;
    }
    //CREATE
    public void UzregistruotiPadaveja(Connection con, Scanner sc) throws SQLException {
        try {
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            con.setAutoCommit(false);
            String vardas = val.ValidateString(sc, "Iveskite padavejo varda:");
            String pavarde = val.ValidateString(sc, "Iveskite padavejo pavarde");
            Double atlyginimas = val.ValidateDouble(sc, "Iveskite padavejo atlyginima");
            sql.UzregistruotiNaujoPadavejoInfoSQL(con, vardas, pavarde, atlyginimas);
            //Gauti nauja padavejo ID
            int padavejoID = sql.GautiPaskutinesEilutesID(con, "Padavejas", "PadavejoID");
            if (padavejoID != -1) { //If managed to get ID
                UzregistruotiPadavejoNr(con, sc, padavejoID);
            }
            con.commit();
            con.setAutoCommit(true);
        }
        catch(SQLException e){
            System.out.println("Klaida duombazeje.");
            con.rollback() ;
            con.setAutoCommit(true) ;
        }
    }
    public void PriimtiUzsakyma(Connection con, Scanner sc) {
        HashMap<Integer,Integer> padavejuCount = sql.GrazintiPadavejuAktyviuUzsakymuKieki(con);
        int padavejoID = findFirstSmallestKey(padavejuCount);
        if(padavejuCount.get(Integer.valueOf(padavejoID)) == 4){
            System.out.println("Nera laisvu padaveju. Uzsakymas atsauktas.");
            return;
        }
        else {
            sql.UzregistruotiUzsakymaSQL(con, padavejoID);
            int uzsakymoID = sql.GautiPaskutinesEilutesID(con,"Uzsakymas","UzsakymoID");
            SuzinotiUzsakymoTurini(con,sc, uzsakymoID);
        }
    }
    private int MeniuPasirinkimas(Connection con, Scanner sc) {
        print.PrintMeniu(con);
        HashSet<Integer> hs = sql.ReturnSetOfIDS(con,"Meniu_tipas","MeniuID");
        int pasirinkimas = val.ValidateInteger(sc, "Iveskite meniu indeksa:");
        if(hs.contains(pasirinkimas)) {
            return pasirinkimas;
        }
        else {
            System.out.println("Indeksas nepriklauso meniu");
            return MeniuPasirinkimas(con, sc);
        }
    }
    private void PatiekaloMeniuPasirinkimas(Connection con, Scanner sc, int meniuID, int uzsakymoID) {
        print.PrintPatiekaluMeniu(con, meniuID);
        HashSet<Integer> hs = sql.ReturnSetOfIDS(con, "Patiekalas", "PatiekaloID", String.format("MeniuID = %s", meniuID));
        int pasirinkimas = val.ValidateInteger(sc, "Iveskite patiekalo indeksa:");
        if (hs.contains(pasirinkimas)) {
            int turimas = sql.GautiProduktoTurimaKieki(con, pasirinkimas);
            int kiekis = val.ValidateInteger(sc, "Ivekite patiekalo kieki", turimas);
            System.out.println(kiekis);
            sql.IdetiPatiekalaIUzsakyma(con, uzsakymoID, pasirinkimas, meniuID, kiekis);
            System.out.println("Patiekalas itrauktas i uzsakyma");
            if(val.StopInputValidation(sc, "Ar norite ivesti nauja patiekala: t/n")){
                SuzinotiUzsakymoTurini(con,sc,uzsakymoID);
            }
            else {
                return;
            }
        } else {
            System.out.println("Indeksas nepriklauso meniu. Bandykite dar karta");
            PatiekaloMeniuPasirinkimas(con, sc, meniuID, uzsakymoID);
        }
    }
    private void SuzinotiUzsakymoTurini(Connection con, Scanner sc, int uzsakymoID){
        int meniuID = MeniuPasirinkimas(con,sc);
        PatiekaloMeniuPasirinkimas(con,sc, meniuID, uzsakymoID);
    }
    private int findFirstSmallestKey(HashMap<Integer,Integer> hp) {
        Integer minimum = Integer.MAX_VALUE;
        int key = -1;
        for(Integer i : hp.keySet()) {
            if(hp.get(i) < minimum) {
                minimum = hp.get(i);
                key = i.intValue();
            }
        }
        return key;
    }
    private void UzregistruotiPadavejoNr(Connection con, Scanner sc, int padavejoID) throws SQLException {
        if(val.StopInputValidation(sc,"Ar darbuotojas neturi telefono numerio: t/n")) {
            return;
        }
        else{
            int numeris = val.ValidateInteger(sc, "Iveskite telefono numeri:");
            sql.UzregistruotiNaujoPadavejoNrSQL(con,padavejoID, numeris);
            UzregistruotiPadavejoNr(con, sc, padavejoID);
        }
    }

}
