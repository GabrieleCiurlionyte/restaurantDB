import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Program_loop {

    SQLFunctions sql = new SQLFunctions();
    UpdateFunctions up = new UpdateFunctions();
    SearchFunctions sf = new SearchFunctions();
    DeleteFunctions del = new DeleteFunctions();
    Validation val = new Validation();
    DBFunctions db = new DBFunctions();
    Scanner sc = new Scanner(System.in);
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public void PrintProgramLoop(Connection con) {
        boolean continuation = true;
        while (continuation){
            System.out.println("Pasirinkite norima metoda:");
            System.out.println("1. Uzregistruoti padaveja.");
            System.out.println("2. Priimti uzsakyma.");
            System.out.println("3. Pakeisti uzsakymo busena.");
            System.out.println("4. Atnaujinti turimu patiekalu kieki.");
            System.out.println("5. Grazinti ivykdytus uzsakymus.");
            System.out.println("6. Grazinti patiekalus esancius pasirinktame kainu diapazone.");
            System.out.println("7. Gauti uzsakymu suma.");
            System.out.println("8. Atsaukti uzsakyma.");
            System.out.println("9. Iseiti is programos.");
            try{
                int pasirinkimas = Integer.parseInt(bf.readLine());
                continuation = functionExecution(pasirinkimas,con,sc);
            }
            catch (IOException e){
                System.out.println("Neteisinga ivestis");
                continuation = false;
            }
        }
    }
    private boolean functionExecution(int pasirinkimas, Connection con, Scanner sc){
        switch (pasirinkimas) {
            case 1:
                try{
                    db.UzregistruotiPadaveja(con,sc);
                    return true;
                }
                catch(SQLException e){
                    System.out.println("Transakcijos klaida");
                    return false;
                }
            case 2:
                db.PriimtiUzsakyma(con,sc);
                return true;
            case 3:
                up.PakeistiUzsakymoBusena(con,sc,sql);
                return true;
            case 4:
                up.AtnaujintiTurimuPatiekaluKieki(con,sc,sql);
                return true;
            case 5:
                sf.GrazintiIvykdytusUzsakymus(con,sql);
                return true;
            case 6:
                sf.GrazintiPatiekalusTarpKainu(con,sql,sc,val);
                return true;
            case 7:
                sf.GetUzsakymoSuma(con,sql,sc,val);
                return true;
            case 8:
                try{
                    del.atsauktiUzsakyma(con,sc,sql);
                    return true;
                }
                catch(SQLException e) {
                    System.out.println("Nepavyko ivykdyti metodo");
                    return true;
                }
            case 9:
                return false;
            default:
                System.out.println("Ivestis neteisinga");
                return true;
        }
    }
}
