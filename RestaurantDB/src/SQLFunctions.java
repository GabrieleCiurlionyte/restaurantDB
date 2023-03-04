import DBObjectModels.Patiekalas;
import DBObjectModels.Uzsakymas;

import javax.xml.transform.Result;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;


public class SQLFunctions {

    public int GautiPaskutinesEilutesID(Connection con, String table_name, String columnName) {
        try (Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            String string = String.format("SELECT * FROM %s;", table_name);
            ResultSet rs = stmt.executeQuery(string);
            if(rs.last()) {
                return rs.getInt(columnName);
            }
        }
        catch (SQLException e) {
            System.out.println("Exception "+ e.getErrorCode());
        }
        return -1;
    }

    public HashMap<Integer, Integer> GrazintiPadavejuAktyviuUzsakymuKieki(Connection con){
        try (Statement stmt = con.createStatement()) {
            String string = String.format("WITH Visi as (SELECT padavejoID, COUNT(padavejoID) as Kiekis FROM Uzsakymas WHERE Busena = 'Pradetas' GROUP BY padavejoID UNION SELECT padavejoID, 0 as Count FROM padavejas) SELECT padavejoID, SUM(Kiekis) as Kiekis FROM Visi GROUP BY padavejoID;");
            ResultSet rs = stmt.executeQuery(string);
            HashMap<Integer, Integer> uzsakymuCount = new HashMap<Integer,Integer>();
            while(rs.next()){
                Integer padavejoID = Integer.valueOf(rs.getInt("PadavejoID"));
                Integer count = Integer.valueOf(rs.getInt("Kiekis"));
                uzsakymuCount.put(padavejoID,count);
            }
            return uzsakymuCount;
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened" + e.getMessage());
        }
        return null;
    }

