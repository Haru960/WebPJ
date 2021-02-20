<%@page import="com.javaEdu.pj.dto.QADto"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	QADto qdto = (QADto)request.getAttribute("content_view");
	int aId = qdto.getaId(); //int �� null �̿ð�� 0���� ġȯ�Ǿ�����?
			if(aId != 0){
				QADto adto = (QADto)request.getAttribute("reply_view");
			}
	String login_id = null;
	String id = qdto.getId();
	int qid = qdto.getqId();
	String searchType;
	String search;
	String PN;
	if (session.getAttribute("ValidMem") != null) {
		login_id = String.valueOf(session.getAttribute("id"));
	}
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
<!-- css ���� -->
<link href="${pageContext.request.contextPath}/css/css_QAlist.css?v=3"
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
	<script type="text/javascript" src="js/answer_btn.js"></script>
	
	
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
		<input type="radio" id="list1" name="show" onclick="list1_click()" /> <br/>
 			<input type="radio" id="list2" name="show"  onclick="list2_click()" checked="checked"  /><br/>
 			<div class="tab">
			    <label for="list1"> �ڷ�� </label>
			    <label for="list2"> Q/A </label> 
			</div>
		</div>
		
		<div class="search">
			<form class="table-from" action="QAlist.do?PN=<%= 1 %>" method="get">
			
				<select name="searchType">
					<option value="title" <% if(searchType.equals("title")) out.print("selected"); %>> ���� </option>
					<option value="writerId" <% if(searchType.equals("writerId")) out.print("selected"); %>> �ۼ��� </option>
				</select>
				<input class="data_input" type = "text" name="search" size = "20" value="<%= search %>">
				<input class="btn btn-search" type="submit" value="�˻�"/>
				
			</form>
		</div>
		<div class="right_side">
		<h2><b>Q/A</b></h2>
			<div>
				<table  cellpadding="0" cellspacion="0" class="QAwrite_table">
				
					<form action = "qa_modify.do" method="post" >
					
						<input type="hidden" name="qId" value="${content_view.qId}">
						<input type="hidden" name="id" value="${content_view.id}" >
						<tr>
							<td class="left_td"> ���� </td>
							<td> <input type="text" name="qTitle" id="QATitle_input" value="${content_view.qTitle}" <% if(!(id.equals(login_id)) || aId != 0) out.print("disabled"); %>> </td>
						</tr>
						<tr>
							<td class="left_td"> �ۼ��� </td>
							<td> <input type="text" name="id_view" id="QAWriter" value="${content_view.id}" disabled="disabled"> </td>
						</tr>
						<tr>
							<td class="left_td"> ���� </td>
							<td> <textarea rows="10" name="qContent" <% if( !(id.equals(login_id)) || aId != 0) out.print("disabled"); %> >${content_view.qContent} </textarea> </td>
						</tr>
						<tr>
						<td colspan="2">
						<%
						if(aId == 0){
							if (session.getAttribute("ValidMem") != null) {
								String isad = String.valueOf(session.getAttribute(("isAd")));
								if(id.equals(login_id)){	
						%>
								<input type="submit" value="����"> &nbsp;&nbsp;
								<a href="qa_delete.do?qId=${content_view.qId }">����</a> &nbsp;&nbsp;
						<%
								}
								if(isad.equals("1")) {
						%>
								<p class="btn_area" id="p_answer_w">
									<a href="#" onclick="display('answer_w'); return false;"> �亯 �ۼ�</a> &nbsp;&nbsp;
								</p>
						<%
								}
							}
						%>
							<a href="QAlist.do">��Ϻ���</a></td>
						<%
						}
						%>
						</tr>
					</form>
				</table>
				<%
				if(aId != 0){
				%>
				<div class="answer_table_view">
					<b class="answer_header">======�亯======</b>
					<table class="answer_view">
							<tbody>
								<tr>
									<th scope="row">
										<div class="thcell">
											<p>����</p>
										</div>
									</th>
									<td>
										<div class="thcell">
											<p>${reply_view.qTitle}</p>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row">
										<div class="thcell">
												<p>���� </p>
										</div>
									</th>
									<td>
										<div class="thcell">
											<textarea rows="10" disabled="disabled"> ${reply_view.qContent}</textarea>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row">
										<div class="thcell">
											<p>�亯��</p>
										</div>
									</th>
									<td>
										<div class="thcell">
											<p>${reply_view.id}</p>
										</div>
									</td>
								</tr>
								<tr>
								<th scope="row">
										<div class="thcell">
											<p>�ۼ���</p>
										</div>
									</th>
									<td>
										<div class="thcell">
											<p>${reply_view.qDate}</p>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<a href="QAlist.do">��Ϻ���</a></td>
									</td>
								</tr>
							</tbody>
					</table>	
				</div>
										
				<%
				}
				%>
				<div class="answer_table_input" id="answer_w">
				<h2><b>�亯 �ۼ�</b></h2>
					<table  cellpadding="0" cellspacion="0" class="QAwrite_table">
						<form action = "qa_answer.do" method="post">
						
							<input type="hidden" name="qId" value="${content_view.qId}">
							<input type="hidden" name="answer_id" value="<%=login_id %>">
							<tr>
								<td class="left_td"> ���� </td>
								<td> <input type="text" name="aTitle" value="Re: ${content_view.qTitle}" > </td>
							</tr>
							<tr>
								<td class="left_td"> �ۼ��� </td>
								<td> <input type="text" name="answer_id_view" value="<%=login_id %>" disabled="disabled"> </td>
							</tr>
							<tr>
								<td class="left_td"> ���� </td>
								<td> <textarea rows="10" name="aContent"> </textarea> </td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="submit" value="�亯 �Ϸ�">
									<a href="#" onclick="cancelAnswer('answer_w');return false;" >�亯 ���</a>
								</td>
							</tr>
						</form>
					</table>
				</div>
			</div>
			
			<%-- <div class="paging">
			<!-- ���� �� ������ ����¡ ó�� �ٽ� �ؾߵ� -->
				<ul class="pagination">
					<li><a href="qacontent_view.do?bId=<%=qid -1%>" class="bt"> &lt;  </a> </li>
						
					<li><a href="qacontent_view.do?bId=<%=qid +1%>" class="bt"> &gt; </a> </li>
					
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