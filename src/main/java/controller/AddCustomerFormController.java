package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private DatePicker txtDob;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXComboBox<String> txtTitle;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                txtTitle.getValue().toString(),
                txtName.getText(),
                txtDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
        if(new CustomerController().addCustomer(customer)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer Added Successfully...");
            alert.show();
            cleartxt();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer Didn't added....");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> title = FXCollections.observableArrayList();
        title.add("Mr.");
        title.add("Mss");
        txtTitle.setItems(title);
        generateID();
    }

    public void generateID() {
        try {
            String SQL = "SELECT MAX(CustID) FROM customer";  // Get the maximum customer ID
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet resultSet = pstm.executeQuery();

            int newId = 1;  // Start with ID 1 if there are no customers

            if (resultSet.next()) {
                String lastId = resultSet.getString(1);  // Get the maximum ID from the result set
                if (lastId != null) {
                    // Extract the numeric part of the ID and increment by 1
                    newId = Integer.parseInt(lastId.substring(1)) + 1;
                }
            }

            // Format the new ID with prefix 'C' and 3 digits
            txtId.setText(String.format("C%03d", newId));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cleartxt(){
        txtId.setText(null);
        txtTitle.getSelectionModel().clearSelection();
        txtName.setText(null);
        txtDob.setValue(null);
        txtSalary.setText(null);
        txtAddress.setText(null);
        txtCity.setText(null);
        txtProvince.setText(null);
        txtPostalCode.setText(null);
    }
}
