package com.vervyle.oop.drawable.utils;

import com.vervyle.oop.utils.Point2D;
import com.vervyle.oop.utils.Vector2D;

public class VerticesHelper {
    public double[] calcVerticesForRegularPolygon(Point2D center, double radius, int numOfVertices) {
        double[] vertices = new double[numOfVertices * 2];
        double angleStep = 360.0 / numOfVertices / 180 * Math.PI;
        double angleNormalVertex;
        Point2D point2D;
        for (int i = 0; i < numOfVertices; i++) {
            angleNormalVertex = angleStep * i;
            point2D = calcPoint(center, angleNormalVertex, radius);
            vertices[2 * i] = point2D.x();
            vertices[2 * i + 1] = point2D.y();
        }
        return vertices;
    }

    private Point2D calcPoint(Point2D center, double angle, double radius) {
        double x = center.x() + Math.sin(angle) * radius;
        double y = center.y() + Math.cos(angle) * radius;
        return new Point2D(x, y);
    }

    public double[] calcVerticesForRegularStar(Point2D center, double radius, double lowerRadius, int numOfSharpAngles) {
        double[] vertices = new double[numOfSharpAngles * 4];
        double angleStep = 360.0 / numOfSharpAngles / 180 / 2 * Math.PI;
        double angleNormalVertex;
        double currentRadius = lowerRadius;
        Point2D point2D;
        for (int i = 0; i < numOfSharpAngles * 2; i++) {
            angleNormalVertex = angleStep * i;
            point2D = calcPoint(center, angleNormalVertex, currentRadius);
            vertices[2 * i] = point2D.x();
            vertices[2 * i + 1] = point2D.y();
            if (currentRadius == lowerRadius) {
                currentRadius = radius;
            } else {
                currentRadius = lowerRadius;
            }
        }
        return vertices;
    }

    /**
     * считает коэффициент k векторного произведения
     * @return k > 0
     */
    private boolean vectorProduct(Vector2D first, Vector2D second) {
        double a1 = first.end().x() - first.start().x();
        double a2 = first.end().y() - first.start().y();
        double b1 = second.end().x() - second.start().x();
        double b2 = second.end().y() - second.start().y();

        double k = a1 * b2 - a2 * b1;

        return k > 0;
    }

    public boolean isInsideV2(double[] vertices, int numOfVertices, Point2D point2D) {
        Point2D first = new Point2D(vertices[2 * numOfVertices - 2], vertices[2 * numOfVertices - 1]);
        Point2D second;
        double res = 1;
        for (int i = 0; i < numOfVertices; i++) {
            second = new Point2D(vertices[2 * i], vertices[2 * i + 1]);
            res *= intersects(first, second, point2D);
            first = second;
        }
        return res < 1;
    }

    /**
     * @param first   первая точка отрезка
     * @param second  второая точка отрезка
     * @param point2D точка из которой будет запущен луч в направлении +x
     * @return 0 - точка лежит на отрезке; -1 - луч пересекает отрезок; 1 - луч не пересекает отрезок
     */
    private double intersects(Point2D first, Point2D second, Point2D point2D) {
        double ax = first.x() - point2D.x();
        double ay = first.y() - point2D.y();
        double bx = second.x() - point2D.x();
        double by = second.y() - point2D.y();
        double s = Math.signum(ax * by - ay * bx);
        if (s == 0 && (ay == 0 || by == 0) && ax * bx <= 0)
            return 0;
        if (ay < 0 ^ by < 0) {
            if (by < 0)
                return s;
            return -s;
        }
        return 1;
    }

    /**
     * считает векторное произведение для пар векторов(точка, вершина), если все положительны то точка лежит внутри выпуклого многоугольника
     */
    public boolean isInside(double[] vertices, int numOfVertices, Point2D point2D) {
        Point2D endFirst = new Point2D(vertices[2 * numOfVertices - 2], vertices[2 * numOfVertices - 1]);
        Point2D endSecond;
        Vector2D first = new Vector2D(point2D, endFirst);
        Vector2D second;
        for (int i = 0; i < numOfVertices; i++) {
            endSecond = new Point2D(vertices[2 * i], vertices[2 * i + 1]);
            second = new Vector2D(point2D, endSecond);
            if (vectorProduct(first, second))
                return false;
            first = second;
        }
        return true;
    }
}
