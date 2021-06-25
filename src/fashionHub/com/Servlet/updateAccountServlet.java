package fashionHub.com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.AccountDBUtil;
import fashionHub.com.Object.Person;

@WebServlet("/updateAccountServlet")
public class updateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public updateAccountServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println(username);
						
		//Create a new session to pass the username
		HttpSession sessionpass = request.getSession();
		sessionpass.setAttribute("user", username);
		
		//Copying all the input parameters in to local variables
		String firstname = request.getParameter("firstname");
		String middlename = request.getParameter("middlename");
		String lastname = request.getParameter("lastname");
		String address = request.getParameter("address");
		String birthday = request.getParameter("birthday");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		
		Person p = new Person();
		AccountDBUtil acc = new AccountDBUtil();
		
		//Send Data to RegiterData Object
		p.setFirstname(firstname);
		p.setMiddlename(middlename);
		p.setLastname(lastname);
		p.setAddress(address);
		p.setDob(birthday);
		p.setGender(gender);
		p.setEmail(email);
		p.setTp(telephone);
		p.setUsername(username);
		
		int x = acc.UpdateUser(p);
		
		if(x != 0) {
			ServletHandler.setSuccessMessage("Account updated Successfully!", request);
			ServletHandler.forward("success.jsp", request, response);
		}
		else {
			ServletHandler.setSuccessMessage("Error! Please Try Again", request);
			ServletHandler.forward("unsuccess.jsp", request, response);
		}
	}

}
