package fashionHub.com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.CartDBUtil;

@WebServlet("/displayCartServlet")
public class displayCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public displayCartServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println("Username in DisplayUpdateServlet: " + username);
				
		//Set Session newly
		session.setAttribute("user", username);
		
		CartDBUtil cart = new CartDBUtil();
		List list = null;
		List list2 = null;
		List list3 = null;
		list = cart.getImages(username);
		list2 = cart.getItemData(username);
		list3 = cart.getCart(username);
		System.out.println(list.size());
		System.out.println(list2.size());	
		System.out.println(list3.size());
		
		if(list == null && list.size() == 0) {
			ServletHandler.setErrorMessage("Records Not Found!", request);
		}
		
		if(list2 == null && list2.size() == 0) {
			ServletHandler.setErrorMessage("Records Not Found!", request);
		}
		
		
		
		ServletHandler.setList(list, request);
		session.setAttribute("list2", list2);
		session.setAttribute("list3", list3);
		ServletHandler.forward("cart.jsp", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
