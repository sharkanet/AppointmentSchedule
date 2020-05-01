/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.DAO;

import c195appointmentschedule.model.User;
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
public class UserDaoImpl{
    private static User user;
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static String query;    
    private static ResultSet rs;
    private Map<Integer,User> idToUserMap = new HashMap<>();
    
    private static int userID;
    private static String userName;
    private static String password;
    private static boolean active;
    private static ZonedDateTime createDate;
    private static String createdBy;
    private static ZonedDateTime lastUpdate;
    private static String lastUpdateBy; 
    private User currentUser;
    
    ///singleton
    private UserDaoImpl(){
        readAllUsers();
    }
    private static UserDaoImpl ref;
    public static UserDaoImpl getUserDaoImpl(){
        if(ref == null)
            ref = new UserDaoImpl();
        return ref;
    }
    ///
    
    
    
/// function to get list of all users
    public void readAllUsers(){ 
        query = "SELECT * FROM user";
        try{
            DBQuery.setPStatement(query);
            rs=DBQuery.getPStatement().executeQuery();
            users.clear();
            while(rs.next()){
                userID = rs.getInt("userId");
                userName = rs.getString("userName");
                password=  rs.getString("password");
                active = rs.getBoolean("active");
                createDate = rs.getTimestamp("createDate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                createdBy = rs.getString("createdBy");
                lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                lastUpdateBy = rs.getString("lastUpdateBy");
                User newUser = new User(userID, userName, password, active, createDate, createdBy, lastUpdate, lastUpdateBy);
                users.add(newUser);
                idToUserMap.put(userID, newUser);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        //printAllUsers();        
    }   
    public Map<Integer,User> getUserMap(){
        return idToUserMap;
    }
    public ObservableList<User> getAllUsers(){ 
        return users;
    }
    
    public void printAllUsers(){
        users.forEach(u -> u.print() );
    }
    
/// function to verify login?
    public void verifyUser(){
    }
    
/// method to get a user matching some parameter ?   
    public void getUser(){
    }
    
    //set current user at login
    public void setCurrentUser(User u){
        currentUser = u;
    }
    public User getCurrentUser(){
        return currentUser;
    }
    
    
    
}
