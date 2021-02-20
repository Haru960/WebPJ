<%@page import="com.javaEdu.pj.dao.MemberDAO"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	request.setCharacterEncoding("UTF-8");
    %>
    <jsp:useBean id="dto" class = "com.javaEdu.pj.dto.MemberDTO"></jsp:useBean>
    <jsp:setProperty name="dto" property="*"/>
    <%
    	dto.setrDate(new Timestamp(System.currentTimeMillis()));
    	MemberDAO dao = MemberDAO.getInstance();
    	if(dao.confirmId(dto.getId()) == MemberDAO.MEMBER_EXISTENT){
    %>
    	<script>
    		alert('아이디가 이미 존재 합니다.');
    		history.back();
    	</script>
	<%
    	}else{
    		int ri = dao.insertMember(dto);
    		if(ri == MemberDAO.MEMBER_JOIN_SUCCESS){
    			session.setAttribute("id", dto.getId());
	%>    
		<script>
			alert('회원가입을 축하 합니다.');
			document.location.href="list.do";
		</script>
	<%
    		}else{
	%>
		<script>
			alert('회원가입에 실패했습니다.');
			document.location.href="list.do";
		</script>
	<%
    		}
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