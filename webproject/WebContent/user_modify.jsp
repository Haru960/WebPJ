<%@page import="com.javaEdu.pj.dao.BDao"%>
<%@page import="com.javaEdu.pj.dto.MemberDTO"%>
<%@page import="com.javaEdu.pj.dao.MemberDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("ValidMem") == null ) {
%>
	<script type="text/javascript">
		alert("로그인이 풀려있습니다.");
		location.href="list.do";
	</script>
<%
	}
%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	String id = (String) session.getAttribute("id");
	MemberDAO dao = MemberDAO.getInstance();
	MemberDTO dto = dao.getMember(id);
%>
<%
	String ck;
	String searchType;
	String search;
	String PN;
	if(request.getParameter("ck") != null){
		ck = request.getParameter("ck");
	}else{
		ck = "mp";
	}
	if(request.getParameter("PN") != null){
		PN = request.getParameter("PN");
	}else{
		PN = "1";
	}
	if(request.getParameter("searchType") != null){
		searchType = request.getParameter("searchType");
	}else{
		searchType = "all";
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
		response.sendRedirect("user_modify.do"); 
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<!-- css 파일 -->
<link href="${pageContext.request.contextPath}/css/css_modify.css?v=3"
	rel="stylesheet" type="text/css">

<!-- bootstrap -->
<meta name="viewport" content="width=dievice-width" initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.js" charset="UTF-8"></script>
	<script type="text/javascript" src="js/members.js" charset="UTF-8"></script>
	<script type="text/javascript" src="js/myPage.js" charset="UTF-8"></script>
	<!-- 
	<script>
	$(document).ready(function() {
		$('.mypage').show(); //페이지를 로드할 때 표시할 요소
		$('.mywrite').hide(); //페이지를 로드할 때 숨길 요소
		
		$('#mypage-1').click(function(){
			$ ('.mypage').show(); 
			$ ('.mywrite').hide();
			$('#mypage-1').checked();
			return false;
		});
		$('#mywrite-2').click(function(){
			$ ('.mywrite').show(); 
			$ ('.mypage').hide();
			$('#mywrite-2').checked();
			return false;
		});
	});
	</script>
	 -->

	<!-- bootstrap -->
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
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
			<input type="radio" id="mypage-1" name="show" onclick="myPage_click()" <% if(ck.equals("mp")) out.print("checked"); %> /> <br/>
 			<input type="radio" id="mywrite-2" name="show"  onclick="myWrite_click()" <% if(ck.equals("mw")) out.print("checked"); %> /><br/>
 			<input type="radio" id="mywrite-3" name="show"  onclick="my_QList_click()" <% if(ck.equals("myq")) out.print("checked"); %> /><br/>
 			<div class="tab">
			    <label for="mypage-1">마이페이지</label>
			    <label for="mywrite-2">내가 쓴 글</label> 
			    <label for="mywrite-3">내가 쓴 1:1문의</label> 
			</div>
		</div>
		<div class="right_side">

			<div class="mypage" <%if (!ck.equals("mp"))
				out.print("hidden");%>>
				<div class="info_header">
					<h1>회원정보</h1>
					<p class="contxt">
						<strong><%=id%></strong>님의 연락처 정보입니다.
					</p>
				</div>

					<table class="myinfo">
						<tbody>
							<tr>
								<th scope="row">
									<div class="thcell">
										<span class="data_input_name">이름</span>
									</div>
								</th>
								<td>
									<div class="thcell">
										<input type="text" id="name" name="name" size="20" value="<%=dto.getName()%>"><br />
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<div class="thcell">
										<span class="data_input_name">핸드폰</span> <br />
									</div>
								</th>
								<td>
									<div class="thcell">
										<input type="text" id="phone" name="phone" size="20" value="<%=dto.getPhone()%>"><br />
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<div class="thcell">
										<span class="data_input_name">메일</span> <br />
									</div>
								</th>
								<td>
									<div class="thcell">
										<input type="text" id="eMail" name="eMail" size="20" value="<%=dto.geteMail()%>"><br />
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<div class=thcell>
										<span class="data_input_name">가입일</span> <br />
									</div>
								</th>
								<td>
									<div class="thcell">
										<input type="text" name="name" size="20" value="<%=dto.getrDate()%>" disabled><br />
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<button type="button" id="submit_btn" onclick="updateInfoConfirm()"> 수정 </button>
			</div>

			<div class="mywrite" <% if(!ck.equals("mw")) out.print("hidden"); %>>
				<div class="search">
					<form class="table-from" action="user_modify.do" method="get">
						<input class="data_input" type="text" name="search" size="50"
							value="<%= search %>"> 
						<input class="data_input"
							type="text" name="PN" size="50" value="1" hidden=""> 
						<input
							class="data_input" type="text" name="ck" size="50"
							value="mw" hidden=""> 
						<input class="btn btn-search"
							type="submit" value="검색" />

					</form>
				</div>

				<table class="board">
					<tr id="tr_first">
						<td width="8%">번호</td>
						<td width="40%" class="border_td">제목</td>
						<td width="15%" class="border_td">작성자</td>
						<td width="10%" class="border_td">날짜</td>
						<td width="8%" class="border_td">조회</td>
						<td width="8%">삭제</td>
					</tr>
					<c:forEach items="${list}" var="dto">
						<tr class="trs">
							<td id="td_num">${dto.bId}</td>
							<td class="border_td" id="td_title"><a
								href="bcontent_view.do?bId=${dto.bId}">${dto.bTitle}</a></td>
							<td class="border_td" id="td_uName">${dto.id}</td>
							<td class="border_td" id="td_date">${ (dto.bDate).toString().substring(2, 10) }</td>
							<td id="td_hit">${dto.bHit}</td>
							<td id="td_del"><a href="delete.do?bId=${dto.bId}">삭제</a></td>
						</tr>
					</c:forEach>
				</table>
				<div class="paging">
					<ul class="pagination">
						<% 
						String firstPage = "1";
						int startPage = (Integer.parseInt(PN) / 10) * 10 + 1;
						if(Integer.parseInt(PN) % 10 == 0) startPage -= 10;
						int targetPage = new BDao().targetPage(PN, search, searchType, id);
						
						%>

						<li><a
							href="user_modify.do?PN=<%= firstPage %>&searchType=<%=searchType%>&ck=mw"
							class="bt"> &lt;&lt; </a></li>

						<%
						if(Integer.parseInt(PN) != 1) {
						%>

						<li><a
							href="user_modify.do?PN=<%= Integer.parseInt(PN) - 1 %>&searchType=<%=searchType%>&ck=mw"
							class="bt"> &lt; </a></li>

						<%
						} else {
						%>

						<li><a
							href="user_modify.do?PN=<%= Integer.parseInt(PN) %>&searchType=<%=searchType%>&ck=mw"
							class="bt"> &lt; </a></li>

						<%
						}
						%>

						&nbsp;

						<%
						for(int i = startPage; i < Integer.parseInt(PN); i++){
						%>
						<li><a
							href="user_modify.do?PN=<%= i %>&searchType=<%=searchType%>&ck=mw"
							class="bt"><%= i %> </a></li>
						<%
						}
						%>
						<li class="active"><a
							href="user_modify.do?PN=<%= PN %>&searchType=<%=searchType%>&ck=mw"
							class="bt"><%= PN %> </a></li>
						<%
						for(int i = Integer.parseInt(PN) + 1; i <= targetPage; i++){
							if(i < startPage + 10) {
							
						%>
						<li><a
							href="user_modify.do?PN=<%= i %>&searchType=<%=searchType%>&ck=mw"
							class="bt"><%= i %> </a></li>
						<%
							}
						}
						%>
						&nbsp;
						<li><a
							href="user_modify.do?PN=<%=Integer.parseInt(PN) + 1 %>&searchType=<%=searchType%>&ck=mw&ck=mw"
							class="bt"> &gt; </a></li>
						<% 
						if(targetPage + Integer.parseInt(PN) > startPage + 9){
						%>
						<li><a
							href="user_modify.do?PN=<%= startPage+10 %>&searchType=<%=searchType%>&ck=mw"
							class="bt"> &gt;&gt; </a></li>
						<%
						} else {
						%>
						<li><a
							href="user_modify.do?PN=<%=PN%>&searchType=<%=searchType%>&ck=mw"
							class="bt"> &gt;&gt; </a></li>
						<%
						}
						%>
					</ul>
				</div>
			</div>
			
			<div class="mywrite" <% if(!ck.equals("myq")) out.print("hidden"); %>>
				<div class="search">
					<form class="table-from" action="user_modify.do" method="get">
						<input class="data_input" type="text" name="search" size="50"
							value="<%= search %>"> 
						<input class="data_input"
							type="text" name="PN" size="50" value="1" hidden=""> 
						<input
							class="data_input" type="text" name="ck" size="50"
							value="myq" hidden=""> 
						<input class="btn btn-search"
							type="submit" value="검색" />

					</form>
				</div>

				<table class="board">
					<tr id="tr_first">
						<td width="8%">번호</td>
						<td width="40%" class="border_td">제목</td>
						<td width="15%" class="border_td">작성자</td>
						<td width="10%" class="border_td">날짜</td>
						<td width="8%" class="border_td">조회</td>
						<td width="8%">삭제</td>
					</tr>
					<c:forEach items="${qlist}" var="dto">
						<tr class="trs">
							<td id="td_num">${dto.qId}</td>
							<td class="border_td" id="td_title">
								<div class="title_bd">
									<div>
										<a href="qa_content_view.do?qId=${dto.qId}">
											${dto.qTitle}
										</a>
									</div>
									
									<div class="answer">
										<c:if test="${dto.aId != 0}">
											<strong id="yes">답변 완료 </strong>
										</c:if>
										<c:if test="${dto.aId == 0}">
											<strong id="no">미 답변 </strong>
										</c:if>     
									</div>
								</div>
							<td class="border_td" id="td_uName">${dto.id}</td>
							<td class="border_td" id="td_date">${ (dto.qDate).toString().substring(2, 10) }</td>
							<td id="td_hit">${dto.qHit}</td>
							<td id="td_del">
								<c:if test="${dto.aId != 0}">
									<strong id="yes"> 불가 </strong>
								</c:if>
								<c:if test="${dto.aId == 0}">
									<a href="qa_m_delete.do?qId=${dto.qId}">삭제</a>
								</c:if>     
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="paging">
					<ul class="pagination">
						<% 
						firstPage = "1";
						startPage = (Integer.parseInt(PN) / 10) * 10 + 1;
						if(Integer.parseInt(PN) % 10 == 0) startPage -= 10;
						targetPage = new BDao().targetPage(PN, search, searchType, id);
						
						%>

						<li><a
							href="user_modify.do?PN=<%= firstPage %>&searchType=<%=searchType%>&ck=myq"
							class="bt"> &lt;&lt; </a></li>

						<%
						if(Integer.parseInt(PN) != 1) {
						%>

						<li><a
							href="user_modify.do?PN=<%= Integer.parseInt(PN) - 1 %>&searchType=<%=searchType%>&ck=myq"
							class="bt"> &lt; </a></li>

						<%
						} else {
						%>

						<li><a
							href="user_modify.do?PN=<%= Integer.parseInt(PN) %>&searchType=<%=searchType%>&ck=myq"
							class="bt"> &lt; </a></li>

						<%
						}
						%>

						&nbsp;

						<%
						for(int i = startPage; i < Integer.parseInt(PN); i++){
						%>
						<li><a
							href="user_modify.do?PN=<%= i %>&searchType=<%=searchType%>&ck=myq"
							class="bt"><%= i %> </a></li>
						<%
						}
						%>
						<li class="active"><a
							href="user_modify.do?PN=<%= PN %>&searchType=<%=searchType%>&ck=myq"
							class="bt"><%= PN %> </a></li>
						<%
						for(int i = Integer.parseInt(PN) + 1; i <= targetPage; i++){
							if(i < startPage + 10) {
							
						%>
						<li><a
							href="user_modify.do?PN=<%= i %>&searchType=<%=searchType%>&ck=myq"
							class="bt"><%= i %> </a></li>
						<%
							}
						}
						%>
						&nbsp;
						<li><a
							href="user_modify.do?PN=<%=Integer.parseInt(PN) + 1 %>&searchType=<%=searchType%>&ck=mw&ck=myq"
							class="bt"> &gt; </a></li>
						<% 
						if(targetPage + Integer.parseInt(PN) > startPage + 9){
						%>
						<li><a
							href="user_modify.do?PN=<%= startPage+10 %>&searchType=<%=searchType%>&ck=myq"
							class="bt"> &gt;&gt; </a></li>
						<%
						} else {
						%>
						<li><a
							href="user_modify.do?PN=<%=PN%>&searchType=<%=searchType%>&ck=myq"
							class="bt"> &gt;&gt; </a></li>
						<%
						}
						%>
					</ul>
				</div>
			</div>

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