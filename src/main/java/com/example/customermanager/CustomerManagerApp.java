package com.example.customermanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Customer Manager (Lecture 3 classroom lab). Data is kept in memory, so closing the app loses it. */
public class CustomerManagerApp extends Application {

    // >>> EDIT THESE TWO LINES WITH YOUR OWN DETAILS <<<
    private static f.inal String STUDENT_NAME = "Ugolisa Isaac Urobo";
    private static final String STUDENT_NUMBER = "202512447";

    // Step 2: the list the table listens to. Add or remove here and the table updates by itself.
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // ---- Step 1: the form (name field + province list) ----
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., Mary Banda");
        Label nameLabel = new Label("Customer name");
        nameLabel.setLabelFor(nameField);

        ComboBox<String> provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll("Central", "Copperbelt", "Eastern", "Luapula", "Lusaka",
                "Muchinga", "Northern", "North-Western", "Southern", "Western");
        provinceBox.setPromptText("Choose a province");
        provinceBox.setMaxWidth(Double.MAX_VALUE);
        Label provinceLabel = new Label("Province");
        provinceLabel.setLabelFor(provinceBox);

        Button saveButton = new Button("Save customer");
        saveButton.setDefaultButton(true); // Enter activates Save
        Button deleteButton = new Button("Delete selected");
        Label status = new Label();

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(10);
        form.addRow(0, nameLabel, nameField);
        form.addRow(1, provinceLabel, provinceBox);
        GridPane.setHgrow(nameField, Priority.ALWAYS);
        GridPane.setHgrow(provinceBox, Priority.ALWAYS);

        // ---- Step 3: the TableView with name and province columns ----
        TableView<Customer> table = new TableView<>();
        table.setItems(customers);
        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(280);
        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        provinceCol.setPrefWidth(200);
        table.getColumns().add(nameCol);
        table.getColumns().add(provinceCol);
        table.setPlaceholder(new Label("No customers saved yet."));
        VBox.setVgrow(table, Priority.ALWAYS);

        // Delete stays greyed out until the user selects a row
        deleteButton.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());

        // ---- Step 4: validate input, then add the customer ----
        saveButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                status.setText("Enter the customer name.");
                nameField.requestFocus();
                return; // keep what the user typed, stop here
            }
            String province = provinceBox.getValue();
            if (province == null) {
                status.setText("Choose a province.");
                provinceBox.requestFocus();
                return;
            }
            customers.add(new Customer(name, province));
            status.setText("Customer saved.");
            // clear the fields only after a successful save
            nameField.clear();
            provinceBox.setValue(null);
            nameField.requestFocus();
        });

        // ---- Step 5: confirm deletion of the selected customer ----
        deleteButton.setOnAction(event -> {
            Customer selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                return;
            }
            ButtonType delete = new ButtonType("Delete");
            Alert ask = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete the selected customer?", delete, ButtonType.CANCEL);
            ask.setHeaderText("Confirm deletion");
            ask.initOwner(stage);
            if (ask.showAndWait().orElse(ButtonType.CANCEL) == delete) {
                customers.remove(selected);
                status.setText("Customer deleted.");
            }
            // Cancel or closing the dialog leaves the record unchanged
        });

        // A simple menu bar (slide 24)
        MenuItem closeItem = new MenuItem("Close");
        closeItem.setOnAction(event -> stage.close());
        MenuBar menuBar = new MenuBar(new Menu("File", null, closeItem));

        HBox buttons = new HBox(10, saveButton, deleteButton);
        VBox content = new VBox(12, form, buttons, status, table);
        content.setPadding(new Insets(16));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(content);

        stage.setTitle("Customer Manager - " + STUDENT_NAME + " - " + STUDENT_NUMBER);
        stage.setScene(new Scene(root, 560, 520));
        stage.show();
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
