package com.javaEdu.pj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javaEdu.pj.dto.FDto;

public class FDao {
	
	DataSource dataSource;
	public FDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int upload(String fileNmae, String fileRealName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String query = "INSERT INTO FOOD_BFILE VALUES(food_file_seq.nextval, ?, ?)";
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query, new String[] {"fId"});
			preparedStatement.setString(1, fileNmae);
			preparedStatement.setString(2, fileRealName);
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			int rn = -1;
			while(rs.next()) {
				 rn= rs.getInt(1);
			}
			
			return rn;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				if(rs != null) rs.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return -1;
	}
	
	public FDto getFileName(int fId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		FDto fdto = null;
		try {
			connection = dataSource.getConnection();
			String query = "SELECT * FROM FOOD_BFILE WHERE fId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, fId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				String fileName = resultSet.getString("FILENAME");
				String fileRealName = resultSet.getString("FILEREALNAME");
				fdto = new FDto();
				fdto.setFileName(fileName);
				fdto.setFileRealName(fileRealName);
				System.out.println("name 잘들어왔나? : " +fileName);
				System.out.println("fileRealName 잘들어왔나? : " +fileRealName);
			}
			return fdto;
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
		
		
		return null;
	}

}
