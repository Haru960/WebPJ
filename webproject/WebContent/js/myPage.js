/**
 * 
 */


function myPage_click(){
//	$ ('.mypage').show(); 
//	$ ('.mywrite').hide();
//	$('#mypage-1').checked();
	location.href="user_modify.do?ck=mp";
}

function myWrite_click(){
//	$ ('.mywrite').show(); 
//	$ ('.mypage').hide();
//	$('#mywrite-2').checked();
	location.href="user_modify.do?PN=1&searchType=all&ck=mw";
}
function my_QList_click(){
	location.href="user_modify.do?PN=1&searchType=all&ck=myq";
}
function my_AList_click(){
	location.href="user_modify.do?PN=1&searchType=all&ck=mya";
}
function list1_click(){
	location.href="list.do";
}
function list2_click(){
	location.href="QAlist.do";
}