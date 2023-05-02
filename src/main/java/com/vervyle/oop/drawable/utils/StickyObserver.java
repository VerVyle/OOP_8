package com.vervyle.oop.drawable.utils;

import com.vervyle.oop.drawable.Element;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;

public class StickyObserver {
    private List<Element> parents;

    public StickyObserver() {
        parents = new LinkedList<>();
    }

    public void addParent(Element element) {
        parents.add(element);
    }

    public void removeParent(Element element) {
        parents.remove(element);
    }

    public void removeAll(Pane pane, Element element) {
        parents.forEach(element1 -> element1.stickyObservable.removeListener(pane, element1.getCenter(), element));
        changed(pane);
    }

    public void changed(Pane pane) {
        parents.forEach(element -> element.stickyObservable.showLines(pane, element.getCenter()));
    }
}
