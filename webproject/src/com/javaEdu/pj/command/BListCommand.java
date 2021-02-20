 package com.javaEdu.pj.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.BDao;
import com.javaEdu.pj.dto.BDto;

public class BListCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String pageNumber = request.getParameter("PN");
		String searchType= request.getParameter("searchType");
		String search= request.getParameter("search");
//		String targetPage = request.getParameter("targetPage");
		if(pageNumber == null) {
			pageNumber = "1";
		}
		if(searchType == null) {
			searchType="title";
		}
		if(search == null) {
			search="";
		}
//		if(targetPage ==null) {
//			targetPage = "1";
//		}
		if(searchType.equals("title")) {
			searchType = "bTitle";
		}
		else {
			searchType = "id";
		}
//		search = "%" +search +"%";
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list(pageNumber, searchType, search);
//		targetPage = String.valueOf(dao.targetPage(pageNumber, search, searchType));
		request.setAttribute("list", dtos);
//		request.setAttribute("targetPage", targetPage);
	}
	
}
