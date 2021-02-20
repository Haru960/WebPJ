package com.javaEdu.pj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaEdu.pj.dao.BDao;
import com.javaEdu.pj.dao.FDao;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BWriteCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String ID = null;
		String bName = null;
		String bTitle = null;
		String bContent = null;
		int fId = -1;
		
		
		String directory = request.getSession().getServletContext().getRealPath("/upload/");
		int maxSize = 1024 * 1024 * 100; //100mb까지만 
		String encoding = "UTF-8";
		
		
		try{
			MultipartRequest multipartRequest = new MultipartRequest(request, directory, maxSize, encoding, new DefaultFileRenamePolicy());
			
			ID = multipartRequest.getParameter("id");
			bName = multipartRequest.getParameter("bName");
			bTitle = multipartRequest.getParameter("bTitle");
			bContent = multipartRequest.getParameter("bContent");
			
			
			String fileName = multipartRequest.getOriginalFileName("file");
			String fileRealName = multipartRequest.getFilesystemName("file");
			
			System.out.println("file name test:" +fileName);
			
			
			FDao fdao = new FDao();
			fId = fdao.upload(fileName, fileRealName) ;
			
			System.out.println("fId test : " +fId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		BDao dao = new BDao();
		dao.write(ID, bName, bTitle, bContent, fId);
	}
	
}
