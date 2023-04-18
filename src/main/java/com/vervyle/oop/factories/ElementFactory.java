package com.vervyle.oop.factories;

import com.vervyle.oop.containers.MyList;
import com.vervyle.oop.drawable.Element;
import com.vervyle.oop.utils.ElementType;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public interface ElementFactory {

    Element createElement(Element element);

    Element createElement(JSONObject jsonObject);

    Element createElement(Point2D center, double radius, Color color, ElementType elementType);

    Element createElement(MyList<Element> children);
}
