package com.javaEdu.pj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


import com.javaEdu.pj.dto.BDto;

public class BDao {
	static final int numPage = 20;
	
	DataSource dataSource;
	
	public BDao() {
		// TODO Auto-generated constructor stub
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String ID, String bName, String bTitle, String bContent, int fId) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		System.out.println("write에 id는?" +ID);
		System.out.println("write에 name는?" +bName);
		try {
			connection = dataSource.getConnection();
			String query = "insert into food_board (bId, id, Name, bTitle, bContent, bHit, bGroup, bStep, bIndent, fId) values (food_board_seq.nextval, ?, ?, ?, ?, 0, food_board_seq.currval, 0, 0, ?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ID);
			preparedStatement.setString(2, bName);
			preparedStatement.setString(3, bTitle);
			preparedStatement.setString(4, bContent);
			preparedStatement.setInt(5, fId);
			int rn = preparedStatement.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<BDto> list(String pageNumber, String searchType, String search){
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		System.out.println(pageNumber +"asd");
		System.out.println(searchType +"asd");
		System.out.println(search +"asd");
		String query;
		if(searchType.equals("bTitle")) {//LIKE 왼쪽 ? 로 처리 시 검색기능 작동안됨
		query = "SELECT * FROM ( "
			    +"SELECT ROWNUM NUM, N.* FROM( "
			            +"SELECT * FROM FOOD_BOARD where bTitle like ? ORDER BY bGroup desc, bStep asc) N )"
			    +"WHERE NUM BETWEEN ? AND ?";	
		
//		query = "SELECT * "
//				+ "FROM food_board "
//				+ "WHERE bGroup > (SELECT MAX(bGroup) FROM food_board WHERE bTitle LIKE ? ) - ? "
//						+ "AND bGroup <= (SELECT MAX(bGroup) FROM food_board WHERE bTitle LIKE ?) - ? "
//								+ "AND bTitle LIKE ? ORDER BY bGroup desc, bStep asc";
		}else {
		query = "SELECT * FROM ( "
			    +"SELECT ROWNUM NUM, N.* FROM( "
			            +"SELECT * FROM FOOD_BOARD where id like ? ORDER BY bGroup desc, bStep asc) N )"
			    +"WHERE NUM BETWEEN ? AND ?";
//		query = "SELECT * "
//				+ "FROM food_board "
//				+ "WHERE bGroup > (SELECT MAX(bGroup) FROM food_board WHERE id LIKE ? ) - ? "
//						+ "AND bGroup <= (SELECT MAX(bGroup) FROM food_board WHERE id LIKE ?) - ? "
//								+ "AND id LIKE ? ORDER BY bGroup desc, bStep asc";
		}
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%"+search+"%");
			preparedStatement.setInt(2, ((Integer.parseInt(pageNumber) - 1) * numPage) + 1);
			preparedStatement.setInt(3, Integer.parseInt(pageNumber) * numPage);
			resultSet = preparedStatement.executeQuery();
			
			System.out.println(Integer.parseInt(pageNumber) * numPage + "뭐가 나왔길래 1개가 더나와?");
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String ID = resultSet.getString("id");
				String bName = resultSet.getString("Name");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate =  resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				int fId = resultSet.getInt("fId");
				
				BDto dto = new BDto(bId, ID, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, fId);
				dtos.add(dto);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
public ArrayList<BDto> modify_list(String pageNumber, String search, String id){
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		System.out.println(pageNumber +"     pn");
		System.out.println(search +"     title");
		System.out.println(id +"     id");
		String query;
		query = "SELECT * FROM ( "
			    +"SELECT ROWNUM NUM, N.* FROM( "
			            +"SELECT * FROM FOOD_BOARD where bTitle like ? AND id LIKE ? ORDER BY bGroup desc, bStep asc) N )"
			    +"WHERE NUM BETWEEN ? AND ?";
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%"+search+"%");
			preparedStatement.setString(2, id);
			preparedStatement.setInt(3, ((Integer.parseInt(pageNumber) - 1) * numPage) + 1);
			preparedStatement.setInt(4, Integer.parseInt(pageNumber) * numPage);
			resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String ID = resultSet.getString("id");
				String bName = resultSet.getString("Name");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate =  resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				int fId = resultSet.getInt("fId");
				
				BDto dto = new BDto(bId, ID, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, fId);
				dtos.add(dto);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return dtos;
	}
//	
//	public boolean nextpage(String pageNumber) {
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			
//			String query = "select * from food_board where bGroup >= ?";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(pageNumber) * numPage);
//			resultSet = preparedStatement.executeQuery();
//			
//			if(resultSet.next()) {
//				return true;
//			}
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return false;
//	}

	public int targetPage(String pageNumber, String search, String searchType, String id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query;
		System.out.println(pageNumber+ "이란 페이지 넘버가 나왓읍니다.");
		if(searchType.equals("all")) {//LIKE 왼쪽 ? 로 처리 시 검색기능 작동안됨
			query = "SELECT count(*) FROM ( "
				    +"SELECT ROWNUM NUM, N.* FROM( "
				            +"SELECT * FROM FOOD_BOARD where bTitle like ? AND id like ? ORDER BY bGroup desc, bStep asc) N )";
//			query = "SELECT COUNT(bGroup) FROM food_board WHERE bTitle LIKE ?";
		}else if(searchType.equals("title")){
			query = "SELECT count(*) FROM ( "
				    +"SELECT ROWNUM NUM, N.* FROM( "
				            +"SELECT * FROM FOOD_BOARD where bTitle like ? ORDER BY bGroup desc, bStep asc) N )";
//			query = "SELECT COUNT(bGroup) FROM food_board WHERE id LIKE ?";
		}else {
			query = "SELECT count(* FROM ( "
				    +"SELECT ROWNUM NUM, N.* FROM( "
				            +"SELECT * FROM FOOD_BOARD where id like ? ORDER BY bGroup desc, bStep asc) N )";
		}
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			if(searchType.equals("all")) {
				preparedStatement.setString(1, "%"+search+"%");
				preparedStatement.setString(2, id);
			}else {
				preparedStatement.setString(1, "%"+search+"%");
			}
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				System.out.println(resultSet.getInt(1) + "게시글 수 숫자가 나왓읍니다.");
				if(resultSet.getInt(1) <= numPage) {
					System.out.println("1");
					return 1;
				}else if(resultSet.getInt(1) % numPage >= 1){
					System.out.println("2");
					return resultSet.getInt(1) / numPage +1;
				}else {
					System.out.println("3");
					return resultSet.getInt(1) / numPage;
				}
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public BDto contentView(String strID) {
		
		upHit(strID);
		
		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from food_board where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strID));
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String id = resultSet.getString("id");
				String bName = resultSet.getString("Name");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate =  resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				int fId = resultSet.getInt("fId");
				
				System.out.println(bName +", " +bTitle);
				dto = new BDto(bId, id, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, fId);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	public void modify(String bId, String id, String bTitle, String bContent) {
		
		Connection connection= null;
		PreparedStatement preparedStatement = null;
		
		System.out.println("id 재대로 넘어왔나? "+id);
		System.out.println("bTitle 재대로 넘어왔나? "+bTitle);
		System.out.println("bContent "+bContent);
		
		try {
			connection = dataSource.getConnection();
			
			String query = "update food_board set id = ?, bTitle = ?, bContent = ? where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bId));
			int rn = preparedStatement.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public void delete(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String str = "삭제 된 글";
		try {
			connection = dataSource.getConnection();
			String query = "delete from food_board where bId = ?";
//			String query = "update food_board set bTitle = ? where bId = ?";
			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setString(1, str);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			int rn = preparedStatement.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
//	public BDto reply_view(String str) {
//		BDto dto = null;
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "select * from food_board where bId = ?";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(str));
//			resultSet = preparedStatement.executeQuery();
//			
//			if(resultSet.next()) {
//				int bId = resultSet.getInt("bId");
//				String id = resultSet.getString("id");
//				String bName = resultSet.getString("Name");
//				String bTitle = resultSet.getString("bTitle");
//				String bContent = resultSet.getString("bContent");
//				Timestamp bDate =  resultSet.getTimestamp("bDate");
//				int bHit = resultSet.getInt("bHit");
//				int bGroup = resultSet.getInt("bGroup");
//				int bStep = resultSet.getInt("bStep");
//				int bIndent = resultSet.getInt("bIndent");
//				int fId = resultSet.getInt("fId");
//				
//				dto = new BDto(bId, id, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, fId);
//			}
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return dto;
//	}
//	
//	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
//		replyShape(bGroup, bStep);
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "insert into food_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (food_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
//			preparedStatement = connection.prepareStatement(query);
//			
//			preparedStatement.setString(1, bName);
//			preparedStatement.setString(2, bTitle);
//			preparedStatement.setString(3, bContent);
//			preparedStatement.setInt(4, Integer.parseInt(bGroup));
//			preparedStatement.setInt(5, Integer.parseInt(bStep) + 1);
//			preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1);
//			
//			int rn = preparedStatement.executeUpdate();
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	private void replyShape (String strGroup, String strStep) {
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "update food_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(strGroup));
//			preparedStatement.setInt(2, Integer.parseInt(strStep));
//			
//			int rn = preparedStatement.executeUpdate();
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//	}
//	
	private void upHit(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update food_board set bHit = bHit + 1 where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);
			
			int rn = preparedStatement.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
