package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fashionHub.com.DBUtil.AccountDBUtil;
import fashionHub.com.DBUtil.FeedbackDBUtil;

@WebServlet("/removeFeedback")
public class removeFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public removeFeedback() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("feedbackid");
		FeedbackDBUtil dacc = new FeedbackDBUtil();
		
		int i = (int) dacc.deleteFeedback(id);
		
		if(i != 0) {
			ServletHandler.setSuccessMessage("Deleted Successfull!", request);
			request.getRequestDispatcher("ViewFeedback").forward(request, response);
		}
		else {
			ServletHandler.setErrorMessage("Something went wrong!, Please try again!", request);
			request.getRequestDispatcher("ViewFeedback").forward(request, response);
		}
	}

}
