package fashionHub.com.Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.DBUtil.AccountDBUtil;
import fashionHub.com.Object.DBConnection;
import fashionHub.com.Object.RegisterData;


@WebServlet("/registerCustomerDataServlet")
public class registerCustomerDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public registerCustomerDataServlet() {
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
		
		//Create RegisterData Object
		RegisterData rd = new RegisterData();
		
		//Send Data to RegiterData Object
		rd.setFirstname(firstname);
		rd.setMiddlename(middlename);
		rd.setLastname(lastname);
		rd.setAddress(address);
		rd.setBirthday(birthday);
		rd.setGender(gender);
		rd.setEmail(email);
		rd.setTelephone(telephone);
		rd.setUsername(username);
		
		//Create RegisterDataDB Object
		AccountDBUtil rddb = new AccountDBUtil();
		
		//send RegiserData Object data to RegisterDBUtil Object
		String userDataRegistered = rddb.registerData(rd);
		
		if(userDataRegistered.equals("SUCCESS")) {
			request.getRequestDispatcher("loadImage").forward(request, response);
			System.out.println("Registered Successfully!");
		}
		else {
			request.setAttribute("errMessage",  userDataRegistered);
			request.getRequestDispatcher("/account.jsp").forward(request, response);
			System.out.println("Registration Failed!");
		}
		
		//Upload Photo to database
		try
		{
			String strpath=request.getParameter("AccountPhoto");
			String filepath=strpath.substring(strpath.lastIndexOf("\\")+1);
			File imgfile = new File(strpath);
			FileInputStream fin = new FileInputStream(imgfile);
			Connection con = null;
			con = DBConnection.createConnection();
			PreparedStatement pre = con.prepareStatement("INSERT INTO upload_image_user(username, image,image_name,image_length) VALUES(?,?,?,?)");
			pre.setString(1, username);
			pre.setBinaryStream(2,fin,(int)imgfile.length());
			pre.setString(3,filepath);
			pre.setLong(4,imgfile.length());
			pre.executeUpdate();
			pre.close();
			//String L_url1=response.encodeRedirectURL("myUpload.jsp");
			//response.sendRedirect(L_url1);

		}
		catch(Exception ex){
			System.out.println("Exception in InsertImage servlet " + ex.getMessage());
		}
	}
	

}
