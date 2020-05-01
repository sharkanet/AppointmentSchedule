/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.views;

import c195appointmentschedule.DAO.CityDaoImpl;
import c195appointmentschedule.DAO.CountryDaoImpl;
import c195appointmentschedule.DAO.UserDaoImpl;
import c195appointmentschedule.model.Country;
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
public class AddCityController implements Initializable {
    @FXML
    private ComboBox<Country> cbox;
    @FXML
    private TextField fdCity;
    @FXML
    private TextField fdCreateDate;
    @FXML
    private TextField fdCreatedBy;
    @FXML
    private TextField fdLastUpdate;
    @FXML
    private TextField fdLastUpdateBy;
    @FXML
    private TextField fdId;
    @FXML
    private Label lblCreateDate;
    @FXML
    private Label lblCreatedBy;
    @FXML
    private Label lblLastUpdate;
    @FXML
    private Label lblLastUpdateBy;

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
        CountryDaoImpl countryDao = CountryDaoImpl.getCountryDaoImpl();
        cbox.setItems(countryDao.getAllCountries());
       
    }    

    @FXML
    private void handleNewCountry(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add country.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
//        CountryDaoImpl countryDao= CountryDaoImpl.getCountryDaoImpl();
//        countryDao.getAllCountries();
    }

    @FXML
    private void handleAdd(ActionEvent event) throws IOException {


//check city name has text and country is selected
        if(cbox.getSelectionModel().getSelectedItem() == null || fdCity.getText().trim().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Check Field Entries");
            alert.setContentText("City field must be filled.\nCountry box must have a selection." );
            alert.showAndWait();            
            return;            
        } else {
                String cityName= fdCity.getText().trim();            
           //     Country country = (Country) cbox.getSelectionModel().getSelectedItem();
                int countryId = cbox.getSelectionModel().getSelectedItem().getCountryID();
////// get current user name
                String user = UserDaoImpl.getUserDaoImpl().getCurrentUser().toString();
                CityDaoImpl cityDao = CityDaoImpl.getCityDaoImpl();      
                cityDao.addNewCity(cityName, countryId, user, user);           
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add address.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        /////////////////////////////////
///////////    Confirm?    ///////////////
////////////////////////////////////////
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add address.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
