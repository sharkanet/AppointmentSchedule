/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.model;

import c195appointmentschedule.DAO.CityDaoImpl;
import c195appointmentschedule.DAO.CountryDaoImpl;
import static java.sql.JDBCType.TIMESTAMP;
import static java.sql.Types.TIMESTAMP;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 *
 * @author Chris
 */
public class Address {
    private int addressID;
    private  String address1;
    private String address2;
    private int cityID;
    private String postalCode;
    private String phone;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdateBy;
//    private Map<Integer,String> cityIdToNameMap = CityDaoImpl.getCityDaoImpl().getCityIdToNameMap();
//    private Map<Integer,Integer> cityIdToCountryMap = CityDaoImpl.getCityDaoImpl().getCityIdToCountryMap();
//    private Map<Integer,String> countryIdMap = CountryDaoImpl.getCountryDaoImpl().getIdToNameMap();
    private Map<Integer,City> cityIdMap = CityDaoImpl.getCityDaoImpl().getCityMap();
    private City city;
    
    public Address(int addressID, String address, String address2, int cityID, String postalCode, String phone, ZonedDateTime createDate, String createdBy, ZonedDateTime lastUpdate, String lastUpdateBy){
        this.addressID= addressID;
        this.address1= address;
        this.address2=address2;
        this.cityID=cityID;
        this.postalCode=postalCode;
        this.phone=phone;
        this.createDate=createDate;
        this.createdBy=createdBy;
        this.lastUpdate=lastUpdate;
        this.lastUpdateBy=lastUpdateBy;
        city = cityIdMap.get(cityID);
    }
    
    public int getAddressID(){
        return addressID;
    }
    public String getAddress() {
        return address1;
    }
    public String getAddress2(){
        return address2;        
    } 
    public int getCityID() {
        return cityID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
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

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public void setAddress(String address) {
        this.address1 = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        System.out.println("Address ID: " + addressID + "  Address: " + address1 + "   LastUpdate: " + lastUpdate);
    }
    
//    public String toString(){
// ///// add mapping
//        if(address2.equals("")){
//            return(address1 + ", "+ cityIdToNameMap.get(cityID) + ", " + countryIdMap.get(cityIdToCountryMap.get(cityID)));
//        }else{
//        return(address1 + ", " + address2 + ", " +cityIdToNameMap.get(cityID) + ", " + countryIdMap.get(cityIdToCountryMap.get(cityID)));
//        }
//    }
    public String toString(){
        if(address2.equals("")){
            return(address1 +", " + city);
        }else{
            return(address1 +", " + address2 +", " +city);
        }
    }
    
}
