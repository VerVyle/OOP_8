package com.vervyle.oop.drawable.utils;

import com.vervyle.oop.drawable.Element;
import javafx.scene.layout.Pane;

public class MoveCommand {
    Element element;

    public MoveCommand(Element element) {
        this.element = element;
    }

    public void append(Pane pane, double deltaX, double deltaY) {
        element.move(pane, deltaX, deltaY);
    }
}
