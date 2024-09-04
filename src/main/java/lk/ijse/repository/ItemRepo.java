package lk.ijse.repository;

import lk.ijse.db.DBConnection;
import lk.ijse.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static boolean save(Item item) throws SQLException, ClassNotFoundException {
        String sql= "INSERT INTO Item(ItemCode, ItemName, Price, QtyOnHand) VALUES(?,?,?,?)";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, item.getItemCode());
        pstm.setString(2, item.getItemName());
        pstm.setDouble(3, item.getPrice());
        pstm.setInt(4, item.getQtyOnHand());

         return pstm.executeUpdate() >0 ;
    }

    public static boolean update(Item item) throws SQLException, ClassNotFoundException {
        String sql= "UPDATE Item SET ItemName = ?, Price = ?, QtyOnHand = ? WHERE ItemCode = ?";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, item.getItemName());
        pstm.setDouble(2, item.getPrice());
        pstm.setInt(3, item.getQtyOnHand());
        pstm.setString(4, item.getItemCode());

        return pstm.executeUpdate() >0 ;
    }

    public static boolean delete(String itemCode) throws SQLException, ClassNotFoundException {
        String sql= "DELETE FROM Item WHERE ItemCode = ?";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, itemCode);
        return pstm.executeUpdate() >0 ;
    }

    public static Item search(String itemCode) throws SQLException, ClassNotFoundException {
        String sql= "SELECT * FROM Item WHERE ItemCode = ?";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, itemCode);

        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            String ItemCode = rst.getString(1);
            String ItemName = rst.getString(2);
            double Price = rst.getDouble(3);
            int QtyOnHand = rst.getInt(4);

            Item item = new Item(ItemCode, ItemName, Price, QtyOnHand);

            return item;
        }
        return null;

    }

    public static List<Item> getAll() throws SQLException, ClassNotFoundException {
        String sql= "SELECT * FROM Item";

        PreparedStatement pstm = DBConnection.getDbConnection().getConnection().prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();
        List<Item> itemList = new ArrayList<>();

        while (rst.next()){
            String ItemCode = rst.getString(1);
            String ItemName = rst.getString(2);
            double Price = rst.getDouble(3);
            int QtyOnHand = rst.getInt(4);

            Item item = new Item(ItemCode, ItemName, Price, QtyOnHand);
            itemList.add(item);
        }
        return itemList;
    }
}
