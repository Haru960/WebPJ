<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.javaEdu.pj.dto.FDto"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.javaEdu.pj.dto.BDto"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
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
		session.setAttribute("messageType", "����");
		session.setAttribute("messageContent", "������ ��ȣ�� �߸��Ǿ����ϴ�.");
		response.sendRedirect("list.do"); 
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta charset="EUC-KR">
<!-- css ���� -->
<link href="${pageContext.request.contextPath}/css/css_list.css?v=3"
	rel="stylesheet" type="text/css">

<!-- bootstrap ���� �ٲ�� ��-->
<meta name="viewport" content="width=dievice-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<script type="text/javascript" src="js/myPage.js"></script>
	
	
	<!-- bootstrap ���� �ٲ�� ��--> 
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<!-- bootstrap ���� Ȩ��ư-->
			<div class="navbar-brand-div">
				<a class="navbar-brand" href="Main.jsp">
					<img src="<%=request.getContextPath()%>/img/logo.png" alt="" width="160px" height="100%">
				</a>
			</div>
			
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<!-- bootstrap Ȩ��ư ������ ��Ÿ side menu-->
			<ul class="nav navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"> Ŀ�´�Ƽ <span class="caret"></span></a>
					<ul class="dropdown-menu">
					<!-- li class="active" �ش� ��ġ�� ��� ��Ƽ��� Ȱ��ȭ ���Ѿ��Ѵ� -->
						<li><a href="list.do">�ڷ��</a></li>
						<li><a href="QAlist.do">Q/A</a></li>
					</ul>
				</li>
			</ul>
			<!-- bootstrap �ֻ�� ������ ��ӹڽ� �޴�-->
			<ul class="nav navbar-nav navbar-right">
				<%
					if (session.getAttribute("ValidMem") != null) {
						id = (String)session.getAttribute("id");
				%>
				<li><a href="logout.do">�α׾ƿ�</a></li>
				<li><a href="user_modify.do">���� ������</a></li>
				<%
					} else {
				%>

				<li><a href="login.do">�α���</a></li>
				<li><a href="join.do">ȸ������</a></li>
				<%
					}
				%>

				
			</ul>
		</div>
	</nav>



	
	
	<div id="sub_visual">
		<div>
			<h2><b>Ŀ�´�Ƽ</b></h2>
		</div>
	</div>
	
	
	<div class="grid_container">


		<div class="left_side">
		<input type="radio" id="list1" name="show" onclick="list1_click()" checked="checked" /> <br/>
 			<input type="radio" id="list2" name="show"  onclick="list2_click()" /><br/>
 			<div class="tab">
			    <label for="list1"> �ڷ�� </label>
			    <label for="list2"> Q/A </label> 
			</div>
		</div>
		
		<div class="search">
			<form class="table-from" action="bcontent_view.do?bid=<%= 1 %>" method="get">
			
				<select name="searchType">
					<option value="title" <% if(searchType.equals("title")) out.print("selected"); %>> ���� </option>
					<option value="writerId" <% if(searchType.equals("writerId")) out.print("selected"); %>> �ۼ��� </option>
				</select>
				<input class="data_input" type = "text" name="search" size = "20" value="<%= search %>">
				<input class="btn btn-search" type="submit" value="�˻�"/>
				
			</form>
		</div>
		<div class="right_side">
		<h2><b>�ڷ��</b></h2>
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
						<td class="left_td"> ���� </td>
						<td><b> <input type="text" id="bTitle_input" name="bTitle" value="${content_view.bTitle}" <% if(!(id.equals(login_id))) out.print("disabled"); %>> </b></td>
					</tr>
					<tr>
						<td class="left_td"> �ۼ��� </td>
						<td> <input type="text" id="bWriter" name="id_view" value="${content_view.id}" disabled="disabled"> </td>
					</tr>
					<tr>
						<td class="left_td"> ���� </td>
						<td> <textarea rows="10" name="bContent" <% if( !(id.equals(login_id))) out.print("disabled"); %> >${content_view.bContent} </textarea> </td>
					</tr>
					<tr>
					<%
						if((bdto.getfId() != -1)){
							FDto fdto = (FDto)request.getAttribute("fileInfo");
					%>
						<td class="left_td">�ڷ�</td>				
						<td> <a href="file_Down.do?bId=<%=bid %>&fileRealName=<%=fdto.getFileRealName()%>"><%=fdto.getFileRealName()%> �ٿ�ε�</a> </td>
					<%
						}
					%>
					</tr>
					<tr>
						<td colspan="2">
							<%
							if(id.equals(login_id)){
							%>
							
								<input type="submit" value="����"> &nbsp;&nbsp;
								<a href="delete.do?bId=${content_view.bId }">����</a> &nbsp;&nbsp;
							<%
									}
								
							%>
							<a href="list.do">��Ϻ���</a>
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
				<address>  ��û���� ȫ���� ȫ�ϸ� �泲��� </address>
				<address><span>Help desk 070.1234.5678, 070.1586.3578</span></address>
				<p class="copy"> Copyright (C) ��û���� ����Ǫ�� �б��޽� ��������. All rights reserved </p>
			</div>
			
		</div>
	</div>

</body>
</html>