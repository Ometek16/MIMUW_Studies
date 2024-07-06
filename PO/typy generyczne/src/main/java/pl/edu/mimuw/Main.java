package pl.edu.mimuw;

public class Main {
    /*
    public static void main(String[] args) {
        var container = new Container<String>("foo");
        var container2 = new Container<String> ("bar");
        
        container.pullValueFrom(container2);
        container2.pushValueTo(container);
        
        var result = container.merge(container2, (first, second) -> (first +
        second).length());
        
        System.out.println(result);
    }
    */
    

    public static void main(String[] args) {
        var list = new NonEmptyList<>(5).prepend(4).prepend(3).prepend(2);

        for (var elem : list)
            System.out.print(elem + " ");
        System.out.println();

        list = list.reverse();
        for (var elem : list)
            System.out.print(elem + " ");
        System.out.println();
    }
}
