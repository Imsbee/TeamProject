package com.yuhan.chat.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ChatConnector {
	private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/chat?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	private static final String ID = "root";
	private static final String PW = "1234";
	private static Connection con;
	
	public static Connection getCon() {
		try {
			Class.forName(DRIVER_PATH);
			//System.out.println("정상적으로 DB가 LOAD 되었습니다.");
			con = DriverManager.getConnection(URL, ID, PW);
			//System.out.println("연결이 잘 되었습니다.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void main(String[] args) {
		getCon();
	}
	
}
