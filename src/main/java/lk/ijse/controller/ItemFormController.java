package lk.ijse.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.Customer;
import lk.ijse.model.Item;
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.model.tm.ItemTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.ItemRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    public AnchorPane root;
    public TableColumn <?,?> colCode;
    public TableColumn <?,?>colItemName;
    public TableColumn <?,?>colPrice;
    public TableColumn <?,?>colQTY;
    public TableView <ItemTm> tblItem;
    public TextField txtItemCode;
    public TextField txtItemName;
    public TextField txtPrice;
    public TextField txtQTYOnHand;

    public void initialize() throws ClassNotFoundException {
        setCellValue();
        loadAllCustomers();
    }

    private void loadAllCustomers() throws ClassNotFoundException {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        try {
            List<Item> ItemList = ItemRepo.getAll();
            for (Item item : ItemList) {
                ItemTm itemTm = new ItemTm(
                    item.getItemCode(),
                    item.getItemName(),
                    item.getPrice(),
                    item.getQtyOnHand()
                );
                obList.add(itemTm);
            }
                tblItem.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void setCellValue() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("QTYOnHand"));

    }

    private void clearFields() {
       txtItemCode.setText("");
       txtItemName.setText("");
       txtPrice.setText("");
       txtQTYOnHand.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String ItemCode = txtItemCode.getText();

        try {
            boolean isDeleted = ItemRepo.delete(ItemCode);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Item Delete Failed").show();
        }
    }


    @FXML
    void btnNewItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {

        String ItemCode = txtItemCode.getText();
        String ItemName = txtItemName.getText();
        double Price = Double.parseDouble(txtPrice.getText());
        int QtyOnHand = Integer.parseInt(txtQTYOnHand.getText());

        Item item = new Item(ItemCode, ItemName, Price, QtyOnHand);
        try {
            boolean isSaved = ItemRepo.save(item);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Item saved").show();
                clearFields();
                loadAllCustomers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Item Save Failed").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String ItemCode = txtItemCode.getText();
        String ItemName = txtItemName.getText();
        double Price = Double.parseDouble(txtPrice.getText());
        int QtyOnHand = Integer.parseInt(txtQTYOnHand.getText());

        Item item = new Item(ItemCode, ItemName, Price, QtyOnHand);
        try {
            boolean isUpdated = ItemRepo.update(item);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Item updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Item Update Failed").show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String ItemCode = txtItemCode.getText();

        Item item = ItemRepo.search(ItemCode);
        if (item != null) {
            txtItemCode.setText(item.getItemCode());
            txtItemName.setText(item.getItemName());
            txtPrice.setText(String.valueOf(item.getPrice()));
            txtQTYOnHand.setText(String.valueOf(item.getQtyOnHand()));
        } else {
            new Alert(Alert.AlertType.ERROR, "Item Not Found").show();
        }
    }


    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
        Stage stage= (Stage)root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Home Page");
        stage.centerOnScreen();
    }
}
