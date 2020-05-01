/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.views;

import c195appointmentschedule.DAO.AddressDaoImpl;
import c195appointmentschedule.DAO.CityDaoImpl;
import c195appointmentschedule.DAO.UserDaoImpl;
import c195appointmentschedule.model.City;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class AddAddressController implements Initializable {
    @FXML
    private ComboBox<City> cbox;
    @FXML
    private TextField fdAddress2;
    @FXML
    private TextField fdAddress1;
    @FXML
    private TextField fdLastUpdate;
    @FXML
    private TextField fdLastUpdateBy;
    @FXML
    private TextField fdId;
    @FXML
    private TextField fdPostCode;
    @FXML
    private TextField fdPhone;
    @FXML
    private TextField fdCreateDate;
    @FXML
    private TextField fdCreatedBy;
    @FXML
    private Label lblCreateDate;
    @FXML
    private Label lblCreatedBy;
    @FXML
    private Label lblLastUpdate;
    @FXML
    private Label lblLastUpdateBy;
    
    private static boolean fromAdd;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
//        lblCreateDate.setVisible(false);
//       fdCreateDate.setVisible(false);
//       lblCreatedBy.setVisible(false);
//       fdCreatedBy.setVisible(false);
//       lblLastUpdate.setVisible(false);
//       fdLastUpdate.setVisible(false);
//       lblLastUpdateBy.setVisible(false);
//       fdLastUpdateBy.setVisible(false);
        CityDaoImpl cityDao = CityDaoImpl.getCityDaoImpl();
        cbox.setItems(cityDao.getAllCities());
    }    

    @FXML
    private void handleNewCity(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add city.fxml"));
//        CityDaoImpl cityDao = CityDaoImpl.getCityDaoImpl();
//        cityDao.printAllCities();
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAdd() throws IOException{
        
////////////////////Need logic        
        if(fdAddress1.getText().trim().equals("") || fdPhone.getText().trim().equals("") || fdPostCode.getText().trim().equals("") || cbox.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Check Field Entries");
            alert.setContentText("Address,Postal Code, and Phone fields must be filled.\nCity box must have a selection." );
            alert.showAndWait();            
            return;
        } else{
            String userName = UserDaoImpl.getUserDaoImpl().getCurrentUser().getUserName();
            AddressDaoImpl.getAddressDaoImpl().addNewAddress(fdAddress1.getText().trim(), fdAddress2.getText().trim(), 
                    cbox.getSelectionModel().getSelectedItem().getCityID(), fdPostCode.getText().trim(), 
                    fdPhone.getText().trim(), userName, userName);
        }
        if(fromAdd){  
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add customer.fxml"));
            Stage stage = (Stage) fdId.getScene().getWindow();  
            Parent root = loader.load();       
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify customer.fxml"));
            Stage stage = (Stage) fdId.getScene().getWindow();  
            Parent root = loader.load();  
            ModifyCustomerController controller = loader.getController();
            controller.setFields();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException{
          ///////////////Add confirmation     
        if(fromAdd){  
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add customer.fxml"));
            Stage stage = (Stage) fdId.getScene().getWindow();  
            Parent root = loader.load();              
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify customer.fxml"));
            Stage stage = (Stage) fdId.getScene().getWindow();  
            Parent root = loader.load();   
            ModifyCustomerController controller = loader.getController();
            controller.setFields();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    public void fromCustAdd(){
        fromAdd = true;
    }
    public void fromCustMod(){
        fromAdd = false;
    }    
}
