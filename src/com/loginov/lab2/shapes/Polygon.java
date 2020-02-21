package com.loginov.lab2.shapes;

/**
 * Абстрактное представление о многоугольнике.
 * <p>
 * Многоуго́льник — это геометрическая фигура, обычно
 * определяемая как часть плоскости, ограниченная замкнутой
 * ломаной.
 *
 * @see <a href="https://ru.wikipedia.org/wiki/%D0%9C%D0%BD%D0%BE%D0%B3%D0%BE%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA">Многоугольник</a>
 */
public interface Polygon extends Shape {

    /**
     * Возвращает периметр многоугольника.
     * <p>
     * Периметр многоугольника равен сумме длин его сторон.
     *
     * @return периметр многоугольника.
     *
     * @see <a href="https://ru.wikipedia.org/wiki/%D0%9F%D0%B5%D1%80%D0%B8%D0%BC%D0%B5%D1%82%D1%80">Периметр</a>
     */
    float getPerimeter();

    /**
     * Возвращает центр многоугольника
     */
    Point getCentre();
}
