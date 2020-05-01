/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.views;

import c195appointmentschedule.DAO.AddressDaoImpl;
import c195appointmentschedule.DAO.AppointmentDaoImpl;
import c195appointmentschedule.DAO.CustomerDaoImpl;
import c195appointmentschedule.DAO.UserDaoImpl;
import c195appointmentschedule.model.Customer;
import c195appointmentschedule.model.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static c195appointmentschedule.alerts.AppointmentTimeAlerts.*;
import c195appointmentschedule.model.Appointment;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AddAppointmentController implements Initializable {
    @FXML
    private ComboBox<Customer> cbox;
    @FXML
    private TextField fdUserId;
    @FXML
    private TextField fdTitle;
    @FXML
    private TextField fdDesc;
    @FXML
    private TextField fdLoc;
    @FXML
    private TextField fdContact;
    @FXML
    private TextField fdType;
    @FXML
    private TextField fdId;
    @FXML
    private TextField fdUrl;
    @FXML
    private TextField fdStart;
    @FXML
    private TextField fdEnd;
    @FXML
    private ComboBox<String> cbStartHr;
    @FXML
    private ComboBox<String> cbStartMin;
    @FXML
    private ComboBox<String> cbEndHr;
    @FXML
    private ComboBox<String> cbEndMin;
    @FXML
    private DatePicker datePicker;
    
    
    private ObservableList<String> hours = FXCollections.observableArrayList();
    private ObservableList<String> mins = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerDaoImpl custDao= CustomerDaoImpl.getCustomerDaoImpl();
        cbox.setItems(custDao.getAllCustomers());
        fdUserId.setText(UserDaoImpl.getUserDaoImpl().getCurrentUser().toString());
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        mins.addAll("00", "05", "10", "15","20", "25", "30", "35","40", "45", "50", "55");
        cbStartHr.setItems(hours);
        cbEndHr.setItems(hours);
        cbStartMin.setItems(mins);
        cbEndMin.setItems(mins);
    }    

    @FXML
    private void handleAdd(ActionEvent event) throws IOException {
        ///////////////Add logic 
        AppointmentDaoImpl apptDao = AppointmentDaoImpl.getAppointmentDaoImpl();
        // check fields are filled        
        if(datePicker.getValue() == null || cbox.getSelectionModel().getSelectedItem() == null || fdType.getText().trim().equals("")){
         //   System.out.println("erroer messages!!!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Type field must be filled.\nCustomer must be selected.\nDate, Start time, and End time must be selected.\nTitle, Description, Location, Contact, and URL fields are optional." );
            alert.showAndWait();
            return;
        }   else{
            LocalDate date= datePicker.getValue();
            String startHr = cbStartHr.getValue();
            String startMin = cbStartMin.getValue();
            String endHr = cbEndHr.getValue();
            String endMin = cbEndMin.getValue();
            LocalTime startTime = LocalTime.of(Integer.parseInt(startHr), Integer.parseInt(startMin));
            LocalTime endTime = LocalTime.of(Integer.parseInt(endHr),Integer.parseInt(endMin));
            LocalTime openingTime = LocalTime.of(9, 0);
            LocalTime closingTime = LocalTime.of(21 , 0);
            LocalDateTime start = LocalDateTime.of(date,startTime);
            LocalDateTime end = LocalDateTime.of(date, endTime);
            //check times arent the same
            if(startTime.equals(endTime)){
                apptStartEndSame();
            } else if (startTime.isAfter(endTime)){ //check times make sense
                apptStartAfterEnd();
            } else if (startTime.isAfter(closingTime) || startTime.isBefore(openingTime) || endTime.isAfter(closingTime)){ //check for businesshours
                apptOutsideBusinessHours();
            } else if(checkNoOverlap(start,end)){                 
////////////check for overlap
//                apptDao.getUserAppointments().forEach((Appointment appt) -> {
//                        if((start.isAfter(appt.getStart())&& start.isBefore(appt.getEnd())) || (end.isAfter(appt.getStart())&& end.isBefore(appt.getEnd()))){
//                                apptOverlap();                                  
//                        }
//                });   
//                boolean overlap = false;
//                for(Appointment appt: apptDao.getUserAppointments()){
//                    if((start.isAfter(appt.getStart())&& start.isBefore(appt.getEnd())) || (end.isAfter(appt.getStart())&& end.isBefore(appt.getEnd()))){
//                                apptOverlap();
//                                overlap = true;
//                }
//                    if(overlap == false){
                    User currentUser = UserDaoImpl.getUserDaoImpl().getCurrentUser();
                    apptDao.addAppointment(cbox.getSelectionModel().getSelectedItem().getCustomerID(),
                            currentUser.getUserID(),
                            fdTitle.getText().trim(),
                            fdDesc.getText().trim(),
                            fdLoc.getText().trim(),
                            fdContact.getText().trim(),
                            fdType.getText().trim(),
                            fdUrl.getText().trim(),
                            start,
                            end,
                            currentUser.getUserName(),
                            currentUser.getUserName());


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
                    Stage stage = (Stage) fdId.getScene().getWindow();  
                    Parent root = loader.load();       
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
//                    }
//                }
            }
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        ///////////////Add confirmation     
  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public boolean checkNoOverlap(LocalDateTime ldt){
        AppointmentDaoImpl apptDao = AppointmentDaoImpl.getAppointmentDaoImpl();    
        for(Appointment appt: apptDao.getUserAppointments()){
                    if( (ldt.isAfter(appt.getStart()) || ldt.isEqual(appt.getStart()))&&(ldt.isBefore(appt.getEnd()) || ldt.isEqual(appt.getEnd()))){
                                apptOverlap();
                                return false;
                }
        }
        return true;
    }
    public boolean checkNoOverlap(LocalDateTime ldt, LocalDateTime ldt2){
        if((!checkNoOverlap(ldt))) {
            return false;
        } else return checkNoOverlap(ldt2);
    }
}
