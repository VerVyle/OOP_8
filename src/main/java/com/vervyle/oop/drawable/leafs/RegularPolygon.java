package com.vervyle.oop.drawable.leafs;

import com.vervyle.oop.factories.ElementFactory;
import com.vervyle.oop.drawable.utils.Copyable;
import com.vervyle.oop.drawable.PPolygon;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.json.JSONObject;

public class RegularPolygon extends PPolygon {

    private int numOfVertices;

    public RegularPolygon(double radius, Point2D center, Color color, int numOfVertices) {
        super(radius, center, color);
        this.numOfVertices = numOfVertices;
        createShape();
    }

    public RegularPolygon(JSONObject jsonObject, ElementFactory elementFactory) {
        super(jsonObject, elementFactory);
    }

    @Override
    public boolean intersects(Point2D point2D) {
        return verticesHelper.isInside(vertices, numOfVertices, point2D);
    }

    @Override
    protected void createShape() {
        vertices = verticesHelper.calcVerticesForRegularPolygon(center, radius, numOfVertices);
        shape = new Polygon(vertices);
        shape.setFill(color);
    }

    @Override
    public Copyable copy() {
        return new RegularPolygon(radius, center, color, numOfVertices);
    }

    @Override
    public JSONObject save() {
        JSONObject jsonThis = super.save();
        jsonThis.put("numOfVertices", numOfVertices);
        return jsonThis;
    }

    @Override
    public void load(JSONObject jsonObject, ElementFactory elementFactory) {
        super.load(jsonObject, elementFactory);
        numOfVertices = jsonObject.getInt("numOfVertices");
    }
}
