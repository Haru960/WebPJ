package com.javaEdu.pj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.BDao;
import com.javaEdu.pj.dto.BDto;

public class BReplyViewCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String bId = request.getParameter("bId");
		BDao dao = new BDao();
		BDto dto = dao.reply_view(bId) ;
		
		request.setAttribute("reply_view", dto);
	}
	
}
