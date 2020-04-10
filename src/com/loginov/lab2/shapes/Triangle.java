package com.loginov.lab2.shapes;

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
        return "area = " + getArea()
                + " --- Triangle{" +
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
