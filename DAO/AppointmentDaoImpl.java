/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.DAO;

import c195appointmentschedule.alerts.SQLAlerts;
import c195appointmentschedule.model.Appointment;
import c195appointmentschedule.model.Customer;
import c195appointmentschedule.model.User;
import c195appointmentschedule.utility.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Chris
 */
public class AppointmentDaoImpl {
    private int appointmentID;
    private int customerID;
    private int userID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;    
    private LocalDateTime start;;
    private LocalDateTime end;    
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdateBy; 
    
    
    private ObservableList<Appointment> allAppointments= FXCollections.observableArrayList();
    private ObservableList<Appointment> userAppointments= FXCollections.observableArrayList();
    
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private Appointment appointment;
    
    
    
    
     ///singleton
    private AppointmentDaoImpl(){
    //    readAllAppointments();
        readUserAppointments();
    }
    private static AppointmentDaoImpl ref;
    public static AppointmentDaoImpl getAppointmentDaoImpl(){
        if(ref == null)
            ref = new AppointmentDaoImpl();
        return ref;
    }
    ///
/*    
    ///execute appointment select query
    private void appointmentSelect(String query){
        try{
            DBQuery.setPStatement(query);
            rs = DBQuery.getPStatement().executeQuery();
            allAppointments.clear();
            while(rs.next()){
                appointmentID=rs.getInt("appointmentId");
                customerID = rs.getInt("customerId");
                userID = rs.getInt("userId");
                title= rs.getString("title");
                description = rs.getString("description");
                location = rs.getString("location");
                contact = rs.getString("contact");
                type = rs.getString("type");
                url = rs.getString("url");
                start = rs.getTimestamp("start").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                end = rs.getTimestamp("end").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                createDate = rs.getTimestamp("createDate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                createdBy = rs.getString("createdBy");
                lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                lastUpdateBy = rs.getString("lastUpdateBy");
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
*/
    
