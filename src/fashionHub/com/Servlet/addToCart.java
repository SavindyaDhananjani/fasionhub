package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.CartDBUtil;

@WebServlet("/addToCart")
public class addToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public addToCart() {
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
		
		CartDBUtil addCart = new CartDBUtil();
		
		String str = addCart.addToCart(username, item_code);
		
		if(str.equals("success")) {
			ServletHandler.setSuccessMessage("Item Added Successfully!", request);
			ServletHandler.forward("loadImage", request, response);
		}
		else if(str.equals("itemavailable")) {
			ServletHandler.setErrorMessage("Item Code is Already Taken!", request);
			ServletHandler.forward("loadImage", request, response);
		}
		else {
			ServletHandler.setErrorMessage("Item could no be added! Please try again!", request);
			ServletHandler.forward("loadImage", request, response);
		}
		
	}

}
