/**
 * 
 */

function display(name){
	var answer_input = document.getElementById(name);
	var p_answer_btn_name = 'p_' +name;
	var p_answer_btn = document.getElementById(p_answer_btn_name);
	answer_input.style.display='block';
	p_answer_btn.style.display='none';
	
	return;
}

function cancelAnswer(name){
	var answer_input = document.getElementById(name);
	var p_answer_btn_name = 'p_' +name;
	var p_answer_btn = document.getElementById(p_answer_btn_name);
	answer_input.style.display='none';
	p_answer_btn.style.display='block';
	
	return;
}