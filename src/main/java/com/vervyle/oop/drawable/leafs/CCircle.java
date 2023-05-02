package com.vervyle.oop.drawable.leafs;

import com.vervyle.oop.factories.ElementFactory;
import com.vervyle.oop.drawable.utils.Copyable;
import com.vervyle.oop.drawable.SShape;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.json.JSONObject;

public class CCircle extends SShape {

    public CCircle(double radius, Point2D center, Color color) {
        super(radius, center, color);
        createShape();
    }

    public CCircle(JSONObject jsonObject, ElementFactory elementFactory) {
        super(jsonObject, elementFactory);
        createShape();
        deselect();
    }

    @Override
    public final boolean intersects(Point2D point2D) {
        return Math.pow(center.x() - point2D.x(), 2) + Math.pow(center.y() - point2D.y(), 2) <= Math.pow(radius, 2);
    }

    @Override
    protected final void createShape() {
        shape = new Circle(center.x(), center.y(), radius);
        shape.setFill(color);
    }

    @Override
    public Copyable copy() {
        return new CCircle(radius, center, color);
    }
}
