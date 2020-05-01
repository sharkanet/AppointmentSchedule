/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.DAO;

import c195appointmentschedule.alerts.SQLAlerts;
import c195appointmentschedule.model.Address;
import c195appointmentschedule.model.Customer;
import c195appointmentschedule.utility.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Chris
 */
public class CustomerDaoImpl {
    private static String query;
    private static ResultSet rs;
    
    private static Customer customer;
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Customer> customers =FXCollections.observableArrayList();
    
    private static int customerID;
    private static String customerName;
    private static int addressID;
    private static boolean active;    
    private static ZonedDateTime createDate;
    private static String createdBy;
    private static ZonedDateTime lastUpdate;
    private static String lastUpdateBy; 
    
    private Map<Integer,Customer> idToCustomerMap = new HashMap<>();
    
    ///singleton
    private CustomerDaoImpl(){
        readAllCustomers();
    }
    private static CustomerDaoImpl ref;
    public static CustomerDaoImpl getCustomerDaoImpl(){
        if(ref == null)
            ref = new CustomerDaoImpl();
        return ref;
    }
    ///
     
    
    /// get all customers
     private void readAllCustomers(){ 
        query = "SELECT * FROM customer";
        try{
            DBQuery.setPStatement(query);
            rs=DBQuery.getPStatement().executeQuery();
            allCustomers.clear();
            while(rs.next()){
                customerID = rs.getInt("customerId");
                customerName = rs.getString("customerName");
                addressID=  rs.getInt("addressId");
                active = rs.getBoolean("active");
                createDate = rs.getTimestamp("createDate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                createdBy = rs.getString("createdBy");
                lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                lastUpdateBy = rs.getString("lastUpdateBy");
                Customer newCustomer = new Customer(customerID, customerName, addressID, active, createDate, createdBy, lastUpdate, lastUpdateBy);
                allCustomers.add(newCustomer);
                idToCustomerMap.put(customerID, newCustomer);
            }
        } catch (SQLException e){
            SQLAlerts.sqlReadError("customer");
        }
       // printAllCustomers();
    }   
     public ObservableList<Customer> getAllCustomers(){
         return allCustomers;
     }
     public Map<Integer,Customer> getCustomerMap(){
         return idToCustomerMap;
     }
     
     public void printAllCustomers(){
         allCustomers.forEach(u -> u.print() );
     }
     
    /// get subset of customers
    
    /// get a customer 
    
    /// add a customer
     public boolean addCustomer(String custName, int addressId, boolean active, String user1, String user2){
         query = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES (?,?,?, now(), ?,?)";
         try{
             PreparedStatement ps = DBQuery.getPS(query);
             ps.setString(1, custName);
             ps.setInt(2, addressId);
             ps.setBoolean(3, active);
             ps.setString(4, user1);
             ps.setString(5, user2);
             ps.executeUpdate();
             readAllCustomers();
             return true;
         } catch (SQLException e){
             SQLAlerts.sqlAddError("customer");
         }
         return false;
     }
    
    /// delete a customer
    public boolean deleteCustomer(int custId){
        String query1 = "DELETE FROM customer WHERE customerId = ?";
        String query2 = "DELETE FROM appointment WHERE customerId = ?";
        try{
            PreparedStatement ps2 = DBQuery.getPS(query2);
            PreparedStatement ps1 = DBQuery.getPS(query1);
            ps2.setInt(1, custId);
            ps1.setInt(1, custId);
            ps2.executeUpdate();
            ps1.executeUpdate();
            readAllCustomers();
            AppointmentDaoImpl apptDao = AppointmentDaoImpl.getAppointmentDaoImpl();
            apptDao.readUserAppointments();
            apptDao.readAllAppointments();            
            return true;
        } catch(SQLException e){
            SQLAlerts.sqlDeleteError("customer");
        }
        return false;
    }
     
     
    /// update a customer
     public boolean updateCustomer(String custName, int addressId, boolean active, String user2, int custId){
         query = "UPDATE customer SET customerName = ?, addressId = ?, active = ?, lastUpdateBy =? WHERE customerId=?";
         try{
             PreparedStatement ps = DBQuery.getPS(query);
             ps.setString(1, custName);
             ps.setInt(2, addressId);
             ps.setBoolean(3, active);
             ps.setString(4, user2);
             ps.setInt(5, custId);
             ps.executeUpdate();
             readAllCustomers();
             return true;              
         } catch (SQLException e){
             SQLAlerts.sqlUpdateError("customer");
         }
         return false;
     }
}
