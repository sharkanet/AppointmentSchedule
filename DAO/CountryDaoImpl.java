/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.DAO;

import c195appointmentschedule.alerts.SQLAlerts;
import c195appointmentschedule.model.Country;
import c195appointmentschedule.utility.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
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
public class CountryDaoImpl {
//    private static String query;
//    private static ResultSet rs;
private ObservableList<Country> allCountries = FXCollections.observableArrayList();
   // private static Country country;
    
//    private static int countryID;
//    private static String countryName;    
//    private static ZonedDateTime createDate;
//    private static String createdBy;
//    private static ZonedDateTime lastUpdate;
//    private static String lastUpdateBy; 
    
    ///singleton
    private CountryDaoImpl(){
        readAllCountries();
    }
    private static CountryDaoImpl ref;
    public static CountryDaoImpl getCountryDaoImpl(){
        if(ref == null)
            ref = new CountryDaoImpl();
        
        return ref;
    }
    ///
    
    /// country ID to name map
    private Map<Integer,Country> idToCountryMap = new HashMap<>();    
//    private void makeCountryMap(){
//        String query = "SELECT * FROM country";        
//        try{
//            DBQuery.setPStatement(query);
//            ResultSet rs = DBQuery.getPStatement().executeQuery();    
//            idToCountryMap.clear();
//            while(rs.next()){  
//                int id = rs.getInt("countryId");
//                String countryName = rs.getString("country");
//                idToCountryMap.put(id, countryName);
//            }
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//    }
    public Map<Integer,Country> getIdToCountryMap(){
        return idToCountryMap;        
    }
    
    
    
    /// select all countries
    public void readAllCountries(){
        String query = "SELECT * FROM country";        
        try{
            DBQuery.setPStatement(query);
            ResultSet rs = DBQuery.getPStatement().executeQuery();
            allCountries.clear();
            idToCountryMap.clear();
            while(rs.next()){                
                int countryID=rs.getInt("countryId");
                String countryName = rs.getString("country");
                ZonedDateTime createDate = rs.getTimestamp("createDate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());                
                String createdBy = rs.getString("createdBy");                
                ZonedDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                String lastUpdateBy = rs.getString("lastUpdateBy");
                Country newCountry = new Country(countryID,countryName,createDate,createdBy,lastUpdate,lastUpdateBy);
                allCountries.add(newCountry);
                idToCountryMap.put(countryID, newCountry);
            }
            
        }catch(SQLException e){
            SQLAlerts.sqlReadError("country");
        } 
       
    }
    public ObservableList<Country> getAllCountries(){
        return allCountries;
    }
    public Map<Integer,Country> getCountryMap(){
        return idToCountryMap;
    }
    public void printAllCountries(){
        allCountries.forEach(c->c.print());
    }
    
    /// select a country
    
    /// add country
    public boolean addCountry(String country, String createdBy, String lastUpdateBy){
        String query = "INSERT INTO country (country, createDate, createdBy, lastUpdateBy) VALUES(?,now(),?,?)";        
        try{            
            PreparedStatement ps = DBQuery.getPS(query);
            ps.setString(1,country);
            ps.setString(2, createdBy);
            ps.setString(3, lastUpdateBy);
//            Timestamp tsStart = Timestamp.valueOf(LocalDateTime.now());
//            ps.setString(1,country);
//            ps.setTimestamp(2,tsStart);
//            ps.setString(3, createdBy);
//            ps.setString(4, lastUpdateBy);
            ps.executeUpdate(); 
            readAllCountries();
            return true;
        }catch(SQLException e){
            SQLAlerts.sqlAddError("country");
        }
        
        return false;
    }
   
}
