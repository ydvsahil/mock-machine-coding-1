public class MainClass {
    public static void main(String[] arg) {
        System.out.println("polymorphism application started");

        Car car = new Audi();
        System.out.println(car.getSpeed());

        System.out.println("polymorphism application ended");
    }
}
