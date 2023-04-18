package com.vervyle.oop.drawable;

import com.vervyle.oop.containers.MyLinkedList;
import com.vervyle.oop.containers.MyList;
import com.vervyle.oop.factories.ElementFactory;
import com.vervyle.oop.factories.ElementFactoryImpl;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class GGroup extends Element {

    private MyList<Element> children;

    public GGroup() {
        children = new MyLinkedList<>();
    }

    public GGroup(MyList<Element> children) {
        this.children = children;
    }

    @Override
    public final boolean intersects(Point2D point2D) {
        Iterator<Element> iterator = children.iterator();
        Element current;
        while (iterator.hasNext()) {
            current = iterator.next();
            if (current.intersects(point2D))
                return true;
        }
        return false;
    }

    @Override
    public final void show(Pane pane) {
        Iterator<Element> iterator = children.iterator();
        Element current;
        while (iterator.hasNext()) {
            current = iterator.next();
            current.show(pane);
        }
    }

    @Override
    public void select() {
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            iterator.next().select();
        }
    }

    @Override
    public void deselect() {
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            iterator.next().deselect();
        }
    }

    @Override
    public final void hide(Pane pane) {
        Iterator<Element> iterator = children.iterator();
        Element current;
        while (iterator.hasNext()) {
            current = iterator.next();
            current.hide(pane);
        }
    }

    private MyList<Element> makeCopy() {
        ElementFactory elementFactory = new ElementFactoryImpl();
        MyList<Element> copy = new MyLinkedList<>();
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            copy.add(elementFactory.createElement(iterator.next()));
        }
        return copy;
    }

    @Override
    public boolean resize(Pane pane, double newRadius) {
        MyList<Element> copy = makeCopy();
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().resize(pane, newRadius)) {
                restore(copy);
                System.out.println("Cannot resize group: " + this);
                return false;
            }
        }
        return true;
    }

    private void restore(MyList<Element> copy) {
        children = copy;
    }

    @Override
    public boolean move(Pane pane, double deltaX, double deltaY) {
        MyList<Element> copy = makeCopy();
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().move(pane, deltaX, deltaY)) {
                restore(copy);
                System.out.println("Cannot move group: " + this);
                return false;
            }
        }
        return true;
    }

    @Override
    public void setColor(Pane pane, Color color) {
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            iterator.next().setColor(pane, color);
        }
    }

    @Override
    public void updateShape(Pane pane) {
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            iterator.next().updateShape(pane);
        }
    }

    @Override
    public boolean isOutOfPane(Pane pane) {
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isOutOfPane(pane))
                return true;
        }
        return false;
    }
}