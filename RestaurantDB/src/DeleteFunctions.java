import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;

public class DeleteFunctions {

    Validation val = new Validation();
    PrintFunctions pf = new PrintFunctions();

    public void atsauktiUzsakyma(Connection con, Scanner sc, SQLFunctions sql) throws SQLException {
        try{
            con.setAutoCommit(false);
            pf.PrintUzsakymuInfo(con);
            int uzsakymoID = val.ValidateInteger(sc, "Iveskite norimo istrinti uzsakymo ID");
            HashSet<Integer>  hs = sql.ReturnSetOfIDS(con,"Uzsakymas", "UzsakymoID");
            if(hs.contains(uzsakymoID)){
                sql.IstrintiUzsakymoPatiekalusSQL(con,uzsakymoID);
                sql.IstrintiUzsakymaSQL(con,uzsakymoID);
                con.commit();
                con.setAutoCommit(true);
                System.out.println("Uzsakymas sekmingai istrintas");
            }
            else{
                System.out.println("Uzsakymo ID neegzsituoja, bandykite is naujo:");
                con.rollback();
                con.setAutoCommit(true);
                atsauktiUzsakyma(con,sc,sql);
            }
        }
        catch(SQLException e) {
            con.rollback();
            con.setAutoCommit(true);
            System.out.println("Metode ivyko klaida" + e.getMessage());
        }
    }

}
