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
import static c195appointmentschedule.alerts.AppointmentTimeAlerts.apptSoon;
import c195appointmentschedule.model.Appointment;
import c195appointmentschedule.model.User;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Chris
 */
public class LoginScreenController implements Initializable{
    @FXML
    private Button btnLogin;    
    @FXML
    private Button btnExit;
    @FXML
    private PasswordField fdPw;
    @FXML
    private TextField fdUser;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblPw;
    @FXML
    private Label lblTitle;
    
    private ObservableList<User> users;
    private boolean match = false; 
    private ResourceBundle bundle;
    
    private void verifyLogin(String loginUser, String loginPw){
        users.forEach(u->{
///////lambda to search through user list to find a match
            if(u.getUserName().equals(loginUser)&&u.getPassword().equals(loginPw)){
                match= true;
                UserDaoImpl userDao = UserDaoImpl.getUserDaoImpl();
                userDao.setCurrentUser(u);
                
            }
        });
    }
    private void checkUpcommingAppointments(){
            AppointmentDaoImpl apptDao = AppointmentDaoImpl.getAppointmentDaoImpl();
            ObservableList<Appointment> appts = apptDao.getUserAppointments();
            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime timeSoon = timeNow.plusMinutes(15);
            ObservableList<Appointment> apptFuture = FXCollections.observableArrayList();
// lambda is a clear and easy way to loop through list
// adds each appointment that has not passed to list
            appts.forEach(appt-> {
                if(appt.getEnd().isAfter(timeNow))
                    apptFuture.add(appt);
                });
// if there are any appointments in the list it is passed to a function to find appointments starting within 15 minutes            
            if(apptFuture.isEmpty()==false){
                apptSoon(apptFuture);
            }
    }
    @FXML
    private void toMain() throws IOException{
    // login info check  
        String loginUser = fdUser.getText().trim();
        String loginPw = fdPw.getText().trim();
        verifyLogin(loginUser,loginPw);
        if(match){
            logLogin(loginUser);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen v2.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();  
            Parent root = loader.load();       
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
//            CustomerDaoImpl custDao = CustomerDaoImpl.getCustomerDaoImpl();
//            custDao.getAllCustomers();
/*
            AppointmentDaoImpl apptDao = AppointmentDaoImpl.getAppointmentDaoImpl();
            ObservableList<Appointment> appts = apptDao.getUserAppointments();
            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime timeSoon = timeNow.plusMinutes(15);
            ObservableList<Appointment> apptFuture = FXCollections.observableArrayList();
// lambda is a clear and easy way to loop through list
// adds each appointment that has not passed to list
            appts.forEach(appt-> {
                if(appt.getEnd().isAfter(timeNow))
                    apptFuture.add(appt);
                });
// if there are any appointments in the list it is passed to a function to find appointments starting within 15 minutes            
            if(apptFuture.isEmpty()==false){
                apptSoon(apptFuture);
            }*/
            checkUpcommingAppointments();
        } else {            
///////////////// add error popup with localization
              //  ResourceBundle bundle = ResourceBundle.getBundle("c195appointmentschedule/views/LoginScreen", Locale.GERMAN);
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle(bundle.getString("ERROR"));
                 alert.setHeaderText(bundle.getString("Incorrect_user_or_password"));
                 alert.setContentText(bundle.getString("Incorrect_user_or_password")+".");
                 ((Button)alert.getDialogPane().lookupButton(ButtonType.OK)).setText(bundle.getString("OK"));
                 alert.showAndWait();
                 return;           
        }
    }
    
    @FXML
    private void exit(){
       // ResourceBundle bundle = ResourceBundle.getBundle("c195appointmentschedule/views/LoginScreen", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("EXIT"));
        alert.setHeaderText(bundle.getString("Exit_program"));
        alert.setContentText(bundle.getString("Are_you_sure?"));       
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(bundle.getString("OK"));
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(bundle.getString("Cancel"));


        Optional<ButtonType> result= alert.showAndWait();
        if(result.get()== ButtonType.OK){
            Platform.exit();
        } else { 
        }
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            //languages supported: english german
            bundle = ResourceBundle.getBundle("c195appointmentschedule/views/LoginScreen", Locale.getDefault());
            lblUser.setText(bundle.getString("User"));
            lblPw.setText(bundle.getString("Password"));
            lblTitle.setText(bundle.getString("Title")); 
            btnLogin.setText(bundle.getString("Login"));
            btnExit.setText(bundle.getString("Exit"));
        } catch(Exception e){
            System.out.println("resource bundle failed");
        }
         
    }       
    
    public void setUsers(ObservableList<User> u){
        this.users=u;
    }
    public void logLogin(String userName){
        String logFile = "logins.txt";
// try with for auto close        
        try(FileWriter outputFile = new FileWriter(logFile, true)){
            outputFile.append(userName +" logged in on " + LocalDate.now() + " at " + LocalTime.now() + "\n");
        } catch (IOException e){
            e.printStackTrace();
        }       
        
    }
            
}
