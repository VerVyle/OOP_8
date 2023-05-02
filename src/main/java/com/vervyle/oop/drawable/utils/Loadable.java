package com.vervyle.oop.drawable.utils;

import com.vervyle.oop.factories.ElementFactory;
import org.json.JSONObject;

public interface Loadable {

    void load(JSONObject jsonObject, ElementFactory elementFactory);
}
