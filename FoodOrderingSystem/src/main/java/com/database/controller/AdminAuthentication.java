package com.database.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminAuthenticationServlet")   
public class AdminAuthentication extends HttpServlet{

	private static final long serialVersionUID = 1L;
	   
	protected void getResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String adminName=request.getParameter("uname");
		String adminPwd=request.getParameter("pwd");
		if(adminName.equals("Java09") && adminPwd.equals("Developer@4")){
			RequestDispatcher rd=request.getRequestDispatcher("item-list.jsp");
			rd.forward(request, response);
		}
		else{
			out.println("<p>Invalid User Please Register First.</p>");
		    RequestDispatcher rd1=request.getRequestDispatcher("AdminLogin.html");
		    rd1.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		getResponse(request, response);
	}
}
