package pl.edu.mimuw;

public class Main {

    public static void main(String[] args) {
        CountedList<Integer> lista = new CountedList<>();

        lista.add(5);
        lista.add(3);
        
        lista.get(0);
        lista.get(0);
        lista.get(1);

        System.out.println(lista.getIndexAccessCount(0));
        System.out.println(lista.getIndexAccessCount(1));
        System.out.println(lista.getIndexAccessCount(2));
        System.out.println();
        System.out.println(lista.getElementAccessCount(5));
        System.out.println(lista.getElementAccessCount(3));
        System.out.println(lista.getElementAccessCount(0));
        System.out.println();
        System.out.println(lista.getMostOftenAccessedIndex());
        
    }
}
