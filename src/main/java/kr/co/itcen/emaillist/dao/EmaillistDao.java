package kr.co.itcen.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.emaillist.vo.EmaillistVo;

public class EmaillistDao {
	
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");// 2. 연결하기
			String url = "jdbc:mariadb://192.168.1.61:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver: " + e);
		} 
		return connection;
	}

	public void insert(EmaillistVo vo) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String sql = "insert into emaillist values(null, ?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getLastName());
			pstmt.setString(2, vo.getFirstName());
			pstmt.setString(3, vo.getEmail());
			pstmt.executeUpdate();				
			
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select last_insert_id()");			
			if(rs.next()) {
				Long no = rs.getLong(1);
				vo.setNo(no);
			}
						
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	public void delete() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();

			String sql = "delete from emaillist";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();			

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	public List<EmaillistVo> getList() {
		List<EmaillistVo> list = new ArrayList<EmaillistVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			String sql = "select no, first_name, last_name, email from emaillist order by no desc";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				
				EmaillistVo vo = new EmaillistVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	

}
