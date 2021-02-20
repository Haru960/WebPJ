package com.javaEdu.pj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javaEdu.pj.dto.QADto;

public class QADao {
	
	static final int numPage = 20;
	
	DataSource dataSource;
	
	public QADao() {
		// TODO Auto-generated constructor stub
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String ID, String qTitle, String qContent) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "insert into FOOD_QUESTION (qId, id, qTitle, qContent, qHit) "
					+ "values (FOOD_QUESTION_SEQ.nextval, ?, ?, ?, 0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ID);
			preparedStatement.setString(2, qTitle);
			preparedStatement.setString(3, qContent);
			preparedStatement.executeUpdate();
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
	
public void answer_write(int qid, String ID, String aTitle, String aContent) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "insert into FOOD_ANSWER (aId, id, aContent, qid, aTitle) "
					+ "values (FOOD_ANSWER_SEQ.nextval, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ID);
			preparedStatement.setString(2, aContent);
			preparedStatement.setInt(3, qid);
			preparedStatement.setString(4, aTitle);
			preparedStatement.executeUpdate();
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

public int get_aid(int qid) {
	int aId = 0;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
		connection = dataSource.getConnection();
		String query = "SELECT aId FROM FOOD_ANSWER WHERE qId = ?";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, qid);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			aId = resultSet.getInt("aId");
		}
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
	return aId;
}
public void update_aid(int qId, int aId) {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	try {
		connection = dataSource.getConnection();
		String query = "update FOOD_QUESTION set aId = ? where qId = ?";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, aId);
		preparedStatement.setInt(2, qId);
		preparedStatement.executeUpdate();
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
	
	public ArrayList<QADto> list(String pageNumber, String searchType, String search){
		
		ArrayList<QADto> dtos = new ArrayList<QADto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		System.out.println(pageNumber +"asd");
		System.out.println(searchType +"asd");
		System.out.println(search +"asd");
		String query;
		if(searchType.equals("qTitle")) {//LIKE 왼쪽 ? 로 처리 시 검색기능 작동안됨
		query = "SELECT * FROM ( "
			    +"SELECT ROWNUM NUM, N.* FROM( "
			            +"SELECT * FROM FOOD_QUESTION where qTitle like ? ORDER BY qId desc) N )"
			    +"WHERE NUM BETWEEN ? AND ?";	
		}else {
		query = "SELECT * FROM ( "
			    +"SELECT ROWNUM NUM, N.* FROM( "
			            +"SELECT * FROM FOOD_QUESTION where id like ? ORDER BY qId desc) N )"
			    +"WHERE NUM BETWEEN ? AND ?";
		}
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%"+search+"%");
			preparedStatement.setInt(2, ((Integer.parseInt(pageNumber) - 1) * numPage) + 1);
			preparedStatement.setInt(3, Integer.parseInt(pageNumber) * numPage);
			resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("qId");
				String ID = resultSet.getString("id");
				String qTitle = resultSet.getString("qTitle");
				String qContent = resultSet.getString("qContent");
				Timestamp qDate =  resultSet.getTimestamp("qDate");
				int aId = resultSet.getInt("aId");
				int qHit = resultSet.getInt("qHit");
				
				QADto dto = new QADto(bId, ID, qTitle, qContent, qDate, aId, qHit);
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
	
public ArrayList<QADto> my_q_list(String pageNumber, String search, String id){ //내가쓴글
		
		ArrayList<QADto> dtos = new ArrayList<QADto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		System.out.println(pageNumber +"     pn");
		System.out.println(search +"     title");
		System.out.println(id +"     id");
		String query;
		query = "SELECT * FROM ( "
			    +"SELECT ROWNUM NUM, N.* FROM( "
			            +"SELECT * FROM FOOD_QUESTION where qTitle like ? AND id LIKE ? ORDER BY qId desc) N )"
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
				int qId = resultSet.getInt("qId");
				String ID = resultSet.getString("id");
				String qTitle = resultSet.getString("qTitle");
				String qContent = resultSet.getString("qContent");
				Timestamp qDate =  resultSet.getTimestamp("qDate");
				int aId = resultSet.getInt("aId");
				int qHit = resultSet.getInt("qHit");
				
				QADto dto = new QADto(qId, ID, qTitle, qContent, qDate, aId, qHit);
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
public ArrayList<QADto> my_a_list(String pageNumber, String search, String id){ //내가쓴글
	
	ArrayList<QADto> dtos = new ArrayList<QADto>();
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	System.out.println(pageNumber +"     pn");
	System.out.println(search +"     title");
	System.out.println(id +"     id");
	String query;
	query = "SELECT * FROM ( "
		    +"SELECT ROWNUM NUM, N.* FROM( "
		            +"SELECT * FROM FOOD_QUESTION where qTitle like ? AND id LIKE ? ORDER BY qId desc) N )"
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
			String qTitle = resultSet.getString("qTitle");
			String qContent = resultSet.getString("qContent");
			Timestamp qDate =  resultSet.getTimestamp("qDate");
			int qHit = resultSet.getInt("qHit");
			int bGroup = resultSet.getInt("bGroup");
			int bStep = resultSet.getInt("bStep");
			int bIndent = resultSet.getInt("bIndent");
			
			QADto dto = new QADto(bId, ID, bName, qTitle, qContent, qDate, qHit, bGroup, bStep, bIndent);
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
//			String query = "select * from FOOD_QUESTION where bGroup >= ?";
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
				            +"SELECT * FROM FOOD_QUESTION where qTitle like ? AND id like ? ORDER BY qid desc) N )";
//			query = "SELECT COUNT(bGroup) FROM FOOD_QUESTION WHERE qTitle LIKE ?";
		}else if(searchType.equals("title")){
			query = "SELECT count(*) FROM ( "
				    +"SELECT ROWNUM NUM, N.* FROM( "
				            +"SELECT * FROM FOOD_QUESTION where qTitle like ? ORDER BY qid desc) N )";
//			query = "SELECT COUNT(bGroup) FROM FOOD_QUESTION WHERE id LIKE ?";
		}else {
			query = "SELECT count(* FROM ( "
				    +"SELECT ROWNUM NUM, N.* FROM( "
				            +"SELECT * FROM FOOD_QUESTION where id like ? ORDER BY qid desc) N )";
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
	
	public QADto contentView(String strID) {
		
		upHit(strID);
		
		QADto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from FOOD_QUESTION where qId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strID));
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int qId = resultSet.getInt("qId");
				String id = resultSet.getString("id");
				String qTitle = resultSet.getString("qTitle");
				String qContent = resultSet.getString("qContent");
				Timestamp qDate =  resultSet.getTimestamp("qDate");
				int aId = resultSet.getInt("aId");
				int qHit = resultSet.getInt("qHit");
				System.out.println(id +"id 값이 왜 오류가걸릴까?");
				dto = new QADto(qId, id, qTitle, qContent, qDate, aId, qHit);
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
	
	public void Q_modify(String qId, String id, String qTitle, String qContent) {
		
		Connection connection= null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "update FOOD_QUESTION set id = ?, qTitle = ?, qContent = ? where qId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, qTitle);
			preparedStatement.setString(3, qContent);
			preparedStatement.setInt(4, Integer.parseInt(qId));
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
	
	public void delete(String qId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			String query = "delete from FOOD_QUESTION where qId = ?";
//			String query = "update FOOD_QUESTION set qTitle = ? where bId = ?";
			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setString(1, str);
			preparedStatement.setInt(1, Integer.parseInt(qId));
			preparedStatement.executeUpdate();
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
	
	public QADto reply_view(int aId) {
		QADto dto = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "select * from FOOD_ANSWER where aId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, aId);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int qId = resultSet.getInt("qId");
				String id = resultSet.getString("id");
				String aTitle = resultSet.getString("aTitle");
				String aContent = resultSet.getString("aContent");
				Timestamp aDate =  resultSet.getTimestamp("aDate");
				
				dto = new QADto(qId, id, aTitle, aContent, aDate);
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
	
	private void upHit(String qId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update FOOD_QUESTION set qHit = qHit + 1 where qId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, qId);
			
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
