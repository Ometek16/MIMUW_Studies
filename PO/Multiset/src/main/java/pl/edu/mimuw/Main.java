package pl.edu.mimuw;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        MultiSet<Integer> multiSet = new MultiSet<>();

        multiSet.add(5);
        multiSet.add(5);
        multiSet.add(2);

        multiSet.setCount(3, 7);

        System.out.println(multiSet.getCount(1));
        System.out.println(multiSet.getCount(3));
        System.out.println(multiSet.getCount(5));

        Integer[] arr = multiSet.toArray(new Integer[0]); // TODO jak 0

        for (var elem : arr)
            System.out.print(elem + " ");
        System.out.println();

        for (var elem : multiSet)
            System.out.print(elem + " ");
        System.out.println();

    }
}
