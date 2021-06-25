package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.CartDBUtil;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddToCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//Get session data
				HttpSession session = request.getSession(false);
				String username = (String)session.getAttribute("user");
				System.out.println("Username: " + username);
				
				//Set Session newly
				session.setAttribute("user", username);
				
				String item_code = request.getParameter("item_code");
				int count; //= Integer.parseInt(request.getParameter("quantity"));
				System.out.println("Itemcode: " + item_code);
				//System.out.println("Item Count: " + count);
				count = 5;
				
				CartDBUtil addCart = new CartDBUtil();
				//int count = 4;
				String str = addCart.addToCart(username, item_code, count);
				
				if(str.equals("success")) {
					ServletHandler.setSuccessMessage("Item Added Successfully!", request);
					ServletHandler.forward("displayCart", request, response);
				}
				else if(str.equals("itemavailable")) {
					ServletHandler.setErrorMessage("Item Code is Already Taken!", request);
					ServletHandler.forward("displayCart", request, response);
				}
				else {
					ServletHandler.setErrorMessage("Item could no be added! Please try again!", request);
					ServletHandler.forward("displayCart", request, response);
				}
	}

}
