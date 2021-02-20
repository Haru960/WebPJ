<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.javaEdu.pj.dto.FDto"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.javaEdu.pj.dto.BDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	BDto bdto = (BDto)request.getAttribute("content_view");
	String login_id = null;
	String id = bdto.getId();
	int bid = bdto.getbId();
	String searchType;
	String search;
	String PN;
	if(request.getParameter("PN") != null){
		PN = request.getParameter("PN");
	}else{
		PN = "1";
	}
	if(request.getParameter("searchType") != null){
		searchType = request.getParameter("searchType");
	}else{
		searchType = "title";
	}
	if(request.getParameter("search") != null){
		search = request.getParameter("search");
	}else{
		search = "";
	}
	try{
		Integer.parseInt(PN);
	}catch(Exception e){
		session.setAttribute("messageType", "오류");
		session.setAttribute("messageContent", "페이지 번호가 잘못되었습니다.");
		response.sendRedirect("list.do"); 
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta charset="EUC-KR">
<!-- css 파일 -->
<link href="${pageContext.request.contextPath}/css/css_list.css?v=3"
	rel="stylesheet" type="text/css">

<!-- bootstrap 추후 바꿔야 함-->
<meta name="viewport" content="width=dievice-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<script type="text/javascript" src="js/myPage.js"></script>
	
	
	<!-- bootstrap 추후 바꿔야 함--> 
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<!-- bootstrap 맨위 홈버튼-->
			<div class="navbar-brand-div">
				<a class="navbar-brand" href="Main.jsp">
					<img src="<%=request.getContextPath()%>/img/logo.png" alt="" width="160px" height="100%">
				</a>
			</div>
			
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<!-- bootstrap 홈버튼 오른쪽 기타 side menu-->
			<ul class="nav navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"> 커뮤니티 <span class="caret"></span></a>
					<ul class="dropdown-menu">
					<!-- li class="active" 해당 위치일 경우 액티브로 활성화 시켜야한다 -->
						<li><a href="list.do">자료실</a></li>
						<li><a href="QAlist.do">Q/A</a></li>
					</ul>
				</li>
			</ul>
			<!-- bootstrap 최상단 오른쪽 드롭박스 메뉴-->
			<ul class="nav navbar-nav navbar-right">
				<%
					if (session.getAttribute("ValidMem") != null) {
						id = (String)session.getAttribute("id");
				%>
				<li><a href="logout.do">로그아웃</a></li>
				<li><a href="user_modify.do">마이 페이지</a></li>
				<%
					} else {
				%>

				<li><a href="login.do">로그인</a></li>
				<li><a href="join.do">회원가입</a></li>
				<%
					}
				%>

				
			</ul>
		</div>
	</nav>



	
	
	<div id="sub_visual">
		<div>
			<h2><b>커뮤니티</b></h2>
		</div>
	</div>
	
	
	<div class="grid_container">


		<div class="left_side">
		<input type="radio" id="list1" name="show" onclick="list1_click()" checked="checked" /> <br/>
 			<input type="radio" id="list2" name="show"  onclick="list2_click()" /><br/>
 			<div class="tab">
			    <label for="list1"> 자료실 </label>
			    <label for="list2"> Q/A </label> 
			</div>
		</div>
		
		<div class="search">
			<form class="table-from" action="bcontent_view.do?bid=<%= 1 %>" method="get">
			
				<select name="searchType">
					<option value="title" <% if(searchType.equals("title")) out.print("selected"); %>> 제목 </option>
					<option value="writerId" <% if(searchType.equals("writerId")) out.print("selected"); %>> 작성자 </option>
				</select>
				<input class="data_input" type = "text" name="search" size = "20" value="<%= search %>">
				<input class="btn btn-search" type="submit" value="검색"/>
				
			</form>
		</div>
		<div class="right_side">
		<h2><b>자료실</b></h2>
			<table cellpadding="10" cellspacion="0" class="write_table">
			
					<%
						if (session.getAttribute("ValidMem") != null) {
							login_id = String.valueOf(session.getAttribute("id"));
						}
					%>
				<form action="modify.do" method="post">
				
					<input type="hidden" name="bId" value="${content_view.bId}">
					<input type="hidden" name="id" value="${content_view.id}" >
					<tr>
						<td class="left_td"> 제목 </td>
						<td><b> <input type="text" id="bTitle_input" name="bTitle" value="${content_view.bTitle}" <% if(!(id.equals(login_id))) out.print("disabled"); %>> </b></td>
					</tr>
					<tr>
						<td class="left_td"> 작성자 </td>
						<td> <input type="text" id="bWriter" name="id_view" value="${content_view.id}" disabled="disabled"> </td>
					</tr>
					<tr>
						<td class="left_td"> 내용 </td>
						<td> <textarea rows="10" name="bContent" <% if( !(id.equals(login_id))) out.print("disabled"); %> >${content_view.bContent} </textarea> </td>
					</tr>
					<tr>
					<%
						if((bdto.getfId() != -1)){
							FDto fdto = (FDto)request.getAttribute("fileInfo");
					%>
						<td class="left_td">자료</td>				
						<td> <a href="file_Down.do?bId=<%=bid %>&fileRealName=<%=fdto.getFileRealName()%>"><%=fdto.getFileRealName()%> 다운로드</a> </td>
					<%
						}
					%>
					</tr>
					<tr>
						<td colspan="2">
							<%
							if(id.equals(login_id)){
							%>
							
								<input type="submit" value="수정"> &nbsp;&nbsp;
								<a href="delete.do?bId=${content_view.bId }">삭제</a> &nbsp;&nbsp;
							<%
									}
								
							%>
							<a href="list.do">목록보기</a>
						</td>
					</tr>
				</form>
			</table>
			
			<%-- <div class="paging">
				<ul class="pagination">
					
					<li><a href="bcontent_view.do?bId=<%=bid -1%>" class="bt"> &lt;  </a> </li>
						
					
					
					<li><a href="bcontent_view.do?bId=<%=bdto.getbId() +1%>" class="bt"> &gt; </a> </li>
					
				</ul>
			</div> --%>
		</div>
	</div>
	<div class="under_side">
		<div class="al_wrap">
			<div>
				<img src="<%=request.getContextPath()%>/img/under_logo.png"></img>
			</div>
			<div>
				<address>  충청남도 홍성군 홍북면 충남대로 </address>
				<address><span>Help desk 070.1234.5678, 070.1586.3578</span></address>
				<p class="copy"> Copyright (C) 충청남도 로컬푸드 학교급식 지원센터. All rights reserved </p>
			</div>
			
		</div>
	</div>

</body>
</html>