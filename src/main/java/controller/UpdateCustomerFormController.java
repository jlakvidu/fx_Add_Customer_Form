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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateCustomerFormController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> title = FXCollections.observableArrayList();
        title.add("Mr.");
        title.add("Mss");
        txtTitle.setItems(title);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        ResultSet resultSet = new CustomerController().searchCustomer(txtId.getText());
        if (resultSet.next()) {
            // Populate the fields with the retrieved data
            txtTitle.setValue(resultSet.getString("CustTitle"));
            txtName.setText(resultSet.getString("CustName"));
            txtDob.setValue(resultSet.getDate("DOB").toLocalDate());
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

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(
                txtId.getText(),
                txtTitle.getValue(),
                txtName.getText(),
                txtDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
        boolean isUpdated = new CustomerController().updateCustomer(customer);
        if(isUpdated){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer Updated Successfully..");
            alert.show();
            cleartxt();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer didn't Updated ...");
            alert.show();
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
