<%@page import="com.javaEdu.pj.dto.MemberDTO"%>
<%@page import="com.javaEdu.pj.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>


<%
	request.setCharacterEncoding("EUC-KR");

	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	
	MemberDAO dao = MemberDAO.getInstance();
	int checkNum = dao.userLoginCheck(id, pw);
	if(checkNum == -1){
%>
	<script>
		alert('���̵� �������� �ʽ��ϴ�.');
		history.go(-1);
	</script>
<%
	} else if (checkNum == 0) {
%>
	<script>
		alert('��й�ȣ�� Ʋ���ϴ�.');
		history.go(-1);
	</script>
<%
	} else if (checkNum == 1){
		MemberDTO dto = dao.getMember(id);
		
		if(dto == null){
%>
			<script>
				alert('�������� �ʴ� ȸ�� �Դϴ�.');
				history.go(-1);
			</script>
<%
		} else {
			String name = dto.getName();
			session.setAttribute("id", id);
			session.setAttribute("name", name);
			session.setAttribute("isAd", dto.getIsAdmin());
			session.setAttribute("ValidMem", "yes");
			response.sendRedirect("list.do");
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