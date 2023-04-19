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
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import java.io.*;
import java.util.Iterator;

public class PaneController {

    private final Pane pane;
    private final ElementFactory elementFactory;
    private final ElementsContainer elementsContainer;

    public PaneController(Pane pane, TreeView<String> treeView) {
        this.pane = pane;
        elementFactory = new ElementFactoryImpl();
        elementsContainer = new ElementsContainer();
        TreeController treeController = new TreeController(treeView);
        elementsContainer.addListener(treeController);

        treeView.setOnMouseClicked(mouseEvent -> elementsContainer.updateSelection(treeView.getSelectionModel().getSelectedIndices().stream().toList()));
    }

    public void addElement(Point2D center, double radius, Color color, ElementType elementType) {
        Element element = elementFactory.createElement(center, radius, color, elementType);
        element.show(pane);
        elementsContainer.addElementAsLast(element);
        elementsContainer.deselectAll();
        elementsContainer.selectLastElement();
        elementsContainer.notifyListeners();
    }

    public void deleteElement(Element element) {
        element.hide(pane);
        elementsContainer.removeElement(element);
    }

    public void selectAll(Point2D point2D) {
        elementsContainer.selectAll(point2D);
        elementsContainer.notifyListeners();
    }

    public void selectAll() {
        elementsContainer.selectAll();
        elementsContainer.notifyListeners();
    }

    public void selectLast(Point2D point2D) {
        elementsContainer.selectLast(point2D);
        elementsContainer.notifyListeners();
    }

    public void deselectAll() {
        elementsContainer.deselectAll();
        elementsContainer.notifyListeners();
    }

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
        elementsContainer.deselectAll();
        elementsContainer.selectLastElement();
        elementsContainer.notifyListeners();
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
            deleteElement(element);
        }
        elementsContainer.selectLastElement();
        elementsContainer.notifyListeners();
    }

    public void deGroupSelected() {
        MyList<Element> selected = elementsContainer.getSelectedElements();
        Iterator<Element> iterator = selected.iterator();
        Element element;
        GGroup group;
        MyList<Element> children;
        Iterator<Element> childIterator;
        while (iterator.hasNext()) {
            element = iterator.next();
            if (element instanceof GGroup) {
                group = (GGroup) element;
                group.deselect();
                children = group.getChildren();
                childIterator = children.iterator();
                Element child;
                while (childIterator.hasNext()) {
                    child = childIterator.next();
                    elementsContainer.addElementAsLast(child);
                    elementsContainer.selectElement(child);
                }
                elementsContainer.removeElement(element);
            }
        }
        elementsContainer.notifyListeners();
    }

    public void saveAll(String path) {
        JSONObject jsonContainer = elementsContainer.save();
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(jsonContainer.toString());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Cannot save elements: " + e.getMessage());
        }
        System.out.println("Saved to " + path + ": " + jsonContainer.toString());
    }

    public void loadAll(String path) {
        String str;
        JSONObject jsonContainer;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            str = bufferedReader.readLine();
            jsonContainer = new JSONObject(str);
            elementsContainer.load(jsonContainer);
            elementsContainer.update(pane);
        } catch (Exception e) {
            System.out.println("Cannot load from file + " + path + ": " + e.getMessage());
        }
        elementsContainer.notifyListeners();
    }
}
