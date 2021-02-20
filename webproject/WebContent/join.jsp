<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="${pageContext.request.contextPath}/css/css_join.css?v=3"
	rel="stylesheet" type="text/css">

<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript" src="js/members.js" charset="UTF-8" ></script>
	
	
	<div class="join_big">
		<div>
			<a class="logo_join" href="list.do">Go_Main</a>
			<form action="joinOk.jsp" method="post" name="reg_frm">
				<a class="data_input_name">아이디</a> <br/>
				<input class="data_input" type = "text" name="id" size = "20"><br/>
				<a class="data_input_name">비밀번호</a> <br/>
				<input class="data_input" type = "password" name="pw" size = "20"><br/>
				<a class="data_input_name">비밀번호 확인</a> <br/>
				<input class="data_input" type = "password" name="pw_check" size = "20"><br/><br/>
				<a class="data_input_name">이름</a> <br/>
				<input class="data_input" type = "text" name="name" size = "20"><br/>
				<a class="data_input_name">메일</a> <br/>
				<input class="data_input" type = "text" name="eMail" size = "20"><br/>
				<a class="data_input_name">핸드폰 번호</a> <br/>
				<input class="data_input" type = "text" name="phone" size = "20"><br/>
				
				
				<input class="input_submit" type="submit" value = "회원가입" onclick="infoConfirm()">
			</form>
		</div>
	</div>
</body>
</html>