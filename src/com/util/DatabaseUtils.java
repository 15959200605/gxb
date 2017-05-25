package com.util;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
  
/** 
 * 连接数据库的工具类,被定义成不可继承且是私有访问 
 * @author LHC 
 * @since 2015-11-17 
 */  
public final class DatabaseUtils {  
    private static String url = "jdbc:mysql://localhost:3306/gxb";  
    private static String user = "root";  
    private static String psw = "123";  
      
    private static  Connection conn;  
      
    static {  
        try {  
            Class.forName("com.mysql.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
      
    private DatabaseUtils() {  
          
    }  
      
    /** 
     * 获取数据库的连接 
     * @return conn 
     */  
    public static Connection getConnection() {  
        if(null == conn) {  
            try {  
                conn = DriverManager.getConnection(url, user, psw);  
            } catch (SQLException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            }  
        }  
        return conn;  
    }  
      
    /** 
     * 释放资源 
     * @param conn 
     * @param pstmt 
     * @param rs 
     */  
    public static void closeResources(Connection conn,PreparedStatement pstmt,ResultSet rs) {  
        if(null != rs) {  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally {  
                if(null != pstmt) {  
                    try {  
                        pstmt.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                        throw new RuntimeException(e);  
                    } finally {  
                        if(null != conn) {  
                            try {  
                                conn.close();  
                            } catch (SQLException e) {  
                                e.printStackTrace();  
                                throw new RuntimeException(e);  
                            }  
                        }  
                    }  
                }  
            }  
        }  
    }  
} 
