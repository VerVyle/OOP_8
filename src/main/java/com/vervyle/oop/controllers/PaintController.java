package com.vervyle.oop.controllers;

import com.vervyle.oop.utils.ElementType;
import com.vervyle.oop.utils.Point2D;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PaintController implements Initializable {

    @FXML
    public MenuItem menuSelectAll;
    @FXML
    public MenuItem menuDeselectAll;
    @FXML
    public MenuItem menuGroupSelected;
    @FXML
    public MenuItem menuDeGroupSelected;
    @FXML
    private AnchorPane mainDrawPane;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private MenuItem menuLoad;
    @FXML
    private MenuItem menuNew;
    @FXML
    private MenuItem menuSave;
    @FXML
    private CheckBox toolCTRL;
    @FXML
    private ColorPicker toolColor;
    @FXML
    private ListView<ElementType> toolElements;
    @FXML
    private CheckBox toolIntersections;
    @FXML
    private Slider toolSlider;
    @FXML
    private TreeView<?> treeView;
    private PaneController paneController;

    private double getDouble() {
        return toolSlider.getValue();
    }

    private void initList() {
        toolElements.getItems().addAll(Arrays.stream(ElementType.values()).toList());
        toolElements.getSelectionModel().select(0);
    }

    @FXML
    private void selectAll() {
        paneController.selectAll();
    }

    @FXML
    private void deselectAll() {
        paneController.deselectAll();
    }

    @FXML
    private void groupSelected() {
        paneController.groupSelected();
    }

    @FXML
    private void deGroupSelected() {
        paneController.deGroupSelected();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initList();
        paneController = new PaneController(mainDrawPane);
        mainDrawPane.setOnMouseClicked(mouseEvent -> {
            if (!mouseEvent.getButton().equals(MouseButton.PRIMARY))
                return;
            Point2D center = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            if (paneController.anyCollision(center)) {
                if (!toolCTRL.isSelected() || !mouseEvent.isControlDown())
                    paneController.deselectAll();
                if (toolIntersections.isSelected()) {
                    paneController.selectAll(center);
                    return;
                }
                paneController.selectLast(center);
                return;
            }
            double radius = getDouble();
            Color color = toolColor.getValue();
            ElementType elementType = toolElements.getSelectionModel().getSelectedItem();
            paneController.addElement(center, radius, color, elementType);
        });
        mainScrollPane.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case DELETE -> paneController.deleteSelected();
                case W -> paneController.moveSelected(0, -1 * getDouble());
                case S -> paneController.moveSelected(0, getDouble());
                case A -> paneController.moveSelected(-1 * getDouble(), 0);
                case D -> paneController.moveSelected(getDouble(), 0);
                default -> System.out.println("Cannot handle this key sry");
            }
        });
    }


}
