package com.vervyle.oop.drawable.utils;

import com.vervyle.oop.drawable.Element;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.LinkedList;
import java.util.List;

public class StickyObservable {
    final List<Element> observers;
    final List<Shape> lines;

    public StickyObservable() {
        observers = new LinkedList<>();
        lines = new LinkedList<>();
    }

    public void notifyObservers(Pane pane, double deltaX, double deltaY) {
        observers.forEach(element -> element.move(pane, deltaX, deltaY));
    }

    public void showLines(Pane pane, Point2D begin) {
        hideLines(pane);
        observers.forEach(element -> lines.add(new Line(begin.x(), begin.y(), element.getCenter().x(), element.getCenter().y())));
        lines.forEach(shape -> pane.getChildren().add(shape));
    }

    public void hideLines(Pane pane) {
        lines.forEach(shape -> pane.getChildren().remove(shape));
        lines.clear();
    }

    public boolean isObserver(Element element) {
        return observers.contains(element);
    }

    public void addListener(Element element) {
        observers.add(element);
    }

    public void removeListener(Pane pane, Point2D begin, Element element) {
        observers.remove(element);
        showLines(pane, begin);
    }

    public void removeAll(Pane pane) {
        observers.clear();
        hideLines(pane);
    }
}
