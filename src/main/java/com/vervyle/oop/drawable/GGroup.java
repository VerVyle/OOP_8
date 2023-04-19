package com.vervyle.oop.drawable;

import com.vervyle.oop.containers.MyLinkedList;
import com.vervyle.oop.containers.MyList;
import com.vervyle.oop.factories.ElementFactory;
import com.vervyle.oop.factories.ElementFactoryImpl;
import com.vervyle.oop.utils.Copyable;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class GGroup extends Element {

    private MyList<Element> children;

    public MyList<Element> getChildren() {
        return children;
    }

    public GGroup() {
        children = new MyLinkedList<>();
    }

    public GGroup(JSONObject jsonObject) {
        this();
        load(jsonObject);
        deselect();
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
            if (current.intersects(point2D)) return true;
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
        super.select();
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            iterator.next().select();
        }
    }

    @Override
    public void deselect() {
        super.deselect();
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

    private MyList<Element> makeCopyOfChildren() {
        MyList<Element> copy = new MyLinkedList<>();
        Iterator<Element> iterator = children.iterator();
        Element children;
        while (iterator.hasNext()) {
            children = iterator.next();
            copy.add((Element) children.copy());
        }
        return copy;
    }

    @Override
    public boolean resize(Pane pane, double newRadius) {
        MyList<Element> copy = makeCopyOfChildren();
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().resize(pane, newRadius)) {
                restore(pane, copy);
                System.out.println("Cannot resize group: " + this);
                return false;
            }
        }
        return true;
    }

    private void restore(Pane pane, MyList<Element> copy) {
        children.iterator().forEachRemaining(element -> element.hide(pane));
        children = copy;
        children.iterator().forEachRemaining(element -> {
            element.select();
            element.show(pane);
        });
    }

    @Override
    public boolean move(Pane pane, double deltaX, double deltaY) {
        MyList<Element> copy = makeCopyOfChildren();
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().move(pane, deltaX, deltaY)) {
                restore(pane, copy);
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
            if (iterator.next().isOutOfPane(pane)) return true;
        }
        return false;
    }

    @Override
    public Copyable copy() {
        MyList<Element> newChildren = makeCopyOfChildren();
        return new GGroup(newChildren);
    }

    @Override
    public void load(JSONObject jsonObject) {
        JSONArray jsonChildren = jsonObject.getJSONArray("children");
        ElementFactory elementFactory = new ElementFactoryImpl();
        JSONObject jsonChild;
        Element element;
        Iterator<Object> iterator = jsonChildren.iterator();
        while (iterator.hasNext()) {
            jsonChild = ((JSONObject) iterator.next());
            element = elementFactory.createElement(jsonChild);
            children.add(element);
        }
    }

    @Override
    public JSONObject save() {
        JSONObject jsonThis = super.save();
        JSONArray jsonChildren = new JSONArray();
        JSONObject jsonChild;
        Iterator<Element> iterator = children.iterator();
        while (iterator.hasNext()) {
            jsonChild = iterator.next().save();
            jsonChildren.put(jsonChild);
        }
        jsonThis.put("children", jsonChildren);
        return jsonThis;
    }
}
