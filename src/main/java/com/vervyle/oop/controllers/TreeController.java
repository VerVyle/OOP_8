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

    private static int index = 0;

    public TreeController(TreeView<String> treeView) {
        this.treeView = treeView;
    }

    private void DFSProcessElement(TreeItem<String> parentNode, Element element) {
        TreeItem<String> node = new TreeItem<>(element.getClass().getSimpleName());
        node.setExpanded(true);
        parentNode.getChildren().add(node);
        if (!(element instanceof GGroup)) {
            return;
        }
        GGroup group = (GGroup) element;
        Element child;
        Iterator<Element> iterator = group.getChildren().iterator();
        while (iterator.hasNext()) {
            child = iterator.next();
            DFSProcessElement(node, child);
        }
    }

    private void DFSProcessSelection(Element element) {
        if (element.isSelected()) {
            treeView.getSelectionModel().select(index);
        }
        index++;
        if (!(element instanceof GGroup)) {
            return;
        }
        GGroup group = (GGroup) element;
        Element child;
        Iterator<Element> iterator = group.getChildren().iterator();
        while (iterator.hasNext()) {
            child = iterator.next();
            DFSProcessSelection(child);
        }
    }

    private void updateSelected(MyList<Element> allElements) {
        index = 1;
        treeView.getSelectionModel().clearSelection();
        Iterator<Element> iterator = allElements.iterator();
        while (iterator.hasNext()) {
            DFSProcessSelection(iterator.next());
        }
    }

    private void updateTree(MyList<Element> allElements) {
        TreeItem<String> root = new TreeItem<>("container");
        Iterator<Element> iterator = allElements.iterator();
        while (iterator.hasNext()) {
            DFSProcessElement(root, iterator.next());
        }
        treeView.setRoot(root);
        root.setExpanded(true);
    }

    @Override
    public void invalidated(Observable observable) {
        ElementsContainer elementsContainer = (ElementsContainer) observable;
        updateTree(elementsContainer.getAllElements());
        updateSelected(elementsContainer.getAllElements());
    }
}
