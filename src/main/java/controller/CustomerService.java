package controller;

import javafx.collections.ObservableList;
import model.Customer;

import java.sql.ResultSet;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    ResultSet searchCustomer(String  id);
    boolean deleteCustomer(String id);
    ObservableList<Customer> getAll();
}
