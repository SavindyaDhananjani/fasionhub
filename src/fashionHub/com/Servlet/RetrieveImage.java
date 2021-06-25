package fashionHub.com.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fashionHub.com.Object.DBConnection;

@WebServlet("/RetrieveImage")
public class RetrieveImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RetrieveImage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		InputStream sImage;
		
		//Get session data
		HttpSession session = request.getSession(false);
		String username = (String)session.getAttribute("user");
		System.out.println(username);
		
		//Create a new session to pass the username
		HttpSession sessionpass = request.getSession();
		sessionpass.setAttribute("user", username);
		
		try{

		//String id =request.getParameter("Image_id");
		Connection con = null;
		con = DBConnection.createConnection();
		Statement stmt=con.createStatement();
		String strSql= "select IMAGE from upload_image_user where username='"+username+"'";
		System.out.println("inside servlet Sql–>"+strSql);
		rs=stmt.executeQuery(strSql);
		if(rs.next()) {
			byte[] bytearray = new byte[1048576];
			int size=0;
			sImage = rs.getBinaryStream(1);
			response.reset();
			response.setContentType("image/jpeg");
			
			while((size=sImage.read(bytearray))!= -1 ){
				response.getOutputStream().
				write(bytearray,0,size);
			}
		}
		} catch (Exception e){
			e.printStackTrace();
			}
		}

}
