package com.yuhan.chat.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;

public class ChatDto {
	ArrayList<ChatVo> chatVoList;

	public ArrayList<ChatVo> select() {
		Connection con = ChatConnector.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		chatVoList = new ArrayList<ChatVo>();

		try {
			String sql = "SELECT * FROM user;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ChatVo vo = new ChatVo();
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
				chatVoList.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return chatVoList;
	}
	
	public ArrayList<String> select_name() {
		Connection con = ChatConnector.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> arrayList = new ArrayList<>();
		int i = 0;

		try {
			String sql = "SELECT name FROM user;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				arrayList.add(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}

	public ArrayList<ChatVo> select_id_name() {
		Connection con = ChatConnector.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		chatVoList = new ArrayList<ChatVo>();

		try {
			String sql = "SELECT id, name FROM user;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ChatVo vo = new ChatVo();
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				chatVoList.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return chatVoList;
	}

	public void insert(ChatVo vo) {
		Connection con = ChatConnector.getCon();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO user VALUES(?, ?, ?);";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(ChatVo vo) {
		Connection con = ChatConnector.getCon();
		PreparedStatement pstmt = null;
		String sql = "UPDATE user SET name=?;";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(ChatVo vo) {
		Connection con = ChatConnector.getCon();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM user WHERE id=?;";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void backup() throws FileNotFoundException {
		Connection con = ChatConnector.getCon();

		try {
			File file = new File("C:\\backup.txt");
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM user;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.print(rs.getString("id") + "    ");
				System.out.print(rs.getString("pw") + "    ");
				System.out.println(rs.getString("name"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean exists(String uid) throws NamingException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT id FROM user WHERE id = ?";

			con = ChatConnector.getCon();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uid);

			rs = pstmt.executeQuery();

			return rs.next();
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}
	
	public String getName(String uid) throws NamingException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT name FROM user WHERE id = ?";
			String str = "";

			con = ChatConnector.getCon();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uid);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				str = rs.getString(1);
			}
			return str;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}

	
	
	public int login(String uid, String upass) throws NamingException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT id, pw FROM user WHERE id = ?";

			con = ChatConnector.getCon();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uid);

			rs = pstmt.executeQuery();

			if (!rs.next())
				return 1;
			if (!upass.equals(rs.getString("pw")))
				return 2;

			return 0;

		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}

}
