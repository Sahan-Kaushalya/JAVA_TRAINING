// Abstract class with one abstract method and one concrete method.
abstract class Shape {
    String color;

    Shape(String color) {
        this.color = color;
    }

    abstract double calculateArea(); // must be implemented by subclass

    void displayColor() {
        System.out.println("Color: " + color);
    }
}

// Abstract class with multiple abstract methods.
abstract class Vehicle {
    abstract String getFuelType();
    abstract int getTopSpeed();

    void showDetails() {
        System.out.println("Fuel Type : " + getFuelType());
        System.out.println("Top Speed : " + getTopSpeed() + " km/h");
    }
}

public class AnonymousClassWithAbstractClassDemo {

    public static void main(String[] args) {

        System.out.println("=== Anonymous Class Extending Abstract Class ===\n");

        // --- Example 1: anonymous class implementing one abstract method ---
        // We create an unnamed class on the spot that extends Shape.
        Shape circle = new Shape("Red") {
            @Override
            double calculateArea() {
                double radius = 7.0;
                return Math.PI * radius * radius;
            }
        };

        circle.displayColor();
        System.out.printf("Circle Area = %.2f%n", circle.calculateArea());

        System.out.println();

        // --- Example 2: another anonymous class for a different shape ---
        Shape rectangle = new Shape("Blue") {
            @Override
            double calculateArea() {
                double width = 5.0;
                double height = 10.0;
                return width * height;
            }
        };

        rectangle.displayColor();
        System.out.printf("Rectangle Area = %.2f%n", rectangle.calculateArea());

        System.out.println();

        // --- Example 3: anonymous class implementing multiple abstract methods ---
        // The anonymous class must implement ALL abstract methods of Vehicle.
        Vehicle car = new Vehicle() {
            @Override
            String getFuelType() {
                return "Petrol";
            }

            @Override
            int getTopSpeed() {
                return 220;
            }
        };

        System.out.println("--- Car Details ---");
        car.showDetails();

        System.out.println();

        Vehicle electricBike = new Vehicle() {
            @Override
            String getFuelType() {
                return "Electric";
            }

            @Override
            int getTopSpeed() {
                return 150;
            }
        };

        System.out.println("--- Electric Bike Details ---");
        electricBike.showDetails();

        System.out.println();

        // --- Example 4: anonymous class passed directly as a method argument ---
        System.out.println("--- Inline anonymous class as method argument ---");
        printShape(new Shape("Green") {
            @Override
            double calculateArea() {
                return 0.5 * 8.0 * 6.0; // triangle: 0.5 * base * height
            }
        });
    }

    // Helper method that accepts any Shape
    static void printShape(Shape s) {
        s.displayColor();
        System.out.printf("Area = %.2f%n", s.calculateArea());
    }
}

