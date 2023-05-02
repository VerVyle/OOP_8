package com.vervyle.oop.controllers;

import com.vervyle.oop.PaintApplication;
import com.vervyle.oop.utils.ElementType;
import com.vervyle.oop.utils.Point2D;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
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
    private TreeView<String> treeView;
    private PaneController paneController;

    public static final String PATH = "obj.json";

    private double getDouble() {
        return toolSlider.getValue();
    }

    private void initList() {
        toolElements.getItems().addAll(Arrays.stream(ElementType.values()).toList());
        toolElements.getSelectionModel().select(0);
    }

    private void initTree() {
        MultipleSelectionModel<TreeItem<String>> selectionModel = treeView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        TreeItem<String> root = new TreeItem<>("container");
        treeView.setRoot(root);
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

    @FXML
    private void resizeSelected() {
        paneController.resizeSelected(getDouble());
    }

    @FXML
    private void saveAll() {
        File file = new FileChooser().showSaveDialog(PaintApplication.getStage());
        paneController.saveAll(file.getAbsolutePath());
    }

    @FXML
    private void loadAll() {
        File file = new FileChooser().showOpenDialog(PaintApplication.getStage());
        paneController.loadAll(file.getAbsolutePath());
    }

    @FXML
    private void close() {
        paneController.deleteAll();
    }

    @FXML
    public void colorizeSelected() {
        paneController.colorizeSelected(toolColor.getValue());
    }

    Point2D bufferSource = null;
    boolean dragEntered = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initList();
        initTree();
        paneController = new PaneController(mainDrawPane, treeView);
        mainDrawPane.setOnMouseClicked(mouseEvent -> {
            dragEntered = false;
            bufferSource = null;
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
        mainDrawPane.setOnMousePressed(mouseDragEvent -> {
            double x = mouseDragEvent.getX(), y = mouseDragEvent.getY();
            Point2D point2D = new Point2D(x, y);
            if (paneController.anyCollision(point2D)) {
                bufferSource = point2D;
            }
        });
        mainDrawPane.setOnMouseReleased(mouseDragEvent -> {
            if (!dragEntered || bufferSource == null)
                return;
            double x = mouseDragEvent.getX(), y = mouseDragEvent.getY();
            Point2D target = new Point2D(x, y);
            paneController.stickElements(bufferSource, target);
        });
        mainDrawPane.setOnMouseDragged(mouseEvent -> {
            dragEntered = true;
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
