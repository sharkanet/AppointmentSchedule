/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.alerts;

import c195appointmentschedule.DAO.UserDaoImpl;
import c195appointmentschedule.model.Appointment;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

/**
 *
 * @author Chris
 */
public class AppointmentTimeAlerts {
    
    public static void apptStartEndSame(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Start Time and End Time are the same");
            alert.setContentText("Start Time must be before End Time." );
            alert.showAndWait();
    }
    
    public static void apptStartAfterEnd(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Start Time after End Time");
            alert.setContentText("Start Time must be before End Time." );
            alert.showAndWait();
        
    }
    
    public static void apptOutsideBusinessHours(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Appointment scheduled after business hours");
            alert.setContentText("Business hours are between 9:00 AM (09:00) and 9:00 PM (21:00)" );
            alert.showAndWait();
    }
    
    public static void apptOverlap(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Appointment Overlap");
            alert.setContentText("Appointment time overlaps with another scheduled appointment." );
            alert.showAndWait();
        
    }
    
    public static void apptSoon(ObservableList<Appointment> appts){
        StringBuilder sb = new StringBuilder();
          
        //int uId = UserDaoImpl.getUserDaoImpl().getCurrentUser().getUserID();
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime timeSoon = timeNow.plusMinutes(15);
        
/*
        // trying out lambdas
        Predicate soon = ((Appointment appointment) -> {
            return appointment.getStart().isBefore(timeSoon);});
        ObservableList<Appointment> soonAppointment = appts.filtered(soon);
*/
        for(Appointment appt: appts){           
            LocalDateTime apptTime = appt.getStart();            
            if(apptTime.isAfter(timeNow) && (apptTime.isBefore(timeSoon) || apptTime.isEqual(timeSoon))){
                //sb.append(appt).append("\n");  
                sb.append("Appointment with Customer: ").append(appt.getCustomer()).append(" at: ").append(appt.getStart().toLocalTime()).append(System.getProperty("line.separator"));
            }
        }
       // System.out.println(sb.toString());
    //lambda to add each appointment to stringbuilder    
      // implement  
        if(sb.toString().equals("")){
            
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Upcoming Appointment");
            alert.setHeaderText("Appointment Starting Within 15 Minutes");
            alert.setContentText(sb.toString());        
            alert.showAndWait();
        }
    }
    
}
