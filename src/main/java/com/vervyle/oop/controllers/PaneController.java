package com.vervyle.oop.controllers;

import com.vervyle.oop.containers.ElementsContainer;
import com.vervyle.oop.containers.MyLinkedList;
import com.vervyle.oop.containers.MyList;
import com.vervyle.oop.drawable.Element;
import com.vervyle.oop.drawable.GGroup;
import com.vervyle.oop.factories.ElementFactory;
import com.vervyle.oop.factories.ElementFactoryImpl;
import com.vervyle.oop.utils.ElementType;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class PaneController {

    private Pane pane;
    private ElementFactory elementFactory;
    private ElementsContainer elementsContainer;

    public PaneController(Pane pane) {
        elementFactory = new ElementFactoryImpl();
        elementsContainer = new ElementsContainer();
        this.pane = pane;
    }

    public void addElement(Point2D center, double radius, Color color, ElementType elementType) {
        Element element = elementFactory.createElement(center, radius, color, elementType);
        element.show(pane);
        elementsContainer.addElementAsLast(element);
    }

    public void deleteElement(Element element) {
        element.hide(pane);
        elementsContainer.removeElement(element);
    }

    public void selectAll(Point2D point2D) {
        elementsContainer.selectAll(point2D);
    }

    public void selectAll() {
        elementsContainer.selectAll();
    }

    public void selectLast(Point2D point2D) {
        elementsContainer.selectLast(point2D);
    }

    public void deselectAll() {
        elementsContainer.deselectAll();
    }

    // TODO: 17.04.2023 aesiufas
    public void groupSelected() {
        MyList<Element> selected = elementsContainer.getSelectedElements();
        Iterator<Element> iterator = selected.iterator();
        MyList<Element> children = new MyLinkedList<>();
        iterator.forEachRemaining(children::add);
        GGroup group = new GGroup(children);
        iterator = selected.iterator();
        Element element;
        while (iterator.hasNext()) {
            element = iterator.next();
            elementsContainer.removeElement(element);
        }
        elementsContainer.addElementAsLast(group);
    }

    public void moveSelected(double deltaX, double deltaY) {
        MyList<Element> selected = elementsContainer.getSelectedElements();
        selected.iterator().forEachRemaining(element -> element.move(pane, deltaX, deltaY));
    }

    public void resizeSelected(double newRadius) {
        MyList<Element> selected = elementsContainer.getSelectedElements();
        selected.iterator().forEachRemaining(element -> element.resize(pane, newRadius));
    }

    public boolean anyCollision(Point2D point2D) {
        return elementsContainer.anyCollision(point2D);
    }

    public void deleteSelected() {
        MyList<Element> selected = elementsContainer.getSelectedElements();
        Iterator<Element> iterator = selected.iterator();
        Element element;
        while (iterator.hasNext()) {
            element = iterator.next();
            element.hide(pane);
            elementsContainer.removeElement(element);
        }
        elementsContainer.selectLastElement();
    }

    public void deGroupSelected() {
    }
}
