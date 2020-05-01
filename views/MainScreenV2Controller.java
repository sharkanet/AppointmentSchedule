/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.views;

import c195appointmentschedule.DAO.AppointmentDaoImpl;
import c195appointmentschedule.DAO.CustomerDaoImpl;
import c195appointmentschedule.DAO.UserDaoImpl;
import c195appointmentschedule.alerts.SQLAlerts;
import c195appointmentschedule.model.Address;
import c195appointmentschedule.model.Appointment;
import c195appointmentschedule.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class MainScreenV2Controller implements Initializable {

    @FXML
    private Button btnAddCust;
    @FXML
    private TableView<Customer> tblCust;
    @FXML
    private TableColumn<Customer, Integer> tblCustId;
    @FXML
    private TableColumn<Customer, Customer> tblCustName;
    @FXML
    private TableColumn<Customer, Address> tblCustAddress;
    @FXML
    private TableColumn<Customer, Boolean> tblCustActive;
    
    @FXML
    private TableView<Appointment> tblAppt;
    @FXML
    private TableColumn<Appointment, Integer> tblApptId;
    @FXML
    private TableColumn<Appointment, String> tblApptCust;
    @FXML
    private TableColumn<Appointment, String> tblApptTitle;
    @FXML
    private TableColumn<Appointment, String> tblApptType;
    @FXML
    private TableColumn<Appointment, String> tblApptLoc;
    @FXML
    private TableColumn<Appointment, LocalDateTime> tblApptStart;
    @FXML
    private RadioButton btnAll;
    @FXML
    private RadioButton btnWeek;
    @FXML
    private RadioButton btnMonth;
    @FXML
    private Label lblTitle;
    
    private ObservableList<Appointment> allUserAppts;
    ObservableList<Appointment> weekAppts = FXCollections.observableArrayList();
    ObservableList<Appointment> monthAppts = FXCollections.observableArrayList();
    private enum rbOption{
        ALL,
        WEEK,
        MONTH
    }
    private rbOption rbSelected;
    
    @FXML
    private void handleBtnAddCust() throws IOException{        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add customer.fxml"));
        Stage stage = (Stage) btnAddCust.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleBtnModCust()throws IOException{        
   
        if(tblCust.getSelectionModel().getSelectedItem()==null){
            System.out.println("error message: nothing selected");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify customer.fxml"));
           Parent root = loader.load(); 
           ModifyCustomerController controller2 = loader.getController();            
        // pass selected customer to modify customer screen
            Customer selectedCustomer = tblCust.getSelectionModel().getSelectedItem();
          //  System.out.println(selectedCustomer);
            controller2.setCustomer(selectedCustomer);           
            Stage stage = (Stage) btnAddCust.getScene().getWindow();                    
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void handleBtnDelCust(){
        if(tblCust.getSelectionModel().getSelectedItem()==null){
            System.out.println("nothing selected");
        } else{
//            Customer selected = tblCust.getSelectionModel().getSelectedItem();
//            Alert alert = new Alert(AlertType.CONFIRMATION);
//            alert.setTitle("Confirm Delete");
//            alert.setHeaderText("Deleting Customer " + selected.getCustomerName() + " and all associated appointments!");
//            alert.setContentText("Are you sure?");
//            Optional<ButtonType> result = alert.showAndWait();
//            if(result.get() == ButtonType.OK){
//                CustomerDaoImpl.getCustomerDaoImpl().deleteCustomer(selected.getCustomerID());                
//            } else {
//                return;
//            }          
            Customer selected = tblCust.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure?" , ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Confirm Delete!");
            alert.setHeaderText("Deleting Customer " + selected.getCustomerName() + " and all associated appointments!");
            alert.showAndWait()
// lambda to determine when ok button pressed so action can be taken
// lambdas here make the code cleaner and easier to read
                    .filter(res-> res == ButtonType.OK)
// lambda to set the action that is taken when button pressed
                    .ifPresent( res-> {
                                CustomerDaoImpl.getCustomerDaoImpl().deleteCustomer(selected.getCustomerID());
                               }
                    );
        }
    }
    
    @FXML
    private void handleBtnAddAppt() throws IOException{        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add appointment.fxml"));
        Stage stage = (Stage) btnAddCust.getScene().getWindow();  
        Parent root = loader.load();       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleBtnModAppt()throws IOException{      
        if(tblAppt.getSelectionModel().getSelectedItem()==null){
            System.out.println("nothing selected");
        } else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify appointment.fxml"));
            Stage stage = (Stage) btnAddCust.getScene().getWindow();  
            Parent root = loader.load();       
            ModifyAppointmentController controller = loader.getController();
            controller.setSelectedAppointment(tblAppt.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();            
        }
    }
    @FXML
    private void handleBtnDelAppt(){
         if(tblAppt.getSelectionModel().getSelectedItem()==null){
            System.out.println("nothing selected");
        } else{
             Appointment selected = tblAppt.getSelectionModel().getSelectedItem();             
             Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Deleting " + selected);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                AppointmentDaoImpl.getAppointmentDaoImpl().deleteAppointment(selected);
                //tblAppt.setItems(AppointmentDaoImpl.getAppointmentDaoImpl().getUserAppointments());
                if(rbSelected == rbOption.MONTH){
                    handleBtnMonth();
                }
            } else {
                return;
            }
         }
    }
    
    @FXML
    private void handleBtnAll(){
        rbSelected = rbOption.ALL;
        tblAppt.setItems(allUserAppts);  
        System.out.println("btn");
    }
    @FXML
    private void handleBtnWeek(){
        rbSelected = rbOption.WEEK;
  //      ObservableList<Appointment> weekAppts;
        LocalDate todayDate = LocalDate.now();
        DayOfWeek todayDay = todayDate.getDayOfWeek();
        LocalDate startOfWeek = LocalDate.now(); // week starts sunday 
        LocalDate endOfWeek = LocalDate.now();   // week ends saturday
        switch(todayDay){
            case SUNDAY:
                startOfWeek = todayDate;
                endOfWeek = todayDate.plusDays(6);
                //System.out.println(startOfWeek +"\t" + endOfWeek);
                break;
            case MONDAY:
                startOfWeek = todayDate.minusDays(1);
                endOfWeek = todayDate.plusDays(5);
               // System.out.println(startOfWeek +"\t" + endOfWeek);
                break;
            case TUESDAY:
                startOfWeek = todayDate.minusDays(2);
                endOfWeek = todayDate.plusDays(4);
                //System.out.println(startOfWeek +"\t" + endOfWeek);
                break;
            case WEDNESDAY:
                startOfWeek = todayDate.minusDays(3);
                endOfWeek = todayDate.plusDays(3);
               // System.out.println(startOfWeek +"\t" + endOfWeek);
                break;
            case THURSDAY:
                startOfWeek = todayDate.minusDays(4);
                endOfWeek = todayDate.plusDays(2);
               // System.out.println(startOfWeek +"\t" + endOfWeek);
                break;
            case FRIDAY:
                startOfWeek = todayDate.minusDays(5);
                endOfWeek = todayDate.plusDays(1);
               // System.out.println(startOfWeek +"\t" + endOfWeek);
                break;
            case SATURDAY:
                startOfWeek = todayDate.minusDays(6);
                endOfWeek = todayDate;
               // System.out.println(startOfWeek +"\t" + endOfWeek);
                break;            
        }
        weekAppts.clear();
        for(Appointment appt: allUserAppts){
            // find week's appointments
            LocalDate apptDay = appt.getStart().toLocalDate();
            if( apptDay.equals(startOfWeek)|| apptDay.equals(endOfWeek) || (apptDay.isAfter(startOfWeek) && apptDay.isBefore(endOfWeek))){
                weekAppts.add(appt);
            }
        }
        tblAppt.setItems(weekAppts);
    }
    @FXML
    private void handleBtnMonth(){
        rbSelected = rbOption.MONTH;
        monthAppts.clear();
        Month thisMonth = LocalDate.now().getMonth();
        for(Appointment appt: allUserAppts){
            if(appt.getStart().getMonth().equals(thisMonth)){
                monthAppts.add(appt);
            }            
        }
        tblAppt.setItems(monthAppts);          
    }
    
    
     @FXML
    private void handleReport1(){
        System.out.println("btn");        
    }
    @FXML
    private void handleReport2(){
        System.out.println("btn");
        
    }
    @FXML
    private void handleReport3(){
        AppointmentDaoImpl.getAppointmentDaoImpl().readAppointmentsGroupByUser();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  System.out.println("main ctrl");
      
        tblCustId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        tblCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblCustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustActive.setCellValueFactory(new PropertyValueFactory<>("active"));
        tblCust.setItems(CustomerDaoImpl.getCustomerDaoImpl().getAllCustomers());
        
        tblApptId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        tblApptCust.setCellValueFactory(new PropertyValueFactory<>("customer"));
        tblApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tblApptLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        tblApptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        allUserAppts = AppointmentDaoImpl.getAppointmentDaoImpl().getUserAppointments();
        tblAppt.setItems(allUserAppts);   
        
        lblTitle.setText("Hello " + UserDaoImpl.getUserDaoImpl().getCurrentUser().getUserName());
        rbSelected = rbOption.ALL;
        btnAll.setSelected(true);
        
    }    
    
}
