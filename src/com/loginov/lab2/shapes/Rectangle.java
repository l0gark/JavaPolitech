package com.loginov.lab2.shapes;


/**
 * Представление о прямоугольнике.
 * <p>
 * Прямоугольник — четырехугольник, у которого все углы
 * прямые (равны 90 градусам).
 *
 * @see <a href="https://ru.wikipedia.org/wiki/%D0%9F%D1%80%D1%8F%D0%BC%D0%BE%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA">Прямоугольник</a>
 */
public class Rectangle implements Polygon {

    private Point p1;
    private Point p2;

    private final float length;
    private final float height;

    private int angle;

    public Rectangle(float x1, float y1, float x2, float y2) {
        this(new MyPoint(x1, y1), new MyPoint(x2, y2));
    }

    public Rectangle(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;

        if (p1.equals(p2)) {
            throw new IllegalArgumentException();
        }
        length = Math.abs(p1.getX() - p2.getX());
        height = Math.abs(p1.getY() - p2.getY());
        angle = 0;
    }

    @Override
    public float getPerimeter() {
        return 2 * length + 2 * height;
    }

    @Override
    public Point getCentre() {
        return new MyPoint((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
    }

    @Override
    public float getArea() {
        return length * height;
    }

    @Override
    public int getRotation() {
        return angle;
    }

    @Override
    public void rotate(int angle) {
        final Point centre = getCentre();
        p1 = Point.rotate(p1, centre, angle);
        p2 = Point.rotate(p2, centre, angle);

        this.angle += angle;
        this.angle %= 360;
    }

    @Override
    public String toString() {
        return "area = " + getArea()
                + " --- Rectangle{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", length=" + length +
                ", height=" + height +
                ", angle=" + angle +
                '}';
    }
}
