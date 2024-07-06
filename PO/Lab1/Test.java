package Lab1;
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World!!!");
        Dog dog = new Dog();

        System.out.println(dog.getWeight());
        dog.setWeight(20);
        System.out.println(dog.getWeight());
    }
}