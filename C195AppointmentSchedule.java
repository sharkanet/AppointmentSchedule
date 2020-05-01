/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule;

import c195appointmentschedule.DAO.UserDaoImpl;
import c195appointmentschedule.utility.DBConn;
import c195appointmentschedule.views.LoginScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Chris
 */
public class C195AppointmentSchedule extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login screen.fxml"));
        Parent root = loader.load();
        DBConn.startConnection();
        LoginScreenController controller = loader.getController();
        UserDaoImpl userDao = UserDaoImpl.getUserDaoImpl();
        controller.setUsers(userDao.getAllUsers());        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
