import DBObjectModels.Patiekalas;
import DBObjectModels.Uzsakymas;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SearchFunctions {

    PrintFunctions pf = new PrintFunctions();

    public void GrazintiIvykdytusUzsakymus(Connection con, SQLFunctions sql){
        ArrayList<Uzsakymas> baigtiUzsakymai = sql.GrazintiIvykdytusUzsakymus(con);
        pf.PrintBaigtiUzsakymai(baigtiUzsakymai);
    }

    public void GrazintiPatiekalusTarpKainu(Connection con, SQLFunctions sql, Scanner sc, Validation val){
        double first = val.ValidateDouble(sc, "Iveskite maziausia galima kaina");
        double second = val.ValidateDouble(sc, "Iveskite didziausia galima kaina");
        ArrayList<Patiekalas> arrP = sql.RastiPatiekalaTarpKainu(con, first, second);
        pf.PrintFiltruotiPatiekalai(arrP);
    }

    public void GetUzsakymoSuma(Connection con, SQLFunctions sql, Scanner sc, Validation val) {
        pf.PrintUzsakymuInfo(con);
        int uzsakymoID = val.ValidateInteger(sc, "Iveskite uzsakymoID:");
        HashSet<Integer> hs = sql.ReturnSetOfIDS(con,"Uzsakymas","UzsakymoID");
        if(hs.contains(uzsakymoID)) {
            double sum = sql.GrazintiBendraUzsakymoSuma(con,uzsakymoID);
            if(sum == -1) {
                System.out.print("Uzsakymas yra tuscias");
            }
            else{
                System.out.println(String.format("Uzsakymo %d suma %.2f€", uzsakymoID, sum));
            }
        }
        else{
            System.out.println("Ivestas neegzistuojantis uzsakymo ID. Bandykite is naujo.");
            GetUzsakymoSuma(con,sql,sc,val);
        }
    }
}
