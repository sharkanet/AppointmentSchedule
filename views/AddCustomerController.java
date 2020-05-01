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
import java.io.IOException;
import java.net.URL;
import static java.time.LocalDateTime.now;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AddCustomerController implements Initializable {
    @FXML
    private ComboBox<Address> cbox;
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
    private Label lblActive;
    @FXML
    private Label lblCreateDate;
    @FXML
    private Label lblCreatedBy;
    @FXML
    private Label lblLastUpdate;
    @FXML
    private Label lblLastUpdateBy;
    
    //private Map<Integer,Address> addressMap = AddressDaoImpl.getAddressDaoImpl().getAddressIdToObjectMap();
    
    
    @FXML
    private void handleNewAddress() throws IOException{    
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add address.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();  
        AddAddressController controller2 = loader.getController();
        controller2.fromCustAdd();
//        AddressDaoImpl addressDao = AddressDaoImpl.getAddressDaoImpl();
//        addressDao.getAllAddresses();             
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        stage.show();
    }
    
    @FXML
    private void handleAdd() throws IOException{       
        if(fdName.getText().trim().equals("")||cbox.getSelectionModel().getSelectedItem() == null || cbActive.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Name field must be filled.\nAddress and Active box must have selections." );
            alert.showAndWait();            
            return;
        } else{
          //code to add customer
          String user = UserDaoImpl.getUserDaoImpl().getCurrentUser().getUserName();
          CustomerDaoImpl.getCustomerDaoImpl().addCustomer(
                  fdName.getText().trim(),
                  cbox.getSelectionModel().getSelectedItem().getAddressID(),
                  cbActive.getSelectionModel().getSelectedItem(),
                  user,
                  user );
        }        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleCancel() throws IOException{
        
  ///////////////Add confirmation ?    
  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // fdLastUpdate.setText(now().toString());
//       lblActive.setVisible(false);
//       fdActive.setVisible(false);
//       lblCreateDate.setVisible(false);
//       fdCreateDate.setVisible(false);
//       lblCreatedBy.setVisible(false);
//       fdCreatedBy.setVisible(false);
//       lblLastUpdate.setVisible(false);
//       fdLastUpdate.setVisible(false);
//       lblLastUpdateBy.setVisible(false);
//       fdLastUpdateBy.setVisible(false);
        ObservableList<Boolean> activity = FXCollections.observableArrayList();
        activity.add(Boolean.TRUE);
        activity.add(Boolean.FALSE);
        cbActive.setItems(activity);
        cbActive.getSelectionModel().select(Boolean.TRUE);
       
       AddressDaoImpl addressDao = AddressDaoImpl.getAddressDaoImpl();
       cbox.setItems(addressDao.getAllAddresses());
    }       
    
}
