package cn.wqydj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	static{
		try {
			Class.forName(PropertiesUtil.getValue("driverclassname"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConn(){
		Connection conn = null;
		try {
			return DriverManager.getConnection(PropertiesUtil.getValue("url"), PropertiesUtil.getValue("user"), PropertiesUtil.getValue("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(DbUtil.getConn());
	}
}
