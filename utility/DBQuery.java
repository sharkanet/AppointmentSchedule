/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195appointmentschedule.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Chris
 */
public class DBQuery {
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static final Connection CONN = DBConn.getConn();
    private static int updated;
    public static void setPStatement(Connection conn, String sqlStatement) throws SQLException{
        ps=conn.prepareStatement(sqlStatement);
    }
    public static void setPStatement(String sqlStatement) throws SQLException{
        ps=CONN.prepareStatement(sqlStatement);
    }   
    public static PreparedStatement getPStatement(){
        return ps;
    }    
    
    
    
    
    public static PreparedStatement getPS(Connection conn, String sqlStatement) throws SQLException{
        return conn.prepareStatement(sqlStatement);
    }
     public static PreparedStatement getPS(String sqlStatement) throws SQLException{
        return CONN.prepareStatement(sqlStatement);
    }
    
    
}
