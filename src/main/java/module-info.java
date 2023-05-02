module com.vervyle.oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires kotlin.stdlib;


    opens com.vervyle.oop to javafx.fxml;
    opens com.vervyle.oop.controllers to javafx.fxml;
    exports com.vervyle.oop;
    exports com.vervyle.oop.containers;
    exports com.vervyle.oop.controllers;
    exports com.vervyle.oop.drawable;
    exports com.vervyle.oop.drawable.leafs;
    exports com.vervyle.oop.factories;
    exports com.vervyle.oop.utils;
    exports com.vervyle.oop.drawable.utils;
}