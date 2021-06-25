package fashionHub.com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.ItemDBUtil;

@WebServlet("/displayHomeServlet")
public class displayHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public displayHomeServlet() {
        super();
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ItemDBUtil imgshow = new ItemDBUtil();
		List list = null;
		List list2 = null;
		
		list = imgshow.list();
		list2 = imgshow.showData();
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
		ServletHandler.forward("index.jsp", request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
