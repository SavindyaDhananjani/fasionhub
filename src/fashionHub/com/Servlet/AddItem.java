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

import fashionHub.com.DBUtil.ItemDBUtil;
import fashionHub.com.Object.DBConnection;
import fashionHub.com.Object.Item;
import fashionHub.com.Object.RegisterData;

@WebServlet("/AddItem")
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddItem() {
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
		String itemcode = request.getParameter("itemcode");
		String itemname = request.getParameter("itemname");
		String itemprice = request.getParameter("itemprice");
		String quantity = request.getParameter("quantity");
		String category = request.getParameter("category");
		String description = request.getParameter("description");
		
		//Create Item Object
		Item itm = new Item();
		
		//Send Data to Item Object
		itm.setItemcode(itemcode);
		itm.setItemname(itemname);
		itm.setItemprice(itemprice);
		itm.setQuantity(quantity);
		itm.setCategory(category);
		itm.setDescription(description);
		itm.setAdded_by(username);
		
		//Create Add Item Object
		ItemDBUtil addi = new ItemDBUtil();
		
		String sts = addi.addItem(itm);
		
		if(sts.equals("success")) {
			
			ServletHandler.setSuccessMessage("Item Added Successfully!", request);
			ServletHandler.forward("addItem.jsp", request, response);
		}
		else if(sts.equals("itemcodeerror")) {
			ServletHandler.setErrorMessage("Item Code is Already Taken!", request);
			ServletHandler.forward("addItem.jsp", request, response);
		}
		else {
			ServletHandler.setErrorMessage("Item could no be added! Please try again!", request);
			ServletHandler.forward("addItem.jsp", request, response);
		}
		
		//Upload Photo to database
		try
		{
			String strpath=request.getParameter("itemImage");
			String filepath=strpath.substring(strpath.lastIndexOf("\\")+1);
			File imgfile = new File(strpath);
			FileInputStream fin = new FileInputStream(imgfile);
			Connection con = null;
			con = DBConnection.createConnection();
			PreparedStatement pre = con.prepareStatement("INSERT INTO upload_image_item(itemcode, IMAGE, Image_name,image_length, category) VALUES(?,?,?,?,?)");
			pre.setString(1, category);
			pre.setBinaryStream(2,fin,(int)imgfile.length());
			pre.setString(3,filepath);
			pre.setLong(4,imgfile.length());
			pre.setString(5, itemname);
			pre.executeUpdate();
			pre.close();
			String L_url1=response.encodeRedirectURL("myUpload.jsp");
			response.sendRedirect(L_url1);

		}
			catch(Exception ex){
			System.out.println("Exception in InsertImage servlet " + ex.getMessage());
		}
	}

}
