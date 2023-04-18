package com.vervyle.oop.containers;

import com.vervyle.oop.drawable.Element;
import com.vervyle.oop.utils.Point2D;

import java.util.Iterator;
import java.util.Objects;

public class ElementsContainer {

    private final MyList<Element> allElements;
    private final MyList<Element> selectedElements;

    public ElementsContainer() {
        allElements = new MyLinkedList<>();
        selectedElements = new MyLinkedList<>();
    }

    public void addElementAsLast(Element element) {
        Objects.requireNonNull(element);
        allElements.add(element);
        deselectAll();
        selectLastElement();
    }

    public void removeElement(Element element) {
        Objects.requireNonNull(element);
        allElements.remove(element);
        selectedElements.remove(element);
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
}
