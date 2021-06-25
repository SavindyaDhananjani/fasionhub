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

import fashionHub.com.Object.Cart;
import fashionHub.com.Object.DBConnection;
import fashionHub.com.Object.Image;
import fashionHub.com.Object.Item;

public class CartDBUtil implements CartInterface{
	public String addToCart(String username, String item_code) {
		String status = "un";
		
		Connection con = null;
		PreparedStatement preparedStatement = null;
		
		try {
			PreparedStatement pst = null;
			con = DBConnection.createConnection();
			pst = con.prepareStatement("SELECT * FROM cart WHERE item_code = ? AND username = ?");
			pst.setString(1, item_code);
			pst.setString(2, username);
			ResultSet result = pst.executeQuery();
			
			if(result.next()) {
				status = "itemavailable";
			}
			
			else {
				try {
					String q = "INSERT INTO cart (cartID, username, item_code, quantity) VALUES (null,?,?,1)";
					preparedStatement = con.prepareStatement(q);
					
					preparedStatement.setString(1, username);
					preparedStatement.setString(2, item_code);
						
					int i = preparedStatement.executeUpdate();
					
					if(i != 0)
						status = "success";
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
	
	public List getImages(String username) {
		  ArrayList list=new ArrayList();
		  Connection conn=null;
		  
		  try {
		    conn=DBConnection.createConnection();
		    PreparedStatement pstmt=conn.prepareStatement("Select * from upload_image_item WHERE itemcode IN (SELECT item_code FROM cart WHERE username = ?)");
		    
		    pstmt.setString(1, username);
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
	
	public List getItemData(String username) {
		ArrayList ls = new ArrayList();
		Connection con = null;

		try {
			con = DBConnection.createConnection();
			String sqlQ = "SELECT * FROM item WHERE itemcode IN (SELECT item_code FROM cart WHERE username = ?)";
			PreparedStatement st = con.prepareStatement(sqlQ);
			
			st.setString(1, username);
			
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
		finally {
			//NOTHING YET
		}
		return ls;
	}

	public List getCart(String username) {
		ArrayList ls = new ArrayList();
		Connection con = null;

		try {
			con = DBConnection.createConnection();
			String sqlQ = "SELECT * FROM cart WHERE username = ?";
			PreparedStatement st = con.prepareStatement(sqlQ);
			
			st.setString(1, username);
			
			ResultSet r = st.executeQuery();
			
			while(r.next()) {
				Cart crt = new Cart();
				crt.setUsername(r.getString("username"));
				crt.setQuantity(r.getInt("quantity"));
				crt.setItemcode(r.getString("item_code"));
				crt.setAdded_at(r.getString("added_at"));
				ls.add(crt);
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

	public String removeCart(String item_code) {
		String str = null;
		  try {
			Connection con = DBConnection.createConnection();
		    PreparedStatement stmt = con.prepareStatement("DELETE FROM cart WHERE item_code=?");
		    stmt.setString(1, item_code);
		    int i = stmt.executeUpdate();
		    if(i != 0) {
		    	str = "success";
		    }
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  return str;
	}

	public String addToCart(String username, String item_code, int count) {
		String status = "un";
		
		Connection con = null;
		PreparedStatement preparedStatement = null;
		
		try {
			PreparedStatement pst = null;
			con = DBConnection.createConnection();
			pst = con.prepareStatement("SELECT * FROM cart WHERE item_code = ? AND username = ?");
			pst.setString(1, item_code);
			pst.setString(2, username);
			ResultSet result = pst.executeQuery();
			
			if(result.next()) {
				status = "itemavailable";
			}
			
			else {
				try {
					String q = "INSERT INTO cart (cartID, username, item_code, quantity) VALUES (null,?,?,?)";
					preparedStatement = con.prepareStatement(q);
					
					preparedStatement.setString(1, username);
					preparedStatement.setString(2, item_code);
					preparedStatement.setInt(3, count);
						
					int i = preparedStatement.executeUpdate();
					
					if(i != 0)
						status = "success";
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

	public void removeCartAll(String username) {
		String str = null;
		  try {
			Connection con = DBConnection.createConnection();
		    PreparedStatement stmt = con.prepareStatement("DELETE FROM cart WHERE username=?");
		    stmt.setString(1, username);
		    int i = stmt.executeUpdate();
		    if(i != 0) {
		    	str = "success";
		    }
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
	}
}
