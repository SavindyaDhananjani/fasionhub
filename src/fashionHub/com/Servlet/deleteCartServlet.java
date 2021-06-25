package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.CartDBUtil;

@WebServlet("/deleteCartServlet")
public class deleteCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public deleteCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println("Username in DisplayUpdateServlet: " + username);
				
		//Set Session newly
		session.setAttribute("user", username);
				
		String item_code = request.getParameter("item_code");
		System.out.println(item_code);
		
		CartDBUtil crt = new CartDBUtil();
		
		String str = crt.removeCart(item_code);
		
		if(str.equals("success")) {
			ServletHandler.setSuccessMessage("Item Removed Successfully!", request);
			ServletHandler.forward("displayCart", request, response);
		}
		else {
			ServletHandler.setErrorMessage("Item could not be Removed! Please try again!", request);
			ServletHandler.forward("displayCart", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
