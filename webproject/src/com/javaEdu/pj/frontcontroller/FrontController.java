package com.javaEdu.pj.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Session;

import com.javaEdu.pj.command.AWriteCommand;
import com.javaEdu.pj.command.BContentCommand;
import com.javaEdu.pj.command.BDeleteCommand;
import com.javaEdu.pj.command.BFileDownCommand;
import com.javaEdu.pj.command.BListCommand;
import com.javaEdu.pj.command.BModifyCommand;
import com.javaEdu.pj.command.BMylistCommand;
import com.javaEdu.pj.command.BWriteCommand;
import com.javaEdu.pj.command.Command;
import com.javaEdu.pj.command.QAListCommand;
import com.javaEdu.pj.command.QAWriteCommand;
import com.javaEdu.pj.command.QContentCommand;
import com.javaEdu.pj.command.QDeleteCommand;
import com.javaEdu.pj.command.QModifyCommand;

/**
 * Servlet implementation class BFrontController
 */
@WebServlet("*.do")
//@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost");
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo");
		
		request.setCharacterEncoding("EUC-KR");
		
		String viewPage = null;
		Command command = null;
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		
		if(com.equals("/bwrite_view.do")) {
			viewPage = "bwrite_view.jsp";
		} else if( com.equals("/write.do")) {
			command = new BWriteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		} else if( com.equals("/list.do")) {
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "list.jsp";
		} else if( com.equals("/bcontent_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "bcontent_view.jsp";
		} else if( com.equals("/delete.do")) {
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		} else if( com.equals("/modify.do")) {
			command = new BModifyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}else if( com.equals("/check_pass.do")) {
			viewPage = "check_pass.jsp";
		}else if(com.equals("/file_uploadcheck.do")) {
			viewPage = "upload_file.jsp";
		}else if(com.equals("/file_Down.do")) {
			command = new BFileDownCommand();
			command.execute(request, response);
			viewPage = "bcontent_view.do";
		}
		
		if( com.equals("/login.do")) {
			viewPage = "login.jsp";
		}else if( com.equals("/loginOk.do")) {
			viewPage = "loginOk.jsp";
		}else if ( com.equals("/join.do")) {
			viewPage = "join.jsp";
		}else if( com.equals("/joinOk.do")) {
			viewPage = "joinOk.jsp";
		}else if( com.equals("/logout.do")) {
			viewPage = "logout.jsp";
		}else if( com.equals("/user_modify.do")) {
			command = new BMylistCommand();
			command.execute(request, response);
			viewPage = "user_modify.jsp";
		}else if( com.equals("/user_modifyOk.do")) {
			viewPage = "user_modifyOk.jsp";
		}else if( com.equals("/QAlist.do")) {
			command = new QAListCommand();
			command.execute(request, response);
			viewPage = "QAlist.jsp";
		}else if( com.equals("/qa_content_view.do")) {
			command = new QContentCommand();
			command.execute(request, response);
			viewPage = "qa_content_view.jsp";
		}else if( com.equals("/qa_write_view.do")) {
			viewPage = "qa_write_view.jsp";
		}else if( com.equals("/qa_write.do")) {
			command = new QAWriteCommand();
			command.execute(request, response);
			viewPage = "QAlist.do";
		}else if( com.equals("/qa_answer.do")) {
			command = new AWriteCommand();
			command.execute(request, response);
			viewPage = "qa_content_view.do";
		}else if( com.equals("/qa_modify.do")) {
			command = new QModifyCommand();
			command.execute(request, response);
			viewPage = "qa_content_view.do";
		}else if( com.equals("/qa_delete.do")) {
			command = new QDeleteCommand();
			command.execute(request, response);
			viewPage = "QAlist.do";
		}else if( com.equals("/qa_m_delete.do")) {
			command = new QDeleteCommand();
			command.execute(request, response);
			//viewPage 이동 문제있음. 
			//(CURD) 에서 C U D 이후 .do >> .do 이동시 URL 재대로 바뀌지 않는 문제 + URL로 접근하여 글 도배할 수있는 문제 있음
			viewPage = "QAlist.do";
		}
		
		
		
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

}
