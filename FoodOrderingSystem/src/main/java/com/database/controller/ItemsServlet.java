package com.database.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.List;

import com.database.utils.ItemDAO;
import com.food.ordering.items.RequiredItems;
@WebServlet("/")
public class ItemsServlet  extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private ItemDAO itemDAO;   
    
	public void init() {
		itemDAO = new ItemDAO();
    }
	protected void getRequest(HttpServletRequest request,HttpServletResponse response )throws ServletException,IOException {
		
	 String  inputAction= request.getServletPath();
	 try {
		 switch (inputAction) {
			case "/form":
				showNewForm(request,response );
				break;
			case "/insert":
				insertItem(request, response);
				break;
			case "/update":
				updateItems(request, response);
				break;
			case "/delete":
				deleteItem(request, response);
				break;
				
			default:
				throw new IllegalArgumentException("Unexpected value: " + inputAction);
	}
	 }catch (Exception e) {
		e.printStackTrace();
	}

	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
    	getRequest(request, response);
    }   
    private void itemsList(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, ServletException {
		        List < RequiredItems > listItem =itemDAO.selectAllItems() ;
		        request.setAttribute("listItem", listItem);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("item-list.jsp");
		        dispatcher.forward(request, response);
   }
    private void showNewForm(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
        dispatcher.forward(request, response);

	}
    private void insertItem(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
    	int id=Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int rupee=Integer.parseInt(request.getParameter("rupee"));
        RequiredItems newItem = new RequiredItems(id,name, rupee);
        itemDAO.insertItem(newItem);
        response.sendRedirect("list");

	}
    private void updateItems(HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException, SQLException{
    	int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int rupee = Integer.parseInt(request.getParameter("rupee"));
        RequiredItems book = new RequiredItems(id, name, rupee);
        itemDAO.updateItem(book);
        response.sendRedirect("list");
	}
    private void deleteItem(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException, SQLException{
    	 int id = Integer.parseInt(request.getParameter("id"));
	        itemDAO.deleteItem(id);
	        response.sendRedirect("list");
	}
}
















