import java.sql.Connection;

public class Main {
    public static void main(String[] args)  {

        DBFunctions db = new DBFunctions();
        Program_loop pl = new Program_loop();
        final String dbname = System.getenv("DB_NAME");
        final String username = System.getenv("USER_NAME");
        final String password = System.getenv("DB_PASSWORD");
        Connection con = db.connect_to_db(dbname,username,password);
        pl.PrintProgramLoop(con);
    }
}