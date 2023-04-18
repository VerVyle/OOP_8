package com.vervyle.oop.drawable;

import com.vervyle.oop.utils.Point2D;
import com.vervyle.oop.utils.VerticesHelper;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public abstract class PPolygon extends SShape {

    protected double[] vertices;
    protected VerticesHelper verticesHelper;

    public PPolygon(double radius, Point2D center, Color color) {
        super(radius, center, color);
        verticesHelper = new VerticesHelper();
    }

    public PPolygon(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public JSONObject save() {
        JSONObject jsonThis = super.save();
        jsonThis.put("vertices", vertices);
        return jsonThis;
    }
}
