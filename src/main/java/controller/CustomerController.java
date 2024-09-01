package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lombok.SneakyThrows;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController implements CustomerService{
    @Override
    public boolean addCustomer(Customer customer) {
        try {
            String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1,customer.getCustId());
            pstm.setObject(2,customer.getCustTitle());
            pstm.setObject(3,customer.getCustName());
            pstm.setObject(4,customer.getDob());
            pstm.setObject(5,customer.getSalary());
            pstm.setObject(6,customer.getCustAddress());
            pstm.setObject(7,customer.getCity());
            pstm.setObject(8,customer.getProvince());
            pstm.setObject(9,customer.getPostalCode());
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            String SQL ="UPDATE customer SET CustTitle='"+customer.getCustTitle()+"',CustName='"+customer.getCustName()+"',DOB='"+customer.getDob()+"',salary='"+customer.getSalary()+"',CustAddress='"+customer.getCustAddress()+"',City='"+customer.getCity()+"',Province='"+customer.getProvince()+"',PostalCode='"+customer.getPostalCode()+"' WHERE CustID='"+customer.getCustId()+"' ";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            boolean isUpdated = preparedStatement.executeUpdate()>0;
            return isUpdated;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public ResultSet searchCustomer(String id) {
        String SQL = "SELECT * FROM customer WHERE CustID='"+id+"' ";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet resultSet = pstm.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "DELETE FROM customer WHERE CustID='"+id+"'";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Boolean isdeleated =connection.createStatement().executeUpdate(SQL)>0;
            return  isdeleated;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAll() {
        try {
            String SQL = "SELECT * FROM customer";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet resultSet = pstm.executeQuery();
            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            while (resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")
                );
                System.out.println(customer);
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
