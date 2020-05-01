/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.model;

import c195appointmentschedule.DAO.CountryDaoImpl;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 *
 * @author Chris
 */
public class City {
    private int cityID;
    private String city;
    private int countryID;
    
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdateBy; 
    CountryDaoImpl countryDao = CountryDaoImpl.getCountryDaoImpl();
     Map<Integer,Country> countryIdMap = countryDao.getCountryMap();
    private Country country;

    public City(int cityID, String city, int countryID, ZonedDateTime createDate, String createdBy, ZonedDateTime lastUpdate, String lastUpdateBy) {
        this.cityID = cityID;
        this.city = city;
        this.countryID = countryID;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        country = countryIdMap.get(countryID);
    }

    public int getCityID() {
        return cityID;
    }

    public String getCity() {
        return city;
    }

    public int getCountryID() {
        return countryID;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public void print(){
        System.out.println("City ID: " + cityID + "  City name: " + city + "   LastUpdate: " + lastUpdate);
    }
    
    
    public String toString(){    
        // map to display country name   
        return(city+", "+ country);
    }
}
