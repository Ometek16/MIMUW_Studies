package pl.edu.mimuw;

public class Main {
    final private static String sep = "====== ===== ===== ===== =====";

    public static void foo(String string) {
        try {
            printLength(string); 
        } 
        catch (Exception e) {
            System.out.println(sep);
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
            System.out.println(sep);
        }
    }

    public static void printLength(String string) throws Exception {
        try {
            System.out.println("Długość Napisu = " + string.length());
        }
        catch (NullPointerException e) {
            System.out.println(sep);
            e.printStackTrace();
            Exception exception = new Exception("Wiadomość błędu!", e);
            throw exception;
        }
    }

    public static void main(String[] args) {
        foo(null);
       
        try (var resourse = new CustomAutoCloseable()) {
            throw new Exception();
        } catch (Exception e) {
            // resourse.close();
        }
    }
}
