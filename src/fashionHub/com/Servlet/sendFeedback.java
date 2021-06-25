package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fashionHub.com.DBUtil.FeedbackDBUtil;
import fashionHub.com.Object.Feedback;

@WebServlet("/sendFeedback")
public class sendFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public sendFeedback() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String company = request.getParameter("company");
		String message = request.getParameter("message");
		
		Feedback fb = new Feedback();
		FeedbackDBUtil fbd = new FeedbackDBUtil();
		
		fb.setName(name);
		fb.setEmail(email);
		fb.setSubject(subject);
		fb.setCompany(company);
		fb.setMessage(message);
		
		String sts = fbd.sendFeedback(fb);
		
		if(sts.equals("SUCCESS")) {
			ServletHandler.setSuccessMessage("Send Successfully!", request);
			ServletHandler.forward("contact.jsp", request, response);
		}
		else {
			ServletHandler.setErrorMessage("Send Unsuccessful! Please Try Again!", request);
			ServletHandler.forward("contact.jsp", request, response);
		}
	}

}
