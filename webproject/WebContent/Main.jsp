<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="EUC-KR">
<link href="${pageContext.request.contextPath}/css/css_slide.css?v=3"
	rel="stylesheet" type="text/css">

<!-- bootstrap 추후 바꿔야 함-->
<meta name="viewport" content="width=dievice-width" initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">

<title>MAIN</title>
</head>
<body>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	
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
						String id = (String)session.getAttribute("id");
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
	
	<div class="middles">
		<div class="slideshow-container">

		<div class="mySlides fade">
		  <div class="numbertext">1 / 3</div>
		  <img src="<%=request.getContextPath()%>/img/slide_1.png"  width="100%" height="465px">
		  <!-- <div class="text">Caption Text</div> -->
		</div>
		
		<div class="mySlides fade">
		  <div class="numbertext">2 / 3</div>
		  <img src="<%=request.getContextPath()%>/img/slide2.png"  width="100%" height="465px">
		 <!--  <div class="text">Caption Two</div> -->
		</div>
		
		<div class="mySlides fade">
		  <div class="numbertext">3 / 3</div>
		  <img src="<%=request.getContextPath()%>/img/slide3.png"  width="100%" height="465px">
		  <!-- <div class="text">Caption Three</div> -->
		</div>
		
		
		
		<a class="prev" onclick="plusSlides(-2)">&#10094;</a>
		<a class="next" onclick="plusSlides(0)">&#10095;</a>
		
		</div>
		<br>
		<!--  style="text-align:center" -->
		<div class="dots" >
		  <span class="dot" onclick="currentSlide(1)"></span> 
		  <span class="dot" onclick="currentSlide(2)"></span> 
		  <span class="dot" onclick="currentSlide(3)"></span> 
		</div>
		<script type="text/javascript" src="js/mySlide.js" charset="UTF-8"></script>
	</div>
	<div>
		<div class="middle_cont">
			<div class="c_banner round_edge">
				<a href="list.do">
					<h3> 자료실 </h3>
					<p> 자료실에 등록된 학교 및 공공급식 <br/>
						<span>자료를 확인 할 수 있습니다.</span></p>
				</a>
			</div>
			<div class="c_banner round_edge">
				<a href="QAlist.do">
					<h3> Q/A </h3>
					<p> 궁금한 사항을 작성하거나 <br/>
					<span> 다른 사람이 올린 자료를 확인 할 수 있습니다.</span>
					</p>
				</a>
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