/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.model;

import c195appointmentschedule.DAO.AddressDaoImpl;
import c195appointmentschedule.DAO.CityDaoImpl;
import c195appointmentschedule.DAO.CountryDaoImpl;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 *
 * @author Chris
 */
public class Customer {
     private int customerID;
    private String customerName;
    private int addressID;
    private boolean active;
    
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdateBy; 
    
//    private Map<Integer,String> cityIdToNameMap = CityDaoImpl.getCityDaoImpl().getCityIdToNameMap();
//    private Map<Integer,Integer> cityIdToCountryMap = CityDaoImpl.getCityDaoImpl().getCityIdToCountryMap();
//    private Map<Integer,String> countryIdMap = CountryDaoImpl.getCountryDaoImpl().getIdToNameMap();
    private Map<Integer,Address> addressMap = AddressDaoImpl.getAddressDaoImpl().getAddressMap();
    private Address address;
 //   private String addressName;
    
    
    public Customer(int Id, String Name, int addressID, boolean active, ZonedDateTime createDate, String createdBy, ZonedDateTime lastUpdate, String lastUpdateBy) {
        this.customerID = Id;
        this.customerName = Name;
        this.addressID = addressID;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.address = addressMap.get(addressID);
       // this.addressName = this.address.toString();
       //System.out.println(customerName);
    }
    public Address getAddress(){
        return address;
    }
//    public String getAddressName(){
//        return addressName;
//    }
    public int getCustomerID() {
        return customerID;
    }
    

    public String getCustomerName() {
        return customerName;
    }

    public int getAddressID() {
        return addressID;
    }

    public boolean getActive() {
        return active;
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

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        System.out.println("Customer ID: " + customerID + "  Customer name: " + customerName + "   LastUpdate: " + lastUpdate +"  Active: " + active);
    }
    
    public String toString(){
        return("ID: " + customerID+",\tName: "+customerName);
    }
}
