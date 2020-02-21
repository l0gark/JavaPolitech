package com.loginov.lab2.shapes;

/**
 * Представление о треугольнике.
 * <p>
 * Треуго́льник (в евклидовом пространстве) — геометрическая
 * фигура, образованная тремя отрезками, которые соединяют
 * три точки, не лежащие на одной прямой. Указанные три
 * точки называются вершинами треугольника, а отрезки —
 * сторонами треугольника. Часть плоскости, ограниченная
 * сторонами, называется внутренностью треугольника: нередко
 * треугольник рассматривается вместе со своей внутренностью
 * (например, для определения понятия площади).
 *
 * @see <a href="https://ru.wikipedia.org/wiki/%D0%A2%D1%80%D0%B5%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA">Треугольник</a>
 */
public class Triangle implements Polygon {
    private Point p1;
    private Point p2;
    private Point p3;
    private int angle;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        angle = 0;
    }

    @Override
    public float getPerimeter() {
        return Point.getDistance(p1, p2) + Point.getDistance(p2, p3) + Point.getDistance(p3, p1);
    }

    @Override
    public float getArea() {
        return Math.abs((p1.getX() - p3.getX()) * (p2.getY() - p3.getY())
                - (p2.getX() - p3.getX()) * (p1.getX() - p3.getY())) / 2;
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
        p3 = Point.rotate(p3, centre, angle);
        this.angle += angle;
        this.angle %= 360;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", angle=" + angle +
                '}';
    }

    @Override
    public Point getCentre() {
        float x = (p1.getX() + p2.getX() + p3.getX()) / 3;
        float y = (p1.getY() + p2.getY() + p3.getY()) / 3;
        return new MyPoint(x, y);
    }
}
