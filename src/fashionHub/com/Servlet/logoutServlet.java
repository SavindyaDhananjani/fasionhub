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

@WebServlet("/logoutServlet")
public class logoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public logoutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		
		AccountDBUtil acc = new AccountDBUtil();
		
		int i = acc.updateStatusLogout(username);
		if(i != 0) {
			if(session != null) {
				session.removeAttribute("user");
				ServletHandler.forward("loadImage", request, response);
			}
		}
		else {
			ServletHandler.setErrorMessage("Error Occured! Please Try Again!", request);
			ServletHandler.forward("loadImage", request, response);
		}
		
	}


}
