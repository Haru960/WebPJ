/**
 * 
 */


function infoConfirm(){
	if(document.reg_frm.id.value.length == 0){
		alert("아이디는 필수사항입니다.");
		reg_frm.id.focus();
		return;
	}
	if(document.reg_frm.id.value.length < 0){
		alert("아이디는 4자 이상 입력입니다.");
		reg_frm.id.focus();
		return;
	}
	if(document.reg_frm.pw.value.length == 0){
		alert("패스워드는 필수사항입니다.");
		reg_frm.pw.focus();
		return;
	}
	if(document.reg_frm.pw.value != document.reg_frm.pw_check.value){
		alert("패스워드가 일치하지 않습니다.");
		reg_frm.pw.focus();
		return;
	}
	if(document.reg_frm.name.value.length == 0){
		alert("이름은 필수사항입니다.");
		reg_frm.name.focus();
		return;
	}
	if(document.reg_frm.eMail.value.length == 0){
		alert("eMail는 필수사항입니다.");
		reg_frm.eMail.focus();
		return;
	}
	
	document.reg_frm.submit();
}

function updateInfoConfirm(){
//	if(document.reg_frm.pw.value == ""){
//		alert("패스워드를 입력하세요.");
//		reg_frm.pw.focus();
//		return;
//	}
//	
//	if(document.reg_frm.pw.value != document.reg_frm.pw_check.value){
//		alert("패스워드가 일치하지 않습니다.");
//		reg_frm.pw.focus();
//		return;
//	}
	if(document.getElementById("eMail").value.length == 0){
		alert("eMail는 필수사항입니다.");
		reg_frm.eMail.focus();
		return;
	}
	if(document.getElementById("name").value.length == 0){
		alert("이름은 필수사항입니다.");
		reg_frm.name.focus();
		return;
	}
	if(document.getElementById("phone").value.length == 0){
		alert("핸드폰 번호는 필수사항입니다.");
		reg_frm.phone.focus();
		return;
	}
//	
	sessionStorage.setItem('ch_name', document.getElementById("name").value);
	sessionStorage.setItem('ch_phone', document.getElementById("phone").value);
	sessionStorage.setItem('ch_eMail', document.getElementById("eMail").value);
	
	
	
	alert(sessionStorage.length);
	
//	location.href="check_pass.do";
	window.open("check_pass.do")
	return;
//	var form = document.createElement('form');
//
//	form.setAttribute('method', 'get');
//
//	form.setAttribute('action', 'check_pass.do');
//
//	document.charset = "utf-8";
//	
//	var input = document.createElement('input');
//	input.setAttribute("type", "hidden");
//	input.setAttribute("name", "ch_name");
//	input.setAttribute("value", document.getElementById("name").value);
//	form.appendChild(input);
//	
//	input = document.createElement('input');
//	input.setAttribute('type', 'hidden');
//	input.setAttribute('name', 'ch_phone');
//	input.setAttribute('value', document.getElementById("phone").value);
//	form.appendChild(input);
//	
//	input = document.createElement('input');
//	input.setAttribute('type', 'hidden');
//	input.setAttribute('name', 'ch_eMail');
//	input.setAttribute('value', document.getElementById("eMail").value);
//	form.appendChild(input);
//	
//
//	document.body.appendChild(form);
//	
//	
//	form.submit();
}

function check_pw(pass){
	if(document.getElementById("password").value.lenght == 0){
		alert("비밀번호를 입력해주세요.");
		password.focus();
		return;
	}
	else if(document.getElementById("password").value === pass) {
		var ch_name = sessionStorage.getItem("ch_name");
		var ch_phone = sessionStorage.getItem("ch_phone");
		var ch_eMail = sessionStorage.getItem("ch_eMail");
		
		sessionStorage.removeItem("ch_name");
		sessionStorage.removeItem("ch_phone");
		sessionStorage.removeItem("ch_eMail");
		
		
		var form = document.createElement('form');
	
		form.setAttribute('method', 'post');
	
		form.setAttribute('action', 'user_modifyOk.do');
	
		document.charset = "utf-8";
		
		var input = document.createElement('input');
		input.setAttribute("type", "hidden");
		input.setAttribute("name", "ch_name");
		input.setAttribute("value", ch_name);
		form.appendChild(input);
		
		input = document.createElement('input');
		input.setAttribute('type', 'hidden');
		input.setAttribute('name', 'ch_phone');
		input.setAttribute('value', ch_phone);
		form.appendChild(input);
		
		input = document.createElement('input');
		input.setAttribute('type', 'hidden');
		input.setAttribute('name', 'ch_eMail');
		input.setAttribute('value', ch_eMail);
		form.appendChild(input);
		
		alert(sessionStorage.length);
		
		document.body.appendChild(form);
		form.submit();
			
	}else{
		alert("비밀번호가 틀렸습니다.");
		return;
	}
}
function update_cancel(){
	history.go(-1);
	return;
}
