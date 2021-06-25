package fashionHub.com.DBUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import fashionHub.com.Object.DBConnection;
import fashionHub.com.Object.Image;
import fashionHub.com.Object.Item;

public class ItemDBUtil implements ItemInterface{
public String addItem(Item itm) {
		
		String status = "un";
		
		String itemcode = itm.getItemcode();
		String itemname = itm.getItemname();
		String itemprice = itm.getItemprice();
		String quantity = itm.getQuantity();
		String category = itm.getCategory();
		String description = itm.getDescription();
		String added_by = itm.getAdded_by();
		
		Connection con = null;
		PreparedStatement preparedStatement = null;
		
		try {
			PreparedStatement pst = null;
			con = DBConnection.createConnection();
			pst = con.prepareStatement("SELECT * FROM item WHERE itemcode = ?");
			pst.setString(1, itemcode);
			ResultSet result = pst.executeQuery();
			
			if(result.next()) {
				status = "itemcodeerror";
			}
			
			else {
				try {
					String q = "INSERT INTO item (itemID, name, category, itemcode, unitPrice, description, quantity, added_by) VALUES (null,?,?,?,?,?,?,?)";
					preparedStatement = con.prepareStatement(q);
					
					preparedStatement.setString(1, itemname);
					preparedStatement.setString(2, category);
					preparedStatement.setString(3, itemcode);
					preparedStatement.setString(4, itemprice);
					preparedStatement.setString(5, description);
					preparedStatement.setString(6, quantity);
					preparedStatement.setString(7, added_by);
						
					int i = preparedStatement.executeUpdate();
					
					if(i != 0)
						status =  "success";
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return status;
	}

public List list() {
	  ArrayList list=new ArrayList();
	  Connection conn=null;
	  try {
	    conn=DBConnection.createConnection();
	    PreparedStatement pstmt=conn.prepareStatement("Select * from upload_image_item ORDER BY itemcode DESC");
	    ResultSet rs= pstmt.executeQuery();
	    while (rs.next()) {
	    Image img=new Image();
	    img.setItemcode(rs.getString("itemcode"));
	    img.setImage_name(rs.getString("Image_name"));
	    img.setImage_length(rs.getString("image_length"));
	    Blob blob = rs.getBlob("IMAGE");
	    
	    
	    InputStream inputStream = blob.getBinaryStream();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[4096];
      int bytesRead = -1;
       
      while ((bytesRead = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead);                  
      }
       
      byte[] imageBytes = outputStream.toByteArray();
      String base64Image = Base64.getEncoder().encodeToString(imageBytes);
       
       
      inputStream.close();
      outputStream.close();
      img.setBase64Image(base64Image);
      list.add(img);
	    }
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	  return list;
	}

public List showData() {
	ArrayList ls = new ArrayList();
	Connection con = null;

	try {
		con = DBConnection.createConnection();
		String sqlQ = "SELECT * FROM item ORDER BY itemcode DESC ";
		PreparedStatement st = con.prepareStatement(sqlQ);
		
		ResultSet r = st.executeQuery();
		
		while(r.next()) {
			Item itm = new Item();
			itm.setQuantity(r.getString("quantity"));
			itm.setItemprice(r.getString("unitPrice"));
			itm.setItemname(r.getString("name"));
			itm.setItemcode(r.getString("itemcode"));
			itm.setDescription(r.getString("description"));
			itm.setCategory(r.getString("category"));
			itm.setAdded_by(r.getString("added_by"));
			itm.setAdded_at(r.getString("added_at"));
			ls.add(itm);
		}
		
	}
	catch(SQLException e) {
		System.out.println("SQL Database Error");
		e.printStackTrace();
	}
	catch(Exception e) {
		System.out.println("Exception Error");
		e.printStackTrace();
	}
	return ls;
}

public List getImages(String item_code) {
	ArrayList list=new ArrayList();
	  Connection conn=null;
	  
	  try {
	    conn=DBConnection.createConnection();
	    PreparedStatement pstmt=conn.prepareStatement("Select * from upload_image_item WHERE itemcode = ?");
	    
	    pstmt.setString(1, item_code);
	    ResultSet rs= pstmt.executeQuery();
	    while (rs.next()) {
	    Image img=new Image();
	    img.setItemcode(rs.getString("itemcode"));
	    img.setImage_name(rs.getString("Image_name"));
	    img.setImage_length(rs.getString("image_length"));
	    Blob blob = rs.getBlob("IMAGE");
	    
	    
	    InputStream inputStream = blob.getBinaryStream();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int bytesRead = -1;
     
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);                  
    }
     
    byte[] imageBytes = outputStream.toByteArray();
    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
     
     
    inputStream.close();
    outputStream.close();
    img.setBase64Image(base64Image);
    list.add(img);
	    }
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	  return list;
}

public List getItemData(String item_code) {
	ArrayList ls = new ArrayList();
	Connection con = null;

	try {
		con = DBConnection.createConnection();
		String sqlQ = "SELECT * FROM item WHERE itemcode = ?";
		PreparedStatement st = con.prepareStatement(sqlQ);
		
		st.setString(1, item_code);
		
		ResultSet r = st.executeQuery();
		
		while(r.next()) {
			Item itm = new Item();
			itm.setQuantity(r.getString("quantity"));
			itm.setItemprice(r.getString("unitPrice"));
			itm.setItemname(r.getString("name"));
			itm.setItemcode(r.getString("itemcode"));
			itm.setDescription(r.getString("description"));
			itm.setCategory(r.getString("category"));
			itm.setAdded_by(r.getString("added_by"));
			itm.setAdded_at(r.getString("added_at"));
			ls.add(itm);
		}
		
	}
	catch(SQLException e) {
		System.out.println("SQL Database Error");
		e.printStackTrace();
	}
	catch(Exception e) {
		System.out.println("Exception Error");
		e.printStackTrace();
	}
	return ls;
}

public List SearchItem(String search) {
	ArrayList list=new ArrayList();
	  Connection conn=null;
	  try {
	    conn=DBConnection.createConnection();
	    PreparedStatement pstmt=conn.prepareStatement("Select * from upload_image_item WHERE category = ? ");
	    ResultSet rs= pstmt.executeQuery();
	    pstmt.setString(1, search);
	    
	    while (rs.next()) {
	    Image img=new Image();
	    img.setItemcode(rs.getString("itemcode"));
	    img.setImage_name(rs.getString("Image_name"));
	    img.setImage_length(rs.getString("image_length"));
	    Blob blob = rs.getBlob("IMAGE");
	    
	    
	    InputStream inputStream = blob.getBinaryStream();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[4096];
      int bytesRead = -1;
       
      while ((bytesRead = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead);                  
      }
       
      byte[] imageBytes = outputStream.toByteArray();
      String base64Image = Base64.getEncoder().encodeToString(imageBytes);
       
       
      inputStream.close();
      outputStream.close();
      img.setBase64Image(base64Image);
      list.add(img);
	    }
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	  return list;
	}
}
