/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.alerts;

import javafx.scene.control.Alert;

/**
 *
 * @author Chris
 */
public class SQLAlerts {
    
    public static void sqlAddError(String s){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("SQL Exception");
            alert.setContentText("Unable to add " + s );
            alert.showAndWait();
    }
    
    public static void sqlUpdateError(String s){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("SQL Exception");
            alert.setContentText("Unable to update " + s );
            alert.showAndWait();
    }
    
    public static void sqlReadError(String s){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("SQL Exception");
            alert.setContentText("Unable to read " + s );
            alert.showAndWait();
    }
     public static void sqlDeleteError(String s){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("SQL Exception");
            alert.setContentText("Failed to delete " + s );
            alert.showAndWait();
    }
     public static void sqlConnectionError(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("SQL Exception");
            alert.setContentText("Failed to connect to database");
            alert.showAndWait();
    }
     
}
