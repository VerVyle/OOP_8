package com.vervyle.oop.drawable;

import com.vervyle.oop.utils.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

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
        updateShape(pane);
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
            return;
        }
        deselect();
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
}
