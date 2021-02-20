<%@page import="com.javaEdu.pj.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="dto" class="com.javaEdu.pj.dto.MemberDTO" scope="page"></jsp:useBean>
<jsp:setProperty name="dto" property="*"/>
<% 
	MemberDAO dao = MemberDAO.getInstance();
	String id = (String)session.getAttribute("id");
	dto = dao.getMember(id);
		
	dto.setName((String)request.getParameter("ch_name"));
	dto.setPhone((String)request.getParameter("ch_phone"));
	dto.seteMail((String)request.getParameter("ch_eMail"));
	
	

	
	
	int ri = dao.updateMember(dto);
	
	if(ri == 1){
%>
	<script>
		alert("정보 수정 되었습니다." +"<%=dto.getName()%>, <%=dto.getPhone() %>, <%=dto.geteMail()%>");
		document.location.href="user_modify.do";
	</script>
<%
	} else {
%>
	<script>
		alert("정보 수정 실패 입니다." +"<%=dto.getName()%>, <%=dto.getPhone() %>, <%=dto.geteMail()%>");
		history.go(-1);
	</script>
<%
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>