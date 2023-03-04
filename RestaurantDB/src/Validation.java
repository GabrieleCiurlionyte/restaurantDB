import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validation {

    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    public Double ValidateDouble(Scanner sc, String inputMsg) {
        try {
            System.out.println(inputMsg);
            Double d = sc.nextDouble();
            //Database precision is .2
            d = BigDecimal.valueOf(d)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            return d;
        }
        catch(InputMismatchException e) {
            System.out.println("Neteisinga ivestis. Iveskite skaiciu");
            if(StopInputValidation(sc, "Norite sustabdyti ivedima? Ivesti t/n")) {
                return null;
            }
            else {
                Double d = ValidateDouble(sc, inputMsg);
                return d;
            }
        }
    }
    public String ValidateString(Scanner sc, String inputMsg) {
        try {
            System.out.println(inputMsg);
            String s = sc.nextLine();
            if(CheckIfStringContainsNumbers(s)) {
                System.out.println("Netinkama ivestis. Zodi sudaro skaiciai.");
                if(StopInputValidation(sc, "Norite sustabdyti ivedima? Ivesti t/n")) {
                    return null;
                }
                else {
                    s = ValidateString(sc, inputMsg);
                }
            }
            return s;
        }
        catch(InputMismatchException e) {
            System.out.println("Ivestas netinkamas tipas. Iveskite zodi.");
            if(StopInputValidation(sc, "Norite sustabdyti ivedima? Ivesti t/n")) {
                return null;
            }
            else {
                ValidateString(sc, inputMsg);
            }
        }
        catch(Exception e) {
            System.out.println("Ivyko klaida");
            if(StopInputValidation(sc, "Norite sustabdyti ivedima? Ivesti t/n")) {
                return null;
            }
            else {
                ValidateString(sc, inputMsg);
            }
        }
        return null;
    }
    public int ValidateInteger(Scanner sc, String inputMsg) {
        try {
            System.out.println(inputMsg);
            int d = Integer.parseInt(bf.readLine());
            if(d <= 0) {
                return HandlingOfIntError(sc, "Neteisinga ivestis. Iveskite skaiciu didesni uz 0", inputMsg);
            }
            else return d;
        }
        catch(InputMismatchException e) {
            return HandlingOfIntError(sc, "Neteisinga ivestis. Iveskite skaiciu", inputMsg);
        } catch (IOException e) {
            System.out.print("Klaida nuskaitant");
            return -1;
        }
    }
    public int ValidateInteger(Scanner sc, String inputMsg, int high) {
        try {
            System.out.println(inputMsg);
            int d = sc.nextInt();
            if(d >= 0 && d <= high) {
                return d;
            }
            else return HandlingOfIntError(sc, String.format("Neteisinga ivestis. Iveskite skaiciu didesni uz 0, bet nedidesni uz %d", high), inputMsg);
        }
        catch(InputMismatchException e) {
            return HandlingOfIntError(sc, "Neteisinga ivestis. Iveskite skaiciu", inputMsg);
        }
    }
    private int HandlingOfIntError(Scanner sc, String msg, String inputMsg){
        System.out.println(msg);
        if(StopInputValidation(sc, "Norite sustabdyti ivedima? Ivesti t/n")) {
            return -1;
        }
        else {
            int d = ValidateInteger(sc, inputMsg);
            return d;
        }
    }
    public boolean StopInputValidation(Scanner sc, String msg) {
        System.out.println(msg);
        try {
            char answer =  sc.next(".").charAt(0);
            if(sc.nextLine().equals("")) {
                Character.toLowerCase(answer);
                switch (answer) {
                    case 't' :
                        return true;
                    case 'n':
                        return false;
                    default:
                        return false;
                }
            }
            else {
                System.out.println("Ivestas per ilgas zodis");
                StopInputValidation(sc, "Norite sustabdyti ivedima? Ivesti t/n");
            }
        }
        catch(InputMismatchException e) {
            System.out.println("Ivestas neteisingas tipas. Iveskite viena simboli: 't' arba 'n'");
            StopInputValidation(sc, "Norite sustabdyti ivedima? Ivesti t/n");
        }
        return false;
    }
    private boolean CheckIfStringContainsNumbers(String s) {
        for(char c : s.toCharArray()) {
            if(Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

}
