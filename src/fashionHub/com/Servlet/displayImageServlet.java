package fashionHub.com.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.AccountDBUtil;
import fashionHub.com.Object.Image;

@WebServlet("/displayImageServlet")
public class displayImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public displayImageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println(username);
		
		//Set Session newly
		session.setAttribute("user", username);
							
		AccountDBUtil acc = new AccountDBUtil();
         
        try {
            Image imgs = acc.getImage(username);
             
            request.setAttribute("image", imgs);
             
            String page = "account-details.jsp";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            requestDispatcher.forward(request, response);              
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
	}

}
