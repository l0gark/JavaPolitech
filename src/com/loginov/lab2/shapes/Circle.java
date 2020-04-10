package com.loginov.lab2.shapes;

/**
 * Представление об окружности.
 * <p>
 * Окру́жность — замкнутая плоская кривая, которая состоит из
 * всех точек на плоскости, равноудалённых от заданной точки.
 *
 * @see <a href="https://ru.wikipedia.org/wiki/%D0%9E%D0%BA%D1%80%D1%83%D0%B6%D0%BD%D0%BE%D1%81%D1%82%D1%8C">Окружность</a>
 */
public class Circle implements Ellipse {

    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }

    @Override
    public float getLength() {
        return 2 * (float) Math.PI * radius;
    }

    @Override
    public float getArea() {
        return radius * radius;
    }

    @Override
    public String toString() {
        return "area = " + getArea()
                + " --- Circle{" +
                "radius=" + radius +
                '}';
    }
}
