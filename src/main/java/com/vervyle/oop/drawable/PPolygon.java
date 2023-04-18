package com.vervyle.oop.drawable;

import com.vervyle.oop.utils.Point2D;
import com.vervyle.oop.utils.VerticesHelper;
import javafx.scene.paint.Color;

public abstract class PPolygon extends SShape {

    protected double[] vertices;
    protected VerticesHelper verticesHelper;

    public PPolygon(double radius, Point2D center, Color color) {
        super(radius, center, color);
        verticesHelper = new VerticesHelper();
    }
}
