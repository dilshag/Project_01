package lk.ijse.repository;

import lk.ijse.db.DBConnection;
import lk.ijse.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer(CustomerID,Customer_Name,Customer_Address,PhoneNumber) VALUES(?,?,?,?)";


        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, customer.getCustomerID());
        pstm.setString(2, customer.getCustomer_Name());
        pstm.setString(3, customer.getCustomer_Address());
        pstm.setString(4, customer.getPhoneNumber());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String customerID) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE customerID=?";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, customerID);
        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET Customer_Name=?, Customer_Address=?,PhoneNumber=? WHERE CustomerID=?";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, customer.getCustomer_Name() );
        pstm.setString(2, customer.getCustomer_Address());
        pstm.setString(3, customer.getPhoneNumber());
        pstm.setString(4, customer.getCustomerID());

        return pstm.executeUpdate() > 0;
    }


    public static Customer search(String customerID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer WHERE CustomerID=?";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, customerID);

        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            String CustomerID = rst.getString(1);
            String Customer_Name = rst.getString(2);
            String Customer_Address = rst.getString(3);
            String PhoneNumber = rst.getString(4);

            Customer customer = new Customer(CustomerID,Customer_Name,Customer_Address,PhoneNumber);

            return customer;
        }
        return null;
    }

    public static List<Customer> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer";

        PreparedStatement pstm = DBConnection.getDbConnection().getConnection().prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();
        List<Customer> customerList= new ArrayList<>();

        while (rst.next()) {
            String CustomerID = rst.getString(1);
            String Customer_Name = rst.getString(2);
            String Customer_Address = rst.getString(3);
            String PhoneNumber = rst.getString(4);

            Customer customer = new Customer(CustomerID,Customer_Name,Customer_Address,PhoneNumber);
            customerList.add(customer);
        }

        return customerList;
    }
}