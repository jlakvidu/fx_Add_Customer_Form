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

public class DeleteCustomerFormController {

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
    void btnDeleteOnAction(ActionEvent event) {
        if(new CustomerController().deleteCustomer(txtId.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer Deleted SuccessFully");
            alert.show();
            cleartxt();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer Didn't Found");
            alert.show();
        }
    }

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

    public void cleartxt(){
        txtId.setText(null);
        txtTitle.setText(null);
        txtName.setText(null);
        txtDob.setText(null);
        txtSalary.setText(null);
        txtAddress.setText(null);
        txtCity.setText(null);
        txtProvince.setText(null);
        txtPostalCode.setText(null);
    }
}
