package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.StudentBean;
import com.service.StudentService;
import com.service.StudentServiceFactory;


@WebServlet("*.g")  //means in layoutform.html name in href would be anything but its extension should be .g, then that page all requests are comes to this controller servlet  
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doProcess(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  String requestUri =  request.getRequestURI();
		  System.out.println(requestUri);
		  
		  //redirecting requests of <a>tags from layout page to their perticular form pages
		  if(requestUri.endsWith("addform.g")) {
			  RequestDispatcher rd = request.getRequestDispatcher("addform.html");
				rd.forward(request, response);
		  }
		  
		  if(requestUri.endsWith("searchform.g")) {
			  RequestDispatcher rd = request.getRequestDispatcher("searchform.html");
			  rd.include(request, response);
		  }
		  
		  if(requestUri.endsWith("updateform.g")) {
			  RequestDispatcher rd = request.getRequestDispatcher("updateform.html");
			  rd.forward(request, response);
		  }
		  
		  if(requestUri.endsWith("deleteform.g")) {
			  RequestDispatcher rd = request.getRequestDispatcher("deleteform.html");
			  rd.forward(request, response);
		  }
		  
		  //add
		  if(requestUri.endsWith("add.g")) {
			  String sid = request.getParameter("id");
			  String sname = request.getParameter("name");
			  long sphone = Long.parseLong(request.getParameter("phone"));
			  String saddress = request.getParameter("address");
			  
			  StudentBean beanobj = new StudentBean();
			  beanobj.setSid(sid);
			  beanobj.setSname(sname);
			  beanobj.setSphone(sphone);
			  beanobj.setSaddress(saddress);
			  
			  StudentService stdservice = StudentServiceFactory.getStudentService();
			  String status = stdservice.addStudent(beanobj);
			  
			  if(status.equals("success")) {
				  RequestDispatcher rd = request.getRequestDispatcher("success.html");
				  rd.forward(request, response);
			  }
			  if(status.equals("failure")) {
				  RequestDispatcher rd = request.getRequestDispatcher("failure.html");
				  rd.forward(request, response);
			  }
			  if(status=="existed"){
				  RequestDispatcher rd = request.getRequestDispatcher("existed.html");
				  rd.forward(request, response);
			  }
		  }
		  
		  
		  //search
		  if(requestUri.endsWith("search.g")) {
			 String sid =  request.getParameter("id");
			  
			 StudentService stdService = StudentServiceFactory.getStudentService();
			 StudentBean stb = stdService.searchStudent(sid);
			 
			 if(stb == null) {
				 RequestDispatcher rd = request.getRequestDispatcher("notexisted.html");
				 rd.forward(request, response);
			 }
			 else {
				 PrintWriter out = response.getWriter();
				 
				 out.print("<body><br><br><br>");
				 out.println("<h3>");
				 out.println("<table style='background:blue;color:white;' align='center' border =1>");
				 out.println("<tr> <td>Student Id: </td> <td>"+stb.getSid()+"</td> </tr>");
				 out.println("<tr> <td>Student Name: </td> <td>"+stb.getSname()+"</td> </tr>");
				 out.println("<tr> <td>Student Phone: </td> <td>"+stb.getSphone()+"</td> </tr>");
				 out.println("<tr> <td>Student Address: </td> <td>"+stb.getSaddress()+"</td> </tr>");
				 out.println("</table></h3></body>");
			 }
		  }
		  
		  
		  //update
		  if(requestUri.endsWith("editform.g")) {  //for getting edit form after checking data is present in record or not
			  String sid = request.getParameter("id");
			  
			  StudentService stdservice = StudentServiceFactory.getStudentService();
			  StudentBean stbean = stdservice.searchStudent(sid);
			  
			  if(stbean == null) {
				 RequestDispatcher rd = request.getRequestDispatcher("notexisted.html");
				   rd.forward(request, response);
			  }
			  else {
				 PrintWriter pw = response.getWriter();
				 
				 pw.println("<body><br><br><br>");
				 pw.println("<form action='update.g' method='post' style='background:pink;' align='center'>");
				 pw.println(" Student Id:"+stbean.getSid()+"<br><br>");
				 pw.println("<input type='hidden' name='eid' value='"+stbean.getSid()+"'><br><br>"); 
				 pw.println(" Student Name: <input type='text' name='ename' value='"+stbean.getSname()+"'><br><br>");
                 pw.println(" Student Phone: <input type='tel' name='ephone' value='"+stbean.getSphone()+"'><br><br>");
                 pw.println("Student Address: <input type='text' name='eaddress' value='"+stbean.getSaddress()+"'><br><br>");
                 pw.println("<button>Update</button>");
                 pw.println("</form></body>");
                                              //we get edit form of the perticular id with the older values in the fields,
                                                  //in that fields we have to fill with new values and clivk update button, 
                                                  //then the form goes to controller servlet i:e; this servlet only
			  }
			  
		  }
		  
		  if(requestUri.endsWith("update.g")) {   //here action="update.g" is checked
			 String id =  request.getParameter("eid");  //these data is newly typed data in fields
			 String name =  request.getParameter("ename");
			 long phone = Long.parseLong(request.getParameter("ephone"));
			 String address =  request.getParameter("eaddress");
			 
			 StudentBean bean = new StudentBean();
			 bean.setSid(id);
			 bean.setSname(name);
			 bean.setSphone(phone);
			 bean.setSaddress(address);
			 
			 StudentService service = StudentServiceFactory.getStudentService();
			 String stri = service.updateStudent(bean);
			  if(stri.equals("success")) {
				  RequestDispatcher rd = request.getRequestDispatcher("success.html");
				  rd.forward(request, response);
			  }
			  else {
				  RequestDispatcher rd = request.getRequestDispatcher("failure.html");
				  rd.forward(request, response); 
			  }
		  }
		  
		
		  //delete
		  if(requestUri.endsWith("delete.g")) {
			  String sid = request.getParameter("id");
			  
			  StudentService service = StudentServiceFactory.getStudentService();
			  String st = service.deleteStudent(sid);
			   if(st.equals("success")) {
				   RequestDispatcher rd = request.getRequestDispatcher("success.html");
				   rd.forward(request, response);
			   }
			   if(st.equals("failure")) {
				   RequestDispatcher rd = request.getRequestDispatcher("failure.html");
				   rd.forward(request, response);
			   }
			   if(st.equals("notexist")) {
				   RequestDispatcher rd = request.getRequestDispatcher("notexisted.html");
				   rd.forward(request, response);
			   }
		  }
		  
	}

}
