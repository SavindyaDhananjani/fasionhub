package fashionHub.com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.AccountDBUtil;
import fashionHub.com.Object.Person;

@WebServlet("/displayUpdateServlet")
public class displayUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public displayUpdateServlet() {
        super();
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println("Username in DisplayUpdateServlet: " + username);
		
		//Set Session newly
		session.setAttribute("user", username);
		
		
		AccountDBUtil udb = new AccountDBUtil();
		List list = null;
		list = udb.findData(username);
		System.out.println(list.size());
		
		if(list == null && list.size()==0) {
			System.out.println("NULL VALUE GOT");
			ServletHandler.setErrorMessage("Record Not Found", request);
		} 
			
		ServletHandler.setList(list, request);
		ServletHandler.forward("account-update.jsp", request, response);
	}
}
