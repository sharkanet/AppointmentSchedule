/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.DAO;

import c195appointmentschedule.alerts.SQLAlerts;
import c195appointmentschedule.model.City;
import c195appointmentschedule.model.Country;
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
public class CityDaoImpl {
    private static String query;
    private static ResultSet rs;
    private static ObservableList<City> allCities = FXCollections.observableArrayList();
    private static City city;
    
    private static int cityID;
    private static String cityName;  
    private static int countryID;
    private static ZonedDateTime createDate;
    private static String createdBy;
    private static ZonedDateTime lastUpdate;
    private static String lastUpdateBy; 
    
    
    ///singleton
    private CityDaoImpl(){
        readAllCities();
    }
    private static CityDaoImpl ref;
    public static CityDaoImpl getCityDaoImpl(){
        if(ref == null)
            ref = new CityDaoImpl();
        return ref;
    }
    ///
    //city map
    private Map<Integer,City> idToCityMap = new HashMap<>();
    
///////////id-name map
//////////////////
//    private Map<Integer,String> cityIdToNameMap = new HashMap<>();
//    public Map<Integer,String> getCityIdToNameMap(){
//        return cityIdToNameMap;
//    }
///////////id-countryID map
//////////////////   
//    private Map<Integer,Integer> cityIdToCountryMap = new HashMap<>();
//    public Map<Integer,Integer> getCityIdToCountryMap(){
//        return cityIdToCountryMap;
//    }
//    //// make mapping
//    private void makeCityMap(){
//        query="SELECT * FROM city";
//        try{
//            DBQuery.setPStatement(query);
//            rs = DBQuery.getPStatement().executeQuery();
//            cityIdToNameMap.clear();
//            cityIdToCountryMap.clear();
//            while(rs.next()){
//                int id = rs.getInt("cityId");
//                int countryId = rs.getInt("countryId");
//                String cityId = rs.getString("city");
//                cityIdToNameMap.put(id, cityId);
//                cityIdToCountryMap.put(id, countryId);
//            }
//        } catch(SQLException e){
//            e.printStackTrace();
//        }
//    }
    
    /// select all cities    
    public void readAllCities(){
        query="SELECT * FROM city ORDER BY countryId";
        try{
            DBQuery.setPStatement(query);
            rs = DBQuery.getPStatement().executeQuery();
            allCities.clear();
            while(rs.next()){
                cityID = rs.getInt("cityId");
                cityName = rs.getString("city");
                countryID=rs.getInt("countryId");
                createDate = rs.getTimestamp("createDate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                createdBy = rs.getString("createdBy");
                lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                lastUpdateBy = rs.getString("lastUpdateBy");
                City newCity = new City(cityID,cityName,countryID,createDate,createdBy,lastUpdate,lastUpdateBy);
                allCities.add(newCity);
                idToCityMap.put(cityID, newCity);
            }
        } catch(SQLException e){
            SQLAlerts.sqlReadError("city");
        }
    }
    public ObservableList<City> getAllCities(){
        return allCities;
    }
    public Map<Integer,City> getCityMap(){
        return idToCityMap;
    }
    public void printAllCities(){
        allCities.forEach(c->c.print());
    }
    
    /// select a city
    
    /// add a city    
    public boolean addNewCity(String cityName, int countryId, String user1, String user2){
        String query = "INSERT INTO city(city,countryId, createDate, createdBy, lastUpdateBy) VALUES (?,?, now(),?,?)";
        try{
            PreparedStatement ps = DBQuery.getPS(query);
            ps.setString(1,cityName);
            ps.setInt(2, countryId);
            ps.setString(3, user1);
            ps.setString(4, user2);
            ps.executeUpdate();
            readAllCities();
            return true;
        }catch (SQLException e){
            SQLAlerts.sqlAddError("city");
        }
        return false;        
    }      
}
