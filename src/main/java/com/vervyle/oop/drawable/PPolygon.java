package com.vervyle.oop.drawable;

import com.vervyle.oop.factories.ElementFactory;
import com.vervyle.oop.utils.Point2D;
import com.vervyle.oop.drawable.utils.VerticesHelper;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public abstract class PPolygon extends SShape {

    protected double[] vertices;
    protected final VerticesHelper verticesHelper;

    public PPolygon(double radius, Point2D center, Color color) {
        super(radius, center, color);
        verticesHelper = new VerticesHelper();
    }

    public PPolygon(JSONObject jsonObject, ElementFactory elementFactory) {
        super(jsonObject, elementFactory);
        verticesHelper = new VerticesHelper();
        createShape();
        deselect();
    }
}
