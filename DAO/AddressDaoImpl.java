/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.DAO;

import c195appointmentschedule.alerts.SQLAlerts;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import c195appointmentschedule.model.Address;
import c195appointmentschedule.utility.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;

/**
 *
 * @author Chris
 */
public class AddressDaoImpl {
    private static String query;
    private static ResultSet rs;
    private static ObservableList<Address> allAddresses = FXCollections.observableArrayList();
    private Map<Integer,Address> idToAddressMap = new HashMap<>();
    private static Address address;
    
    private static int addressID;
    private static String address1;
    private static String address2;
    private static int cityID;
    private static String postalCode;
    private static String phone;
    private static ZonedDateTime createDate;
    private static String createdBy;
    private static ZonedDateTime lastUpdate;
    private static String lastUpdateBy;
    
    ///singleton
    private AddressDaoImpl(){
        readAllAddresses();
    }
    private static AddressDaoImpl ref;
    public static AddressDaoImpl getAddressDaoImpl(){
        if(ref == null)
            ref = new AddressDaoImpl();
        return ref;
    }
    ///
    
    
    /// select all address from database
    private ObservableList<Address> readAllAddresses(){
        query="SELECT * FROM address";
        try{
            DBQuery.setPStatement(query);
            rs = DBQuery.getPStatement().executeQuery();
            allAddresses.clear();
            idToAddressMap.clear();
            while(rs.next()){
                addressID=rs.getInt("addressId");
                address1 = rs.getString("address");
                address2 = rs.getString("address2");
                cityID = rs.getInt("cityId");
                postalCode =rs.getString("postalCode");
                phone = rs.getString("phone");
                createDate = rs.getTimestamp("createDate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                createdBy = rs.getString("createdBy");
                lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                lastUpdateBy = rs.getString("lastUpdateBy");
                Address newAddress = new Address (addressID, address1, address2, cityID, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy);                
                allAddresses.add(newAddress);
                idToAddressMap.put(addressID, newAddress);
            }
        }catch(SQLException e){
            SQLAlerts.sqlReadError("address");
        }
      //  printAllAddresses();
        return allAddresses;
    }
    public ObservableList<Address> getAllAddresses(){
        return allAddresses;
    }
    public Map<Integer,Address> getAddressMap(){
        return idToAddressMap;
    }
    public void printAllAddresses(){
         allAddresses.forEach(a->a.print());
    }
    
    
    /// select a address
    
    /// add address
    public boolean addNewAddress(String address1, String address2, int cityID, String postCode, String phone, String user1, String user2){
        String query ="INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy)"
                + "VALUES(?,?,?,?,?, now(),?,?)";
        try{
            PreparedStatement ps= DBQuery.getPS(query);
            ps.setString(1, address1);
            ps.setString(2, address2);
            ps.setInt(3, cityID);
            ps.setString(4, postCode);
            ps.setString(5, phone);;
            ps.setString(6, user1);
            ps.setString(7, user2);
            ps.executeUpdate();
            readAllAddresses();
            return true;
        }catch(SQLException e){
            SQLAlerts.sqlAddError("address");
        }            
        return false;
    }
}
