package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCustomerFormController {

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtDob;

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
    private JFXTextField txtTitle;

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        ResultSet resultSet = new CustomerController().searchCustomer(txtId.getText());
        if (resultSet.next()) {
            // Populate the fields with the retrieved data
            txtTitle.setText(resultSet.getString("CustTitle"));
            txtName.setText(resultSet.getString("CustName"));
            txtDob.setText(resultSet.getString("DOB"));
            txtSalary.setText(resultSet.getString("salary"));
            txtAddress.setText(resultSet.getString("CustAddress"));
            txtCity.setText(resultSet.getString("City"));
            txtProvince.setText(resultSet.getString("Province"));
            txtPostalCode.setText(resultSet.getString("PostalCode"));
        } else {
            // Show an alert if no customer is found
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Not Found");
            alert.setHeaderText(null);
            alert.setContentText("No customer found with ID: " + txtId.getText());
            alert.showAndWait();
        }
    }

}
