package fashionHub.com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.FeedbackDBUtil;

@WebServlet("/ViewFeedbackServlet")
public class ViewFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ViewFeedbackServlet() {
        super();
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		FeedbackDBUtil feedback = new FeedbackDBUtil();
		List list = null;
		
		list = feedback.showFeedback();
		System.out.println(list.size());
		
		if(list == null && list.size() == 0) {
			ServletHandler.setErrorMessage("Records Not Found!", request);
		}
		
		session.setAttribute("list", list);
		ServletHandler.forward("viewFeedback.jsp", request, response);
	}

}
