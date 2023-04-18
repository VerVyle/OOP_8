package com.vervyle.oop.utils;

import javafx.scene.shape.Shape;

public class VerticesHelper {
    public double[] calcVerticesForRegularPolygon(Point2D center, double radius, int numOfVertices) {
        double[] vertices = new double[numOfVertices * 2];
        double angle_step = 360.0 / numOfVertices / 180 * Math.PI;
        double x, y;
        double angle_normal_vertex;
        for (int i = 0; i < numOfVertices; i++) {
            angle_normal_vertex = angle_step * i;
            x = center.x() + Math.sin(angle_normal_vertex) * radius;
            y = center.y() + Math.cos(angle_normal_vertex) * radius;
            vertices[2 * i] = x;
            vertices[2 * i + 1] = y;
        }
        return vertices;
    }

    // TODO: 17.04.2023 create code
    public Shape calcVerticesForRegularStar(Point2D center, double radius, int numOfVertices) {
        throw new UnsupportedOperationException();
    }

    private boolean vectorProduct(Vector2D first, Vector2D second) {
        double a1 = first.end().x() - first.start().x();
        double a2 = first.end().y() - first.start().y();
        double b1 = second.end().x() - second.start().x();
        double b2 = second.end().y() - second.start().y();

        double k = a1 * b2 - a2 * b1;

        return k > 0;
    }

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
