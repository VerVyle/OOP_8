package com.vervyle.oop.drawable;

import com.vervyle.oop.factories.ElementFactory;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class SShape extends Element {
    protected double radius;
    protected Point2D center;
    protected Color color;
    protected Shape shape;

    public SShape(double radius, Point2D center, Color color) {
        this.radius = radius;
        this.center = center;
        this.color = color;
    }

    public SShape(JSONObject jsonObject, ElementFactory elementFactory) {
        load(jsonObject, elementFactory);
    }

    @Override
    public Point2D getCenter() {
        return center;
    }

    @Override
    public final void show(Pane pane) {
        pane.getChildren().add(shape);
    }

    @Override
    public final void hide(Pane pane) {
        pane.getChildren().remove(shape);
    }

    @Override
    public final boolean resize(Pane pane, double newRadius) {
        double oldRadius = radius;
        radius = newRadius;
        if (isOutOfPane(pane)) {
            System.out.println("Cannot resize element: " + this);
            radius = oldRadius;
            return false;
        }
        updateShape(pane);
        return true;
    }

    @Override
    public final boolean move(Pane pane, double deltaX, double deltaY) {
        Point2D oldCenter = center;
        center = new Point2D(center.x() + deltaX, center.y() + deltaY);
        if (isOutOfPane(pane)) {
            System.out.println("Cannot move element :" + this);
            center = oldCenter;
            return false;
        }
        stickyObservable.notifyObservers(pane, deltaX, deltaY);
        updateShape(pane);
        stickyObservable.showLines(pane, getCenter());
        stickyObserver.changed(pane);
        return true;
    }

    @Override
    public final void setColor(Pane pane, Color color) {
        this.color = color;
        updateShape(pane);
    }

    protected abstract void createShape();

    @Override
    public final void updateShape(Pane pane) {
        hide(pane);
        createShape();
        shape.setFill(color);
        show(pane);
        if (isSelected()) {
            select();
        } else {
            deselect();
        }
        stickyObservable.showLines(pane, getCenter());
    }

    @Override
    public final boolean isOutOfPane(Pane pane) {
        if (center.x() - radius < 0 || center.x() + radius > pane.getWidth())
            return true;
        return (center.y() - radius < 0 || center.y() + radius > pane.getHeight());
    }

    @Override
    public final void select() {
        super.select();
        shape.setStyle("-fx-stroke: #FF0000; -fx-stroke-width: 3px");
    }

    @Override
    public final void deselect() {
        super.deselect();
        shape.setStyle("-fx-stroke: #000000; -fx-stroke-width: 3px");
    }

    @Override
    public JSONObject save() {
        JSONObject jsonThis = super.save();
        JSONArray jsonColor = new JSONArray()
                .put(color.getRed())
                .put(color.getGreen())
                .put(color.getBlue());
        JSONArray jsonCenter = new JSONArray()
                .put(center.x())
                .put(center.y());
        jsonThis.put("radius", radius)
                .put("center", jsonCenter)
                .put("color", jsonColor);
        return jsonThis;
    }

    @Override
    public void load(JSONObject jsonObject, ElementFactory elementFactory) {
        radius = jsonObject.getDouble("radius");
        double b0, b1, b2;
        b0 = jsonObject.getJSONArray("center").getDouble(0);
        b1 = jsonObject.getJSONArray("center").getDouble(1);
        center = new Point2D(b0, b1);
        b0 = jsonObject.getJSONArray("color").getDouble(0);
        b1 = jsonObject.getJSONArray("color").getDouble(1);
        b2 = jsonObject.getJSONArray("color").getDouble(2);
        color = new Color(b0, b1, b2, 1);
    }
}
