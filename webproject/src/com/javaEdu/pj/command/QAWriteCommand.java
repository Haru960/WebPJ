package com.javaEdu.pj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.QADao;

public class QAWriteCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String ID = request.getParameter("id");
		String qTitle = request.getParameter("qTitle");
		String qContent = request.getParameter("qContent");
		
		QADao Qdao = new QADao();
		Qdao.write(ID, qTitle, qContent);
	}

}
