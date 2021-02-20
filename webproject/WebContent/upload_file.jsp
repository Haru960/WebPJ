<%@page import="com.sun.org.apache.xpath.internal.operations.Mult"%>
<%@ page import = "com.javaEdu.pj.dao.FDao" %>
<%@ page import = "java.io.File" %>
<%@ page import = "com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import = "com.oreilly.servlet.MultipartRequest" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String directory = application.getRealPath("/upload/");
	int maxSize = 1024 * 1024 * 100; //100mb까지만 
	String encoding = "UTF-8";
	
	String bName = null;
	String bTitle = null;
	String bContent = null;
	String id = null;
	
	try{
		MultipartRequest multipartRequest = new MultipartRequest(request, directory, maxSize, encoding, new DefaultFileRenamePolicy());
		
		bName = (String)session.getAttribute("bName");
		id = (String)session.getAttribute("id");
		bTitle = multipartRequest.getParameter("bTitle");
		bContent = multipartRequest.getParameter("bContent");
		
		String fileName = multipartRequest.getOriginalFileName("file");
		String fileRealName = multipartRequest.getFilesystemName("file");
		
		new FDao().upload(fileName, fileRealName);
		
		out.write( "파일명 : " +fileName +"<br/>");
		out.write( "실제 파일명 : " +fileRealName +"<br/>");
	}catch(Exception e){
		
		request.getRequestDispatcher("bwrite_view.do").forward(request, response);
	}
%>
	<form action="write.do" method="post" enctype="multipart/form-data">
					<tr hidden="">
						<td> <input type="text" name="bName" size="20" value="<%=bName%>" hidden=""> </td>
					</tr>
					<tr hidden="">
						<td> <input type="text" name="id" size="20" value="<%=id%>" hidden=""> </td>
					</tr>
					<tr>
						<td colspan="2"> <input type="text" id="bTitle_input" name="bTitle" size="50"  value="<%=bTitle %>>" width="500px" hidden=""> </td>
					</tr>
					<tr>
						<td colspan="2"> <textarea name="bContent" id="bContent_input" rows="10"  hidden=""><%=bContent %></textarea></td>
					</tr>
					<tr>
						<td> <input type="file" name="file" hidden=""> </td>
					</tr>
					<tr>
						<td colspan="2"> </td>
					</tr>
				</form>
</body>
</html>