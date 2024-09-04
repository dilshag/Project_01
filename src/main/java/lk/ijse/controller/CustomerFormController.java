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
import lk.ijse.db.DBConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.repository.CustomerRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    public TextField txtCustomerID;
    public TextField txtCustomer_Address;
    public TextField txtCustomer_Name;
    public TextField txtPhoneNumber;
    public AnchorPane root;
    public TableColumn<?,?> colID;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colAddress;
    public TableColumn<?,?> colNum;
    public TableView <CustomerTm>tblCustomer;


    public void initialize() throws ClassNotFoundException {
        setCellValue();
        loadAllCustomers();
    }

    private void loadAllCustomers() throws ClassNotFoundException {
        ObservableList<CustomerTm> obList= FXCollections.observableArrayList();

        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTm customerTm = new CustomerTm(
                    customer.getCustomerID(),
                    customer.getCustomer_Name(),
                    customer.getCustomer_Address(),
                    customer.getPhoneNumber()
                );

                    obList.add(customerTm);

            }
                tblCustomer.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private void setCellValue(){
        colID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Customer_Address"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));

    }

    private void clearFields() {
        txtCustomerID.setText("");
        txtCustomer_Name.setText("");
        txtCustomer_Address.setText("");
        txtPhoneNumber.setText("");
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String CustomerID = txtCustomerID.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(CustomerID);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Customer Delete Failed").show();
        }
    }
        /*String sql = "DELETE FROM Customer WHERE customerID=?";

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, CustomerID);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer has been deleted").show();
                clearFields();
            }

        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Customer Delete Failed").show();
        }
    }*/

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String CustomerID = txtCustomerID.getText();
        String Customer_Name = txtCustomer_Name.getText();
        String Customer_Address = txtCustomer_Address.getText();
        String PhoneNumber = txtPhoneNumber.getText();

        Customer customer = new Customer(CustomerID, Customer_Name, Customer_Address, PhoneNumber);
        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved").show();
                clearFields();
                loadAllCustomers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Customer Save Failed").show();
        }
    }
      /* String sql = "INSERT INTO Customer(CustomerID,Customer_Name,Customer_Address,PhoneNumber) VALUES(?,?,?,?)";

       try{
           Connection connection = DBConnection.getDbConnection().getConnection();
           PreparedStatement pstm = connection.prepareStatement(sql);
           pstm.setString(1, CustomerID);
           pstm.setString(2, Customer_Name);
           pstm.setString(3, Customer_Address);
           pstm.setString(4, PhoneNumber);

           boolean isSaved = pstm.executeUpdate() > 0;
           if(isSaved){
              new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Successfully").show();
                clearFields();
           }

       }catch (SQLException e){
           new Alert(Alert.AlertType.ERROR, "Customer Save Failed").show();
       }
    }*/


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String CustomerID = txtCustomerID.getText();
        String Customer_Name = txtCustomer_Name.getText();
        String Customer_Address = txtCustomer_Address.getText();
        String PhoneNumber = txtPhoneNumber.getText();

        Customer customer = new Customer(CustomerID, Customer_Name, Customer_Address, PhoneNumber);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Customer Update Failed").show();
        }
    }

       /* String sql = "UPDATE Customer SET Customer_Name=?, Customer_Address=?,PhoneNumber=? WHERE CustomerID=?";

     try {
         Connection connection = DBConnection.getDbConnection().getConnection();
         PreparedStatement pstm = connection.prepareStatement(sql);
         pstm.setString(1, Customer_Name);
         pstm.setString(2, Customer_Address);
         pstm.setString(3, PhoneNumber);
         pstm.setString(4, CustomerID);

         boolean isUpdated = pstm.executeUpdate() > 0;
         if(isUpdated){
             new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated Successfully").show();
             clearFields();
     }
     }catch (SQLException e){
         new Alert(Alert.AlertType.ERROR, "Customer Update Failed").show();
     }*/



    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String CustomerID = txtCustomerID.getText();

        Customer customer = CustomerRepo.search(CustomerID);
        if (customer != null) {
            txtCustomerID.setText(customer.getCustomerID());
            txtCustomer_Name.setText(customer.getCustomer_Name());
            txtCustomer_Address.setText(customer.getCustomer_Address());
            txtPhoneNumber.setText(customer.getPhoneNumber());
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
        }
    }
       /* String sql = "SELECT * FROM Customer WHERE CustomerID=?";

        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, CustomerID);

            ResultSet rst = pstm.executeQuery();
            if(rst.next()){
                txtCustomer_Name.setText(rst.getString("Customer_Name"));
                txtCustomer_Address.setText(rst.getString("Customer_Address"));
                txtPhoneNumber.setText(rst.getString("PhoneNumber"));
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
        }*/



    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
        Stage stage= (Stage)root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("HOME PAGE");
        stage.centerOnScreen();

    }
}
