package com.loginov.lab2.shapes;

import java.util.Objects;

public class MyPoint implements Point {

    private static final float EPSILON = 1e-5f;
    private float x;
    private float y;

    public MyPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o instanceof MyPoint) {
            return false;
        }
        final Point myPoint = (Point) o;
        return Math.abs(myPoint.getX() - x) < EPSILON &&
                Math.abs(myPoint.getY() - y) < EPSILON ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{x = " + x +
                ", y = " + y +
                "}";
    }
}
