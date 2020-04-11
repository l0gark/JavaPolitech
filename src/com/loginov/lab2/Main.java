package com.loginov.lab2;


import com.loginov.lab2.shapes.Circle;
import com.loginov.lab2.shapes.MyPoint;
import com.loginov.lab2.shapes.Point;
import com.loginov.lab2.shapes.Rectangle;
import com.loginov.lab2.shapes.Shape;
import com.loginov.lab2.shapes.Triangle;

import java.util.Random;

public class Main {
    private static Random random = new Random();

    public static void main(String[] args) {
        final Shape[] shapes = new Shape[10];
        for (int i = 0; i < shapes.length; i++) {
            switch (random.nextInt(3)) {
                case 0:
                    float radius = random.nextInt(9);
                    shapes[i] = new Circle(radius);
                    break;
                case 1:
                    shapes[i] = new Rectangle(randomPoint(), randomPoint());
                    break;
                case 2:
                    shapes[i] = new Triangle(randomPoint(), randomPoint(), randomPoint());
                    break;
                default:
                    throw new IllegalStateException("Something go wrong");
            }
        }

        System.out.println("Набор фигур");
        for (final Shape shape : shapes) {
            System.out.println(shape.toString());
        }

        System.out.println("\n------------------------------------------\n");

        final Shape maxShape = findShapeWithMaxArea(shapes);
        System.out.println("Максимальная фигура\n" + maxShape.toString());
    }

    public static Shape findShapeWithMaxArea(final Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("shapes must contains at least one object!");
        }

        int maxIndex = 0;
        for (int i = 0; i < shapes.length; i++) {
            if (shapes[i] == null) {
                throw new IllegalArgumentException("Shape can't be null");
            }

            if (shapes[i].compareTo(shapes[maxIndex]) > 0) {
                maxIndex = i;
            }
        }

        return shapes[maxIndex];
    }

    private static Point randomPoint() {
        return new MyPoint(random.nextInt(10) - 5, random.nextInt(10) - 5);
    }
}
