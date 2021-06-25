package fashionHub.com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.ItemDBUtil;

@WebServlet("/showProductServlet")
public class showProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public showProductServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println(username);
				
		//Set Session newly
		session.setAttribute("user", username);
		
		String item_code = request.getParameter("item_code");
		System.out.println(item_code);
		
		ItemDBUtil itemsh = new ItemDBUtil();
		
		List list = null;
		List list2 = null;
		List list3 = null;
		list = itemsh.getImages(item_code);
		list2 = itemsh.getItemData(item_code);
		System.out.println(list.size());
		System.out.println(list2.size());	
		
		if(list == null && list.size() == 0) {
			ServletHandler.setErrorMessage("Records Not Found!", request);
		}
		
		if(list2 == null && list2.size() == 0) {
			ServletHandler.setErrorMessage("Records Not Found!", request);
		}
		
		ServletHandler.setList(list, request);
		session.setAttribute("list2", list2);
		ServletHandler.forward("product-detail.jsp", request, response);
	}

}
