package com.vervyle.oop.containers;

import com.vervyle.oop.drawable.Element;
import com.vervyle.oop.drawable.GGroup;
import com.vervyle.oop.factories.ElementFactory;
import com.vervyle.oop.factories.ElementFactoryImpl;
import com.vervyle.oop.utils.Point2D;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ElementsContainer implements Observable {

    private final MyList<Element> allElements;
    private final MyList<Element> selectedElements;

    public ElementsContainer() {
        allElements = new MyLinkedList<>();
        selectedElements = new MyLinkedList<>();
        listeners = new LinkedList<>();
    }

    public void addElementAsLast(Element element) {
        Objects.requireNonNull(element);
        allElements.add(element);
    }

    public void removeElement(Element element) {
        Objects.requireNonNull(element);
        allElements.remove(element);
        selectedElements.remove(element);
    }

    public void removeAll() {
        Iterator<Element> iterator = allElements.iterator();
        while (iterator.hasNext()) {
            removeElement(iterator.next());
        }
    }

    public void selectElement(Element element) {
        Objects.requireNonNull(element);
        if (element.isSelected())
            return;
        element.select();
        selectedElements.add(element);
    }

    public void selectLastElement() {
        Element element = allElements.getLast();
        if (element == null) {
            return;
        }
        selectElement(element);
    }

    public void selectAll() {
        allElements.iterator().forEachRemaining(this::selectElement);
    }

    public void selectAll(Point2D point2D) {
        Iterator<Element> iterator = allElements.iterator();
        Element element;
        while (iterator.hasNext()) {
            element = iterator.next();
            if (element.intersects(point2D) && !element.isSelected())
                selectElement(element);
        }
    }

    public void selectLast(Point2D point2D) {
        Iterator<Element> iterator = allElements.descendingIterator();
        Element element;
        while (iterator.hasNext()) {
            element = iterator.next();
            if (element.intersects(point2D) && !element.isSelected()) {
                selectElement(element);
                return;
            }
        }
    }

    public void deselectElement(Element element) {
        Objects.requireNonNull(element);
        if (!element.isSelected())
            return;
        element.deselect();
        selectedElements.remove(element);
    }

    public void deselectAll() {
        Iterator<Element> iterator = selectedElements.iterator();
        while (iterator.hasNext()) {
            deselectElement(iterator.next());
        }
    }

    public boolean anyCollision(Point2D point2D) {
        Iterator<Element> iterator = allElements.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().intersects(point2D))
                return true;
        }
        return false;
    }

    public MyList<Element> getSelectedElements() {
        return selectedElements;
    }

    public MyList<Element> getAllElements() {
        return allElements;
    }

    private JSONArray saveAll() {
        JSONArray jsonAllElements = new JSONArray();
        JSONObject jsonElement;
        Element element;
        Iterator<Element> iterator = allElements.iterator();
        while (iterator.hasNext()) {
            element = iterator.next();
            jsonElement = element.save();
            jsonAllElements.put(jsonElement);
        }
        return jsonAllElements;
    }

    public JSONObject save() {
        JSONArray jsonAllElements = saveAll();
        return new JSONObject()
                .put("elements", jsonAllElements);
    }

    public void load(JSONObject jsonContainer) {
        removeAll();
        ElementFactory elementFactory = new ElementFactoryImpl();
        JSONArray jsonAllElements = jsonContainer.getJSONArray("elements");
        Iterator<Object> iterator = jsonAllElements.iterator();
        JSONObject jsonElement;
        Element element;
        while (iterator.hasNext()) {
            jsonElement = (JSONObject) iterator.next();
            element = elementFactory.createElement(jsonElement);
            addElementAsLast(element);
        }
        selectLastElement();
    }

    public void update(Pane pane) {
        pane.getChildren().clear();
        allElements.iterator().forEachRemaining(element -> element.show(pane));
    }

    private final List<InvalidationListener> listeners;

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    public void notifyListeners() {
        listeners.forEach(invalidationListener -> invalidationListener.invalidated(this));
    }

    public void updateSelection(List<Integer> selectedIndices) {
        if (selectedIndices.contains(0)) {
            selectAll();
            return;
        }
        int index = 1;
        Iterator<Element> iterator = allElements.iterator();
        Element element;
        while (iterator.hasNext()) {
            element = iterator.next();
            if (selectedIndices.contains(index)) {
                selectElement(element);
            } else {
                deselectElement(element);
            }
            if (element instanceof GGroup group) {
                index += group.getChildren().size();
            }
            index++;
        }
    }
}
