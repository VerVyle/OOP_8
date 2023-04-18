package com.vervyle.oop.controllers;

import com.vervyle.oop.containers.ElementsContainer;
import com.vervyle.oop.containers.MyList;
import com.vervyle.oop.drawable.Element;
import com.vervyle.oop.drawable.GGroup;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Iterator;

public class TreeController implements InvalidationListener {

    private final TreeView<String> treeView;

    private static int INDEX = 0;

    public TreeController(TreeView<String> treeView) {
        this.treeView = treeView;
    }

    private void DFSprocessElement(TreeItem<String> parentNode, Element element) {
        TreeItem<String> node;
        if (!(element instanceof GGroup)) {
            node = new TreeItem<>(element.getClass().getSimpleName());
            parentNode.getChildren().add(node);
            return;
        }
        GGroup group = (GGroup) element;
        Element child;
        node = new TreeItem<>(GGroup.class.getSimpleName());
        Iterator<Element> iterator = group.getChildren().iterator();
        while (iterator.hasNext()) {
            child = iterator.next();
            DFSprocessElement(node, child);
        }
    }

    private void updateTree(MyList<Element> allElements) {
        TreeItem<String> root = new TreeItem<>("container");
        Iterator<Element> iterator = allElements.iterator();
        while (iterator.hasNext()) {
            DFSprocessElement(root, iterator.next());
        }
        treeView.setRoot(root);
    }

    private void getIndex(Element element) {

    }

    private void updateSelected(MyList<Element> selectedElements) {

    }

    @Override
    public void invalidated(Observable observable) {
        ElementsContainer elementsContainer = (ElementsContainer) observable;
        updateTree(elementsContainer.getAllElements());
    }
}
