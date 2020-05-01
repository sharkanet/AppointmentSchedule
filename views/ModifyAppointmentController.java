/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.views;

import c195appointmentschedule.DAO.AppointmentDaoImpl;
import c195appointmentschedule.DAO.CustomerDaoImpl;
import c195appointmentschedule.DAO.UserDaoImpl;
import static c195appointmentschedule.alerts.AppointmentTimeAlerts.apptOutsideBusinessHours;
import static c195appointmentschedule.alerts.AppointmentTimeAlerts.apptOverlap;
import static c195appointmentschedule.alerts.AppointmentTimeAlerts.apptStartAfterEnd;
import static c195appointmentschedule.alerts.AppointmentTimeAlerts.apptStartEndSame;
import c195appointmentschedule.model.Appointment;
import c195appointmentschedule.model.Customer;
import c195appointmentschedule.model.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class ModifyAppointmentController implements Initializable {
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
    private Appointment selectedAppointment;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerDaoImpl custDao= CustomerDaoImpl.getCustomerDaoImpl();
        cbox.setItems(custDao.getAllCustomers());       
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        mins.addAll("00", "05", "10", "15","20", "25", "30", "35","40", "45", "50", "55");
        cbStartHr.setItems(hours);
        cbEndHr.setItems(hours);
        cbStartMin.setItems(mins);
        cbEndMin.setItems(mins);
// fdUserId.setText(UserDaoImpl.getUserDaoImpl().getCurrentUser().toString());
    }    

    @FXML
    private void handleConfirm(ActionEvent event) throws IOException {        
        if(datePicker.getValue() == null || cbox.getSelectionModel().getSelectedItem() == null || fdType.getText().trim().equals("")){
          //  System.out.println("erroer messages!!!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Type field must be filled.\nCustomer must be selected.\nDate, Start time, and End time must be selected.\nTitle, Description, Location, Contact, and URL fields are optional." );
            alert.showAndWait();
            return;
        } else{
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
            if(startTime.equals(endTime)){ // check times not equal
                apptStartEndSame();
            } else if (startTime.isAfter(endTime)){  // check time logic                
                apptStartAfterEnd();
            } else if (startTime.isAfter(closingTime) || startTime.isBefore(openingTime) || endTime.isAfter(closingTime)){  // check hour within business hours                
                apptOutsideBusinessHours();
            } else if(checkNoOverlap(start,end)){        //check overlap          
                User currentUser = UserDaoImpl.getUserDaoImpl().getCurrentUser();
                        AppointmentDaoImpl.getAppointmentDaoImpl().updateAppointment(cbox.getSelectionModel().getSelectedItem().getCustomerID(),
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
                                Integer.valueOf(fdId.getText().trim()));
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
                    Stage stage = (Stage) fdId.getScene().getWindow();  
                    Parent root = loader.load();       
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
            }     
        }
        
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        ///////////////Add confirmation  ?   
  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
        Stage stage = (Stage) fdId.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setSelectedAppointment(Appointment appt){
        this.selectedAppointment=appt;
        setFields();
    }
    public void setFields(){
        
        this.fdUserId.setText(UserDaoImpl.getUserDaoImpl().getUserMap().get(selectedAppointment.getUserID()).toString());
        fdTitle.setText(selectedAppointment.getTitle());
        fdDesc.setText(selectedAppointment.getDescription());
        fdLoc.setText(selectedAppointment.getLocation());
        fdContact.setText(selectedAppointment.getContact());
        fdType.setText(selectedAppointment.getType());
        fdUrl.setText(selectedAppointment.getUrl());
        fdId.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        cbox.getSelectionModel().select(selectedAppointment.getCustomer());
        cbStartHr.getSelectionModel().select(String.valueOf(selectedAppointment.getStart().getHour()));        
        cbStartMin.getSelectionModel().select(String.valueOf(selectedAppointment.getStart().getMinute()));         
        cbEndHr.getSelectionModel().select(String.valueOf(selectedAppointment.getEnd().getHour()));    
        cbEndMin.getSelectionModel().select(String.valueOf(selectedAppointment.getEnd().getMinute()));         
        datePicker.setValue(selectedAppointment.getStart().toLocalDate());
    }
    public boolean checkNoOverlap(LocalDateTime ldt){
        AppointmentDaoImpl apptDao = AppointmentDaoImpl.getAppointmentDaoImpl();    
        for(Appointment appt: apptDao.getUserAppointments()){
            // check times do not overlap with another appointment's times
                    if((appt.getAppointmentID()!= Integer.parseInt(fdId.getText().trim())) && (ldt.isAfter(appt.getStart()) || ldt.equals(appt.getStart())) && (ldt.isBefore(appt.getEnd()) || ldt.equals(appt.getEnd()))){
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
