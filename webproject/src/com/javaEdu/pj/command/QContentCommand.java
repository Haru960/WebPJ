package com.javaEdu.pj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.QADao;
import com.javaEdu.pj.dto.QADto;

public class QContentCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String qId = request.getParameter("qId");
		QADao dao = new QADao();
		QADto qdto = dao.contentView(qId);
		
		
		if(qdto.getaId() >= 1) {
			QADto adto = dao.reply_view(qdto.getaId());
			request.setAttribute("reply_view", adto);
		}
		
		
		
		request.setAttribute("content_view", qdto);
	}
}
