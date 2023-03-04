import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class UpdateFunctions {

    PrintFunctions print = new PrintFunctions();
    Validation val = new Validation();

    public void PakeistiUzsakymoBusena(Connection con, Scanner sc, SQLFunctions sql) {
        print.PrintUzsakymuInfo(con);
        HashSet<Integer> pradetiUzsak = sql.ReturnSetOfIDS(con,"Uzsakymas","UzsakymoID","Busena = 'Pradetas'");
        int uzsakymoID = val.ValidateInteger(sc,"Iveskite uzsakymoID, kuri norite atnaujinti");
        if(pradetiUzsak.contains(uzsakymoID)) {
            sql.BaigtiUzsakymaSQL(con, uzsakymoID);
            System.out.println(String.format("Uzsakymo %d busena atnaujinta i 'Baigta'",uzsakymoID));
        }
        else {
            System.out.println("Pasirinktas uzsakymo ID neegzistuoja. Bandykite dar karta");
            PakeistiUzsakymoBusena(con,sc,sql);
        }
    }

    public void AtnaujintiTurimuPatiekaluKieki(Connection con, Scanner sc, SQLFunctions sql) {
        print.PrintMenuKiekis(con);
        HashSet<Integer> hs = sql.ReturnSetOfIDS(con,"Patiekalas","PatiekaloID");
        int patiekaloID = val.ValidateInteger(sc,"Iveskite patiekaloID, kuri norite atnaujinti");
        int kiekis = val.ValidateInteger(sc, "Iveskite nauja patiekalo kieki");
        if(hs.contains(patiekaloID)) {
            sql.AtnaujintiPatiekaloKiekiSQL(con,patiekaloID,kiekis);
            System.out.println(String.format("Patiekalo %d kiekis atnaujintas i %d",patiekaloID, kiekis));
        }
        else {
            System.out.println("Pasirinktas patiekalo ID neegzistuoja. Bandykite dar karta");
            AtnaujintiTurimuPatiekaluKieki(con,sc,sql);
        }
    }
}
