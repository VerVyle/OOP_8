package com.vervyle.oop.drawable.leafs;

import com.vervyle.oop.utils.Copyable;
import com.vervyle.oop.drawable.PPolygon;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.json.JSONObject;

public class SStar extends PPolygon {

    private int numOfSharpAngles;
    private double lowerRadius;

    public SStar(double radius, Point2D center, Color color, int numOfSharpAngles) {
        super(radius, center, color);
        this.numOfSharpAngles = numOfSharpAngles;
        lowerRadius = radius / 2;
        createShape();
    }

    public SStar(JSONObject jsonObject) {
        super(jsonObject);
        createShape();
        deselect();
    }

    @Override
    public boolean intersects(Point2D point2D) {
        return verticesHelper.isInsideV2(vertices, numOfSharpAngles * 2, point2D);
    }

    @Override
    protected void createShape() {
        vertices = verticesHelper.calcVerticesForRegularStar(center, radius, lowerRadius, numOfSharpAngles);
        shape = new Polygon(vertices);
        shape.setFill(color);
    }

    @Override
    public Copyable copy() {
        return new SStar(radius, center, color, numOfSharpAngles);
    }

    @Override
    public JSONObject save() {
        JSONObject jsonThis = super.save();
        jsonThis.put("numOfSharpAngles", numOfSharpAngles);
        jsonThis.put("lowerRadius", lowerRadius);
        return jsonThis;
    }

    @Override
    public void load(JSONObject jsonObject) {
        super.load(jsonObject);
        numOfSharpAngles = jsonObject.getInt("numOfSharpAngles");
        lowerRadius = jsonObject.getDouble("lowerRadius");
    }
}
