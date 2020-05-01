/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.views;

import c195appointmentschedule.DAO.AddressDaoImpl;
import c195appointmentschedule.DAO.CustomerDaoImpl;
import c195appointmentschedule.DAO.UserDaoImpl;
import c195appointmentschedule.model.Address;
import c195appointmentschedule.model.Customer;
import java.io.IOException;
import java.net.URL;
import static java.time.LocalDateTime.now;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class ModifyCustomerController implements Initializable {
    @FXML
    private TextField fdId;
    @FXML
    private TextField fdName;
    @FXML
    private TextField fdCreateDate;
    @FXML
    private TextField fdCreatedBy;
    @FXML
    private TextField fdLastUpdate;
    @FXML
    private TextField fdLastUpdateBy;
    @FXML
    private ComboBox<Boolean> cbActive;
    @FXML
    private ComboBox<Address> cbox;    
    
    private static Customer lastSelectedCustomer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<Boolean> activity = FXCollections.observableArrayList();
        activity.add(Boolean.TRUE);
        activity.add(Boolean.FALSE);
        cbActive.setItems(activity);
         AddressDaoImpl addressDao = AddressDaoImpl.getAddressDaoImpl();
        cbox.setItems(addressDao.getAllAddresses());     
        
        //fdLastUpdate.setText(now().toString());
    }    
    
    
    
    @FXML
    private void handleNewAddress() throws IOException{    
///////need some way to save the customer selected when returning to modify screen
///////
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add address.fxml"));      
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();
        AddAddressController controller2 = loader.getController();
        controller2.fromCustMod();   
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        stage.show();
    }
    
    @FXML
    private void handleConfirm() throws IOException{        
        if(fdName.getText().trim().equals("")||cbox.getSelectionModel().getSelectedItem() == null || cbActive.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Name field must be filled.\nAddress and Active box must have selections." );
            alert.showAndWait();            
            return;
        } else{
          //code to update customer
          String user = UserDaoImpl.getUserDaoImpl().getCurrentUser().getUserName();
          CustomerDaoImpl.getCustomerDaoImpl().updateCustomer(
                  fdName.getText().trim(),
                  cbox.getSelectionModel().getSelectedItem().getAddressID(),
                  cbActive.getSelectionModel().getSelectedItem(),
                  user,
                  Integer.valueOf(fdId.getText().trim()));
        }      
        
////////////////////Need logic


        FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleCancel() throws IOException{
        
  ///////////////Add confirmation     
  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setCustomer(Customer cust){
       this.lastSelectedCustomer=cust;
       setFields();

    }
    public void setFields(){
        // setup screen fields       
        fdId.setText(String.valueOf(lastSelectedCustomer.getCustomerID()));
        fdName.setText(lastSelectedCustomer.getCustomerName());
        fdCreatedBy.setText(lastSelectedCustomer.getCreatedBy());
        fdCreateDate.setText(String.valueOf(lastSelectedCustomer.getCreateDate()));
//        if(customer.getActive()){
//            cbActive.getSelectionModel().select(Boolean.TRUE);
//        } else {
//             cbActive.getSelectionModel().select(Boolean.FALSE);
//        }
        cbActive.getSelectionModel().select(lastSelectedCustomer.getActive());
        cbox.getSelectionModel().select(lastSelectedCustomer.getAddress());
    }
    public void printSomething (){
        System.out.println("test");
    }
}
