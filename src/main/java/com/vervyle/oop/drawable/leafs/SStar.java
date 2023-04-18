package com.vervyle.oop.drawable.leafs;

import com.vervyle.oop.drawable.PPolygon;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class SStar extends PPolygon {

    private final int numOfVertices;

    public SStar(double radius, Point2D center, Color color, int numOfVertices) {
        super(radius, center, color);
        this.numOfVertices = numOfVertices;
        createShape();
    }

    @Override
    public boolean intersects(Point2D point2D) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void createShape() {
        shape = verticesHelper.calcVerticesForRegularStar(center, radius, numOfVertices);
        shape = new Polygon(vertices);
        shape.setFill(color);
    }
}
