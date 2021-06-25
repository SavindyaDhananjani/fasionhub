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

import fashionHub.com.Object.DBConnection;

@WebServlet("/insertImage")
public class insertImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public insertImage() {
        super();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		response.setContentType("text/html;charset=UTF-8");

    		try
    		{
    		String strpath=request.getParameter("Image");
    		String filepath=strpath.substring(strpath.lastIndexOf("\\")+1);
    		File imgfile = new File(strpath);
    		FileInputStream fin = new FileInputStream(imgfile);
    		Connection con = null;
    		con = DBConnection.createConnection();
    		PreparedStatement pre = con.prepareStatement("insert into upload_image_user(image,image_name,image_length) values(?,?,?)");
    		pre.setBinaryStream(1,fin,(int)imgfile.length());
    		pre.setString(2,filepath);
    		pre.setLong(3,imgfile.length());
    		pre.executeUpdate();
    		pre.close();
    		String L_url1=response.encodeRedirectURL("myUpload.jsp");
    		response.sendRedirect(L_url1);

    		}
    		catch(Exception ex){
    		System.out.println("Exception in InsertImage servlet " + ex.getMessage());
    		}
    		}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	public String getServletInfo() {
		return "Short description";
		}// </editor-fold>

}
