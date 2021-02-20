package com.javaEdu.pj.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.QADao;
import com.javaEdu.pj.dto.QADto;

public class QAListCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String pageNumber = request.getParameter("PN");
		String searchType= request.getParameter("searchType");
		String search= request.getParameter("search");
		if(pageNumber == null) {
			pageNumber = "1";
		}
		if(searchType == null) {
			searchType="qTitle";
		}
		if(search == null) {
			search="";
		}
		if(searchType.equals("title")) {
			searchType = "qTitle";
		}
		else {
			searchType = "id";
		}
		QADao dao = new QADao();
		ArrayList<QADto> dtos = dao.list(pageNumber, searchType, search);
		request.setAttribute("list", dtos);
	}
	
}
