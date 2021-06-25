package fashionHub.com.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.AccountDBUtil;
import fashionHub.com.Object.DBConnection;
import fashionHub.com.Object.Image;
import fashionHub.com.Object.Person;

@WebServlet("/displayPersonDBUtil")
public class displayPersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public displayPersonServlet() {
        super();
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println(username);
		
		//Set Session newly
		session.setAttribute("user", username);
					
		AccountDBUtil acc = new AccountDBUtil();
		List list = null;
		list = acc.displayPerson(username);
		System.out.println(list.size());
        
        try {
            Image imgs = acc.getImage(username);
             
            request.setAttribute("image", imgs); 
            //String page = "account-details.jsp";
            //RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            //requestDispatcher.forward(request, response);              
		if(list == null && list.size()==0) {
			System.out.println("NULL VALUE GOT");
			ServletHandler.setErrorMessage("Record Not Found", request);
		} 
			
		ServletHandler.setList(list, request);
		ServletHandler.forward("account-details.jsp", request, response);
		
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
		
	}

}
