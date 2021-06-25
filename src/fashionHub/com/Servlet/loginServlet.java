package fashionHub.com.Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.AccountDBUtil;
import fashionHub.com.Object.User;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public loginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		AccountDBUtil ldb = new AccountDBUtil();
		

		try {
			User usr = ldb.validateLogin(username, password);
			String dPage = null;
			if(usr != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", usr.getUsername());
				session.setAttribute("role", usr.getRole());
				ldb.updateStatus(username);
				
				if(usr.getRole().equals("customer"))
					dPage = "loadImage";
				else if(usr.getRole().equals("admin"))
					dPage = "admin.jsp";
			}
			else {
				//String message = "";
				ServletHandler.setErrorMessage("Invalid Username or Password", request);
				dPage = "account.jsp";
			}
			
			ServletHandler.forward(dPage, request, response);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
