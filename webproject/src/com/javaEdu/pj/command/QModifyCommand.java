package com.javaEdu.pj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.QADao;

public class QModifyCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String qId = request.getParameter("qId");
		String id = request.getParameter("id");
		String qTitle = request.getParameter("qTitle");
		String qContent = request.getParameter("qContent");
		
		QADao dao = new QADao();
		dao.Q_modify(qId, id, qTitle, qContent);
	}

}
