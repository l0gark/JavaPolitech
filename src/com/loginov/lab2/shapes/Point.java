package com.loginov.lab2.shapes;

/**
 * Абстрактное представление о точке.
 * <p>
 * То́чка — абстрактный объект в пространстве, не имеющий
 * никаких измеримых характеристик (нульмерный объект).
 * Точка является одним из фундаментальных понятий в
 * математике.
 *
 * @see <a href="https://ru.wikipedia.org/wiki/%D0%A2%D0%BE%D1%87%D0%BA%D0%B0_(%D0%B3%D0%B5%D0%BE%D0%BC%D0%B5%D1%82%D1%80%D0%B8%D1%8F)">Точка</a>
 */
public interface Point extends Shape {

    /**
     * Возвращает абсциссу точки.
     * <p>
     * Абсциссой точки A называется координата этой точки на
     * оси X в прямоугольной системе координат.
     *
     * @return x-координату точки
     * @see <a href="https://ru.wikipedia.org/wiki/%D0%90%D0%B1%D1%81%D1%86%D0%B8%D1%81%D1%81%D0%B0">Абсцисса</a>
     */
    float getX();

    /**
     * Возвращает ординату точки.
     * <p>
     * Ординатой точки A называется координата этой точки на
     * оси Y в прямоугольной системе координат.
     *
     * @return н-координату точки
     * @see <a href="https://ru.wikipedia.org/wiki/%D0%9E%D1%80%D0%B4%D0%B8%D0%BD%D0%B0%D1%82%D0%B0">Ордината</a>
     */
    float getY();

    /**
     * Метод для перемещения точки.
     *
     * @param dx смещение по X
     * @param dy смещение по Y
     */
    void move(float dx, float dy);

    @Override
    default float getArea(){
        return 0;
    }

    /**
     * Метод для подсчёта расстояния между точками.
     *
     * @param p1 первая точка
     * @param p2 вторая точка
     * @return расстояние между точками
     */
    static float getDistance(Point p1, Point p2) {
        return (float) Math.sqrt(Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()));
    }

    /**
     * Вращает точку вокруг центра.
     *
     * @param point  точка которая вращается
     * @param centre точка вокруг которой вращается
     * @param angle  угол поворота в градусах
     * @return новое положение точки
     */
    static Point rotate(final Point point, final Point centre, int angle) {
       double angleRad =  angle / (180f / Math.PI);

        double x = centre.getX()
                + (point.getX() - centre.getX()) * Math.cos(angleRad)
                - (point.getY() - centre.getY()) * Math.sin(angleRad);

        double y = centre.getY()
                + (point.getY() - centre.getY()) * Math.cos(angleRad)
                + (point.getX() - centre.getX()) * Math.sin(angleRad);

        return new MyPoint((float) x, (float) y);
    }
}
