package fashionHub.com.Servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.ItemDBUtil;
import fashionHub.com.Object.Image;

@WebServlet("/searchItemServlet")
public class searchItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public searchItemServlet() {
        super();
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		String search = request.getParameter("search");
		System.out.println("Username in DisplayUpdateServlet: " + username);
		
		//Set Session newly
		session.setAttribute("user", username);
		
		//Create SearchItemDBUtil object
		ItemDBUtil src = new ItemDBUtil();
		List list = null;
		list = src.SearchItem(search);
		System.out.println(list.size());
		
		if(list == null && list.size() == 0) {
			ServletHandler.setErrorMessage("Records Not Found!", request);
		}
		
		ServletHandler.setList(list, request);
		ServletHandler.forward("index.jsp", request, response);
	}

}