    public HashSet<Integer> ReturnSetOfIDS(Connection con, String table_name, String id_column_name){
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT %s FROM %s;", id_column_name, table_name);
            ResultSet rs = stmt.executeQuery(string);
            HashSet<Integer> idSet = new HashSet<>();
            while(rs.next()){
                int column_id = rs.getInt(id_column_name);
                idSet.add(column_id);
            }
            return idSet;
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened" + e.getMessage());
        }
        return null;
    }

    public HashSet<Integer> ReturnSetOfIDS(Connection con, String table_name, String id_column_name, String add_param){
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT %s FROM %s WHERE %s;", id_column_name, table_name, add_param);
            ResultSet rs = stmt.executeQuery(string);
            HashSet<Integer> idSet = new HashSet<>();
            while(rs.next()){
                int column_id = rs.getInt(id_column_name);
                idSet.add(column_id);
            }
            return idSet;
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened" + e.getMessage());
        }
        return null;
    }

    //CREATE:
    public void UzregistruotiNaujoPadavejoInfoSQL(Connection con, String vardas, String pavarde, double atlyginimas) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("insert into Padavejas (Vardas, Pavarde, Atlyginimas) VALUES" +
                    "('%s','%s',%f);", vardas, pavarde, atlyginimas);
            stmt.executeUpdate(string);
        } catch (SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened: ");
        }
    }

    public void UzregistruotiNaujoPadavejoNrSQL(Connection con, int padavejoID, int telefonoNr) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("insert into telefono_numeris (PadavejoID, TelefonoNr) VALUES" +
                    "(%d,%d);", padavejoID, telefonoNr);
            stmt.executeUpdate(string);
        } catch (SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened: ");
        }
    }

    public void UzregistruotiUzsakymaSQL(Connection con, int padavejoID) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("insert into Uzsakymas (PadavejoID) VALUES" +
                    "(%d);", padavejoID);
            stmt.executeUpdate(string);
        } catch (SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened: " + e.getMessage());
        }
    }

    public void IdetiPatiekalaIUzsakyma(Connection con, int uzsakymoID, int patiekaloID, int meniuID, int kiekis){
        try (Statement stmt = con.createStatement()) {
            String string = String.format("insert into Sudaro (UzsakymoID, PatiekaloID, MeniuID, Kiekis) VALUES" +
                    "(%d, %d, %d, %d);", uzsakymoID, patiekaloID, meniuID, kiekis);
            stmt.executeUpdate(string);
        } catch (SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened: " + e.getMessage());
        }
    }

    //UPDATE:
    public void AtnaujintiPatiekaloKiekiSQL(Connection con, int patiekaloID, int naujasKiekis) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("UPDATE Patiekalas SET TurimasKiekis = %d WHERE PatiekaloID = %d", naujasKiekis, patiekaloID);
            stmt.executeUpdate(string);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
    }

    public void AtnaujintiPatiekaloKainaSQL(Connection con, int patiekaloID, double kaina ) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("UPDATE Patiekalas SET Kaina = %f WHERE PatiekaloID = %d", kaina, patiekaloID);
            stmt.executeUpdate(string);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
    }

    public void AtnaujintiPadavejoAtlyginimaSQL(Connection con, int padavejoID, double atlyginimas) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("UPDATE Padavejas SET Atlyginimas = %f WHERE PadavejoID = %d", atlyginimas, padavejoID);
            stmt.executeUpdate(string);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
    }

    public void AtnaujintiPadavejoTelefonoNumeriSQL(Connection con, int padavejoID, int num) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("UPDATE telefono_numeris SET TelefonoNr = %d WHERE PadavejoID = %d", num, padavejoID);
            stmt.executeUpdate(string);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
    }

    public void BaigtiUzsakymaSQL(Connection con, int uzsakymoID) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("UPDATE Uzsakymas SET Busena = 'Baigtas' WHERE UzsakymoID = %d", uzsakymoID);
            stmt.executeUpdate(string);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
    }

    //DELETE
     public void IstrintiUzsakymoPatiekalusSQL(Connection con, int uzsakymoID) {
         try (Statement stmt = con.createStatement()) {
             String string = String.format("DELETE FROM Sudaro WHERE UzsakymoID = %d", uzsakymoID);
             stmt.executeUpdate(string);
         }
         catch(SQLException e) {
             System.out.println("Exception "+ e.getErrorCode() + " has happened" + e.getMessage());
         }
     }

     public void IstrintiUzsakymaSQL(Connection con, int uzsakymoID){
         try (Statement stmt = con.createStatement()) {
             String string = String.format("DELETE FROM Uzsakymas WHERE UzsakymoID = %d", uzsakymoID);
             stmt.executeUpdate(string);
         }
         catch(SQLException e) {
             System.out.println("Exception "+ e.getErrorCode() + " has happened" + e.getMessage());
         }
     }

    public void IstrintiPatiekala(Connection con, int patiekaloID, int meniuID){
        try (Statement stmt = con.createStatement()) {
            String string = String.format("DELETE FROM Patiekalas WHERE PatiekaloID = %d AND " +
                    "MeniuID = %d;", patiekaloID, meniuID);
            stmt.executeUpdate(string);
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened" + e.getMessage());
        }
    }

    //READ

    public double GrazintiBendraUzsakymoSuma(Connection con, int uzsakymoID) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT U.uzsakymoID, SUM(kiekis*kaina) FROM Uzsakymas as U, Sudaro as S, Patiekalas as P WHERE U.UzsakymoID = S.UzsakymoID AND S.PatiekaloID = P.PatiekaloID AND U.UzsakymoID = %d GROUP BY U.uzsakymoID;", uzsakymoID);
            ResultSet rs = stmt.executeQuery(string);
            while(rs.next()){
                double sum = rs.getDouble("sum");
                return sum;
            }
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
        return -1;
    }

    public ArrayList<Patiekalas> RastiPatiekalaTarpKainu(Connection con, double first, double second) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT patiekaloID,pavadinimas, meniuID, kaina FROM Patiekalas " +
                    "WHERE Kaina BETWEEN %f AND %f;", first, second);
            ResultSet rs = stmt.executeQuery(string);
            ArrayList<Patiekalas> arrl = new ArrayList<Patiekalas>();
            while(rs.next()) {
                int patiekaloID = rs.getInt("PatiekaloID");
                int meniuID = rs.getInt("MeniuID");
                String pavadinimas = rs.getString("Pavadinimas");
                Double kaina = rs.getDouble("Kaina");
                Patiekalas pt = new Patiekalas(patiekaloID,meniuID,pavadinimas,kaina);
                arrl.add(pt);
            }
            return arrl;
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened" + e.getMessage());
        }
        return null;
    }

    public ArrayList<Uzsakymas> GrazintiIvykdytusUzsakymus(Connection con) {
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT * FROM uzsakymas WHERE Busena='Baigtas';");
            ResultSet rs = stmt.executeQuery(string);
            ArrayList<Uzsakymas> baigtiUzsakymai = new ArrayList<Uzsakymas>();
            while(rs.next()){
                int uzsakymoID = rs.getInt("UzsakymoID");
                int padavejoID = rs.getInt("PadavejoID");
                Timestamp data = rs.getTimestamp("Data");
                Uzsakymas uz = new Uzsakymas(uzsakymoID, padavejoID, data);
                baigtiUzsakymai.add(uz);
            }
            return baigtiUzsakymai;
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened" + e.getMessage());
        }
        return null;
    }

    public int GautiProduktoTurimaKieki(Connection con, int patiekaloID){
        try (Statement stmt = con.createStatement()) {
            String string = String.format("SELECT TurimasKiekis FROM Patiekalas WHERE PatiekaloID = %d;", patiekaloID);
             ResultSet rs = stmt.executeQuery(string);
             while(rs.next()){
                 int stock = rs.getInt("TurimasKiekis");
                 return stock;
             }
        }
        catch(SQLException e) {
            System.out.println("Exception "+ e.getErrorCode() + " has happened");
        }
        return -1;
    }








}
