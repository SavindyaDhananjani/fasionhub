package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.AccountDBUtil;

@WebServlet("/deleteAccountServlet")
public class deleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public deleteAccountServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println(username);
				
		//Set Session newly
		session.setAttribute("user", username);
		
		AccountDBUtil dacc = new AccountDBUtil();
		
		int i = (int)dacc.deleteAcc(username);
		
		if(i != 0) {
			session.removeAttribute("user");
			request.getRequestDispatcher("success.jsp").forward(request, response);
			System.out.println("Delete Successfully!");
		}
		else {
			request.getRequestDispatcher("unsuccess.jsp").forward(request, response);
			System.out.println("Delete Unsuccessful!");
		}
	}


}
