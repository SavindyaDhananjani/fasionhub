package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.AccountDBUtil;
import fashionHub.com.Object.Register;

@WebServlet("/registerCustomerServlet")
public class registerCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public registerCustomerServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Copying all the input parameters in to local variables
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = "customer";
		
		//Create Register Object
		Register r = new Register();
		
		//Send Data to Register Object
		r.setUsername(username);
		r.setPassword(password);
		r.setRole(role);
		
		//Create registerDB Object
		AccountDBUtil rdb = new AccountDBUtil();
		
		//Create session to pass values
		HttpSession session = request.getSession();
		session.setAttribute("user", username);
		//session.setAttribute("role", role);
		
		//Send Register Object to RegisterDBUtil Object
		String userRegistered = rdb.registerUser(r);
		
		if(userRegistered.equals("SUCCESS")) {
			request.getRequestDispatcher("/register-Customer.jsp").forward(request, response);
			System.out.println("Registered Successfully!");
		}
		else {
			request.setAttribute("errMessage",  userRegistered);
			request.getRequestDispatcher("/account.html").forward(request, response);
		}
	}

}
