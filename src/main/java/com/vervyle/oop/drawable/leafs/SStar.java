package com.vervyle.oop.drawable.leafs;

import com.vervyle.oop.utils.Copyable;
import com.vervyle.oop.drawable.PPolygon;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.json.JSONObject;

public class SStar extends PPolygon {

    private int numOfVertices;

    public SStar(double radius, Point2D center, Color color, int numOfVertices) {
        super(radius, center, color);
        this.numOfVertices = numOfVertices;
        createShape();
    }

    public SStar(JSONObject jsonObject) {
        super(jsonObject);
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

    @Override
    public Copyable copy() {
        return new SStar(radius, center, color, numOfVertices);
    }

    @Override
    public JSONObject save() {
        JSONObject jsonThis = super.save();
        jsonThis.put("numOfVertices", numOfVertices);
        return jsonThis;
    }

    @Override
    public void load(JSONObject jsonObject) {
        throw new UnsupportedOperationException();
    }
}
