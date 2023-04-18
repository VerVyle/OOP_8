package com.vervyle.oop.factories;

import com.vervyle.oop.containers.MyList;
import com.vervyle.oop.drawable.Element;
import com.vervyle.oop.drawable.leafs.CCircle;
import com.vervyle.oop.drawable.leafs.RegularPolygon;
import com.vervyle.oop.utils.ElementType;
import com.vervyle.oop.utils.Point2D;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public class ElementFactoryImpl implements ElementFactory {

    @Override
    public Element createElement(Element element) {
        return null;
    }

    @Override
    public Element createElement(JSONObject jsonObject) {
        return null;
    }

    @Override
    public Element createElement(Point2D center, double radius, Color color, ElementType elementType) {
        if (elementType.name().equals(CCircle.class.getSimpleName())) {
            return new CCircle(radius, center, color);
        }
        if (elementType.name().equals("HHexagon")) {
            return new RegularPolygon(radius, center, color, 6);
        }
        if (elementType.name().equals("SSquare")) {
            return new RegularPolygon(radius, center, color, 4);
        }
        if (elementType.name().equals("TTriangle")) {
            return new RegularPolygon(radius, center, color, 3);
        }
        if (elementType.name().equals(("PPentagon"))) {
            return new RegularPolygon(radius, center, color, 5);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Element createElement(MyList<Element> children) {
        return null;
    }
}
