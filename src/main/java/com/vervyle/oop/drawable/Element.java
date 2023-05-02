package com.vervyle.oop.drawable;

import com.vervyle.oop.drawable.utils.*;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import java.lang.reflect.Method;

public abstract class Element implements Copyable, Savable, Loadable {

    protected boolean selected;
    public StickyObservable stickyObservable;
    public StickyObserver stickyObserver;

    public Element() {
        selected = false;
        stickyObservable = new StickyObservable();
        stickyObserver = new StickyObserver();
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

    public abstract Point2D getCenter();

    @Override
    public JSONObject save() {
        return new JSONObject()
                .put("type", this.getClass().getSimpleName());
    }
}