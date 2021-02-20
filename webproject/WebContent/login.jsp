<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("ValidMem") != null) {
%>
	<jsp:forward page="list.do"></jsp:forward>
	
<%
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<!-- css 파일 -->
<link href="${pageContext.request.contextPath}/css/css_login.css?v=3"
	rel="stylesheet" type="text/css">

<title>Insert title here</title>
</head>
<body>
	<div class="login_big">
		<div>
			<a class="logo_login" href="list.do">go_Main</a>
			<form action="loginOk.jsp" method="post">
				<input class="id_pass" id="id_input" type="text" name="id" placeholder="아이디"
					value="<%if (session.getAttribute("id") != null)
				out.println(session.getAttribute("id"));%>"><br />
				<input class="id_pass" id="pass_input" placeholder="비밀번호" type="password" name="pw"><br />
				<input type="submit" value="로그인"> 
				<div>
					<input
						type="button" value="회원가입"
						onclick="javascript:window.location='join.jsp'">
				</div>
			</form>
		</div>
	</div>
</body>


</html>