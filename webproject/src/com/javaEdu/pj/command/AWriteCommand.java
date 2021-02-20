package com.javaEdu.pj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.QADao;

public class AWriteCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int qId = Integer.valueOf(request.getParameter("qId"));
		String id = request.getParameter("answer_id");
		String aTitle = request.getParameter("aTitle");
		String aContent = request.getParameter("aContent");
		
		QADao qaDao = new QADao();
		qaDao.answer_write(qId ,id, aTitle, aContent); 
		int aId = qaDao.get_aid(qId);
		qaDao.update_aid(qId, aId);
	}

}
