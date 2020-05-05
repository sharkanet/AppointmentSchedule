/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.utility;


import static c195appointmentschedule.alerts.SQLAlerts.sqlConnectionError;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;


/**
 *
 * @author Chris
 */
public class DBConn {
    private static Connection conn = null;
    

    
    public static MysqlDataSource getMySqlDataSource() throws IOException{
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
//        try{
            fis = new FileInputStream("src/c195appointmentschedule/utility/db.properties");
            
            props.load(fis);
            mysqlDS= new MysqlDataSource();
          // mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));  
           mysqlDS.setServerName(props.getProperty("MYSQL_DB_SERVER"));
           mysqlDS.setDatabaseName(props.getProperty("MYSQL_DB_DATABASE"));
           mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));         
           mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD")); 
         
           
                    
            
//        }catch (IOException e){
//            e.printStackTrace();            
//        }
        return mysqlDS;        
    }
    
    public static Connection startConnection(){
        
        try{
            conn = getMySqlDataSource().getConnection();            
            System.out.println("Connection Successful");
        } catch(IOException e){
            e.printStackTrace();
        } catch (SQLException e){
            sqlConnectionError();
        }        
        return conn;
    }
    
    public static Connection getConn(){
        return conn;
    }
    
   
}
