package com.javaEdu.pj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.BDao;
import com.javaEdu.pj.dao.FDao;
import com.javaEdu.pj.dto.BDto;
import com.javaEdu.pj.dto.FDto;

public class BContentCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String bId = request.getParameter("bId");
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId);
		FDto fDto = null;
		System.out.println(dto.getfId());
		if(dto.getfId() != -1) {
			FDao fDao = new FDao();
			System.out.println("==============================");
			fDto = fDao.getFileName(dto.getfId());
		}
		System.out.println("==============================file 잘넘어왔는가?" );
		request.setAttribute("content_view", dto);
		request.setAttribute("fileInfo", fDto);
	}
	
}