    /// get all appointments
    public void readAllAppointments(){
        String query;
        query = "SELECT * FROM appointment";
        
        try{
            DBQuery.setPStatement(query);
            ResultSet rs;
            rs = DBQuery.getPStatement().executeQuery();
            allAppointments.clear();
            while(rs.next()){
                appointmentID=rs.getInt("appointmentId");
                customerID = rs.getInt("customerId");
                userID = rs.getInt("userId");
                title= rs.getString("title");
                description = rs.getString("description");
                location = rs.getString("location");
                contact = rs.getString("contact");
                type = rs.getString("type");
                url = rs.getString("url");
                start = rs.getTimestamp("start").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                end = rs.getTimestamp("end").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                createDate = rs.getTimestamp("createDate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                createdBy = rs.getString("createdBy");
                lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                lastUpdateBy = rs.getString("lastUpdateBy");
                allAppointments.add(new Appointment(appointmentID,customerID, userID,title,description,location,contact,type,url,start,end,createDate,createdBy,lastUpdate,lastUpdateBy));
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }
       // appointmentSelect(query);
       //   printAllAppointments();
                   
    }
    public ObservableList<Appointment> getAllAppointments(){
       return allAppointments;  
    }
    public void printAllAppointments(){
        allAppointments.forEach(a->a.print());
    }
    
    
    /// get appointments
    // user's appointments
    public void readUserAppointments(){
        String query;
        query = "SELECT * FROM appointment WHERE userId=? ORDER BY start";
        
        try{
            DBQuery.setPStatement(query);
            PreparedStatement ps = DBQuery.getPStatement();
            ps.setInt(1, UserDaoImpl.getUserDaoImpl().getCurrentUser().getUserID());
            ResultSet rs;
            rs = ps.executeQuery();
            userAppointments.clear();
            while(rs.next()){
                appointmentID=rs.getInt("appointmentId");
                customerID = rs.getInt("customerId");
                userID = rs.getInt("userId");
                title= rs.getString("title");
                description = rs.getString("description");
                location = rs.getString("location");
                contact = rs.getString("contact");
                type = rs.getString("type");
                url = rs.getString("url");
                start = rs.getTimestamp("start").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                end = rs.getTimestamp("end").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                createDate = rs.getTimestamp("createDate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                createdBy = rs.getString("createdBy");
                lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                lastUpdateBy = rs.getString("lastUpdateBy");
                userAppointments.add(new Appointment(appointmentID,customerID, userID,title,description,location,contact,type,url,start,end,createDate,createdBy,lastUpdate,lastUpdateBy));
            }            
        } catch(SQLException e){
            e.printStackTrace();
        }        
       //   printAllAppointments();                       
    }
    public ObservableList<Appointment> getUserAppointments(){
        return userAppointments; 
    }
    
    
    /// get a appointment
    
    /// add appointment
    public boolean addAppointment(int custId, int userId, String title, String description, String location, String contact, String type, String url, LocalDateTime start, LocalDateTime end, String user1, String user2){
        String query ="INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,now(),?,?)";
        try{
            PreparedStatement ps = DBQuery.getPS(query);           
            ps.setInt(1,custId);
            ps.setInt(2, userId);           
            ps.setString(3,title);           
            ps.setString(4,description);            
            ps.setString(5,location);         
            ps.setString(6, contact);
            ps.setString(7, type); 
            ps.setString(8, url);
        //    ZonedDateTime zdt = start.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp tsStart = Timestamp.valueOf(start.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());      
            System.out.println(tsStart.toString());
            Timestamp tsEnd = Timestamp.valueOf(end.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());           
            ps.setTimestamp(9, tsStart);            
            ps.setTimestamp(10, tsEnd);            
            ps.setString(11, user1);        
            ps.setString(12, user2);            
            ps.executeUpdate();
        //    readAllAppointments();
            readUserAppointments();
            return true;
        }catch(SQLException e){
         //   e.printStackTrace();            
            SQLAlerts.sqlAddError("appointment");
        }
        return false;
    }
    
    /// modify appointment
    public boolean updateAppointment(int cId, int uId, String title, String desc, String loc, String contact, String type, String url, LocalDateTime start, LocalDateTime end, String lastUpdateBy, int aId){
        String query = "UPDATE appointment SET customerId = ?, userId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url =?, start = ?, end = ?, lastUpdateBy = ? WHERE appointmentId = ?";
        try{
            PreparedStatement ps = DBQuery.getPS(query);
            ps.setInt(1,cId);
            ps.setInt(2,uId);
            ps.setString(3,title);
            ps.setString(4, desc);
            ps.setString(5, loc);
            ps.setString(6, contact);
            ps.setString(7, type);
            ps.setString(8, url);
            Timestamp tsStart = Timestamp.valueOf(start.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());                  
            Timestamp tsEnd = Timestamp.valueOf(end.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());    
            ps.setTimestamp(9, tsStart);
            ps.setTimestamp(10, tsEnd);
            ps.setString(11, lastUpdateBy);
            ps.setInt(12,aId);
            ps.executeUpdate();
            readUserAppointments();
            return true;
        } catch (SQLException e){
            SQLAlerts.sqlUpdateError("appointment");
           // e.printStackTrace();
        }
        return false;
    }
    
    
    /// delete appointment
    public boolean deleteAppointment(Appointment appt){
        String query = "DELETE FROM appointment WHERE appointmentId = ?";
        try{
            PreparedStatement ps = DBQuery.getPS(query);
            ps.setInt(1,appt.getAppointmentID());
            ps.executeUpdate();
            readUserAppointments();            
            return true;
        }catch(SQLException e){
            SQLAlerts.sqlDeleteError("appointment");
        }
        return false;
    }
    
    public boolean readAppointmentsGroupByUser(){
        String query = "SELECT COUNT(appointmentId), userId FROM appointment GROUP BY userId ORDER BY start";
        try{
            PreparedStatement ps =DBQuery.getPS(query);
            ResultSet rs = ps.executeQuery();
            Map<Integer,User> userMap =UserDaoImpl.getUserDaoImpl().getUserMap();
            StringBuilder sb = new StringBuilder();
            while(rs.next()){
                sb.append(userMap.get(rs.getInt("userId"))).append(" has ").append(rs.getInt("COUNT(appointmentId)")).append(" appointments on record").append(System.getProperty("line.separator"));  
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("REPORT");
            alert.setHeaderText("Appointments By User");
            alert.setContentText(sb.toString());        
            alert.showAndWait();
        } catch(SQLException e){
            SQLAlerts.sqlReadError(query);
        }
        return false;
    }
    
    
}
