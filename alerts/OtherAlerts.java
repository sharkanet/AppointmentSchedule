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
public class OtherAlerts {
    public static void nothingSelected(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Select an item and try again." );
            alert.showAndWait();
        
    }
}
