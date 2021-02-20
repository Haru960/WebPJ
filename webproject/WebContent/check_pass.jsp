<%@page import="com.javaEdu.pj.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="dto" class="com.javaEdu.pj.dto.MemberDTO" scope="page"></jsp:useBean>
<jsp:setProperty name="dto" property="*"/>
<%
	String id = (String)session.getAttribute("id");
	MemberDAO dao = MemberDAO.getInstance();
	dto = dao.getMember(id);
	session.setAttribute("pass", (String)dto.getPw());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript" src="js/members.js" charset="UTF-8"></script>
	<div>
		<h1> 비밀번호를 입력해주세요! <%=(String)dto.getPw()%> </h1><br/> 
		<input type="password" id="password" name="password" size="20"><br />
		<button onclick="check_pw( '<%=(String)dto.getPw()%>' )">확인</button> <button onclick="update_cancel()">취소</button>
	</div>
</body>
</html>