package com.javaEdu.pj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.javaEdu.pj.dto.MemberDTO;
// 1. 싱글톤
// 2. 각 servlet에서 DB에 접근하던 부분은 여기에 함수로 구현해 대체해야한다.
public class MemberDAO {

	public static final int MEMBER_JOIN_SUCCESS = 1;
	public static final int MEMBER_JOIN_FAIL = 0;
	public static final int MEMBER_LOGIN_SUCCESS = 1;
	public static final int MEMBER_LOGIN_PW_NO_GOOD = 0;
	public static final int MEMBER_LOGIN_NOT = -1;
	public static final int MEMBER_NONEXISTENT = 0;
	public static final int MEMBER_EXISTENT = 1;
	public static final int MEMBER_MODIFY_SUCCESS = 1;
	public static final int MEMBER_MODIFY_FAIL = 0;
	
	
	private static MemberDAO instance = new MemberDAO();

	public MemberDAO() {
	}
	
	public static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}
	
	public int updateMember(MemberDTO dto) { //현제 로그인한 계정말고 다른계정도 다바뀌는 문제있음 ( name이 null로 오는게 문제 였음. 해결 )
		int ri = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		String query = "update food_member set uName = ?, eMail = ?, phone = ? where id = ?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			System.out.println(dto.getName() +"이름 뭐가나왔어?");
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.geteMail());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4, dto.getId());
			ri = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(connection != null) connection.close();
			}catch(Exception e) {}
		}
		
		return ri;
	}
	
	public int userLoginCheck(String id, String pw) {
		int ri = 0;
		String dbPw;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet set = null;
		System.out.println(id);
		String query = "select upassword from food_member where id = ?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			set = pstmt.executeQuery();
			
			if(set.next()) {
				dbPw = set.getString("upassword");
				if(dbPw.equals(pw)) {
					ri = MemberDAO.MEMBER_LOGIN_SUCCESS;
				}
				else {
					ri = MemberDAO.MEMBER_LOGIN_PW_NO_GOOD;
				}
			}
			else {
				ri = MemberDAO.MEMBER_LOGIN_NOT;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(set != null)	set.close();
				if(pstmt != null) pstmt.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				
			}
		}
		
		return ri;
	}
	
	public int insertMember(MemberDTO dto) {
		int ri = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		String query = "insert into food_member values(?,?,?,?,?,?,null,null)";
		
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.geteMail());
			pstmt.setTimestamp(5, dto.getrDate());
			pstmt.setString(6, dto.getPhone());
			int i = pstmt.executeUpdate();
			if(i == 1) {
				ri = MemberDAO.MEMBER_JOIN_SUCCESS;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(connection != null) connection.close();
			}catch(Exception e) {}
		}
		
		
		
		return ri;
	}
	
	public MemberDTO getMember (String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet set = null;
		String query = "select * from food_member where id = ?";
		MemberDTO dto = null;
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			set = pstmt.executeQuery();
			
			if(set.next()) {
				dto = new MemberDTO();
				dto.setId(set.getString("id"));
				dto.setPw(set.getString("upassword"));
				dto.setName(set.getString("uName"));
				dto.seteMail(set.getString("eMail"));
				dto.setrDate(set.getTimestamp("rDate"));
				dto.setPhone(set.getString("phone"));
				dto.setIsAdmin(set.getInt("ISADMIN"));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				connection.close();
			}catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
		return dto;
	}
	

//	public ArrayList<MemberDTO> food_memberelect(){
//		ArrayList<MemberDTO> dtos = new ArrayList<MemberDTO>();
//
//		Connection con = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			con = getConnection();
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("select * from food_member");
//
//			while (rs.next()) {
//				String name = rs.getString("name");
//				String id = rs.getString("id");
//				String pw = rs.getString("pw");
//				String phone1 = rs.getString("phone1");
//				String phone2 = rs.getString("phone2");
//				String phone3 = rs.getString("phone3");
//				String gender = rs.getString("gender");
//
//				MemberDTO dto = new MemberDTO(name, id, pw, phone1, phone2, phone3, gender);
//				dtos.add(dto);
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(rs != null) rs.close();
//				if(stmt != null) stmt.close();
//				if(con != null) con.close();
//			}catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//
//		return dtos;
//	}
	
	public int confirmId(String id) {
		int ri = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet set = null;
		String query = "select id from food_member where id = ?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			set = pstmt.executeQuery();
			if(set.next()) {
				ri = MemberDAO.MEMBER_EXISTENT;
			}
			else {
				ri = MemberDAO.MEMBER_NONEXISTENT;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return ri;
	}
	
	private Connection getConnection() {
		Context context = null;
		DataSource dataSource = null;
		Connection connection = null;
		try {
			context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
			connection = dataSource.getConnection();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return connection;
	}

}
