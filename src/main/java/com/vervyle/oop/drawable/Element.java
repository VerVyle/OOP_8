package com.vervyle.oop.drawable;

import com.vervyle.oop.utils.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Element {

    protected boolean selected;

    public Element() {
        selected = false;
    }

    public final boolean isSelected() {
        return selected;
    }

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public abstract boolean intersects(Point2D point2D);

    public abstract void show(Pane pane);

    public abstract void hide(Pane pane);

    public abstract boolean resize(Pane pane, double newRadius);

    public abstract boolean move(Pane pane, double deltaX, double deltaY);

    public abstract void setColor(Pane pane, Color color);

    public abstract void updateShape(Pane pane);

    public abstract boolean isOutOfPane(Pane pane);
}