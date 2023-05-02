package com.vervyle.oop.factories;

import com.vervyle.oop.containers.MyList;
import com.vervyle.oop.drawable.Element;
import com.vervyle.oop.drawable.GGroup;
import com.vervyle.oop.drawable.leafs.CCircle;
import com.vervyle.oop.drawable.leafs.RegularPolygon;
import com.vervyle.oop.drawable.leafs.SStar;
import com.vervyle.oop.utils.ElementType;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import java.util.Objects;

public class ElementFactoryImpl implements ElementFactory {

    public ElementFactoryImpl() {
    }

    @Override
    public Element createElement(Element element) {
        Element elementNew = (Element) element.copy();
        Objects.requireNonNull(elementNew);
        return elementNew;
    }

    @Override
    public Element createElement(JSONObject jsonObject) {
        Element element = null;
        String type = jsonObject.getString("type");
        if (type.equals(CCircle.class.getSimpleName())) {
            element = new CCircle(jsonObject, this);
        }
        if (type.equals(RegularPolygon.class.getSimpleName())) {
            element = new RegularPolygon(jsonObject, this);
        }
        if (type.equals(SStar.class.getSimpleName())) {
            element = new SStar(jsonObject, this);
        }
        if (type.equals(GGroup.class.getSimpleName())) {
            element = new GGroup(jsonObject, this);
        }
        Objects.requireNonNull(element);
        return element;
    }

    @Override
    public Element createElement(Point2D center, double radius, Color color, ElementType elementType) {
        switch (elementType) {
            case CCircle -> {
                return new CCircle(radius, center, color);
            }
            case HHexagon -> {
                return new RegularPolygon(radius, center, color, 6);
            }
            case SSquare -> {
                return new RegularPolygon(radius, center, color, 4);
            }
            case TTriangle -> {
                return new RegularPolygon(radius, center, color, 3);
            }
            case SStar -> {
                return new SStar(radius, center, color, 6);
            }
            default -> throw new UnsupportedOperationException();
        }
    }

    @Override
    public Element createElement(MyList<Element> children) {
        return new GGroup(children);
    }
}
