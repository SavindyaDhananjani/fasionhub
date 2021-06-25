package fashionHub.com.DBUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Vector;

import fashionHub.com.Object.DBConnection;
import fashionHub.com.Object.Image;
import fashionHub.com.Object.Person;
import fashionHub.com.Object.Register;
import fashionHub.com.Object.RegisterData;
import fashionHub.com.Object.User;

public class AccountDBUtil implements AccountInterface{
	public long deleteAcc(String username) {
		  int i = 0;
		  try {
			Connection con = DBConnection.createConnection();
		    PreparedStatement stmt = con.prepareStatement("DELETE FROM user WHERE username=?");
		    stmt.setString(1, username);
		    i = stmt.executeUpdate();
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  return i;
		}
	
	
	public Image getImage(String username) throws SQLException, IOException {
		Connection con = null;
		Image img = null;
		
		try {
			
			con = DBConnection.createConnection();
			String sql = "SELECT * FROM upload_image_user WHERE username = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, username);
	        ResultSet result = statement.executeQuery();
        
        
       
	        if (result.next()) {
	        	img = new Image();
	        	
	        	String uname = result.getString("username");
	        	String image_name = result.getString("image_name");
	        	String image_length = result.getString("image_length");
	            Blob blob = result.getBlob("IMAGE");
	             
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
	             
	            img.setUsername(username);
	            img.setImage_name(image_name);
	            img.setImage_length(image_length);
	            img.setBase64Image(base64Image);
	        }          
         
		} catch (SQLException | IOException ex) {
			ex.printStackTrace();
			throw ex;
		}
        
        return img;
	}
	
	public List displayPerson(String username) {
		ArrayList ls = new ArrayList();
		Connection con = null;

		try {
			con = DBConnection.createConnection();
			String sqlQ = "SELECT * FROM user WHERE username = ?";
			PreparedStatement st = con.prepareStatement(sqlQ);
			
			st.setString(1, username);
			
			ResultSet r = st.executeQuery();
			
			while(r.next()) {
				Person p = new Person();
				p.setUserID(r.getInt("userid"));
				p.setFirstname(r.getString("firstName"));
				p.setMiddlename(r.getString("middleName"));
				p.setLastname(r.getString("lastName"));
				p.setAddress(r.getString("address"));
				p.setDob(r.getString("dob"));
				p.setGender(r.getString("gender"));
				p.setUsername(r.getString("username"));
				p.setPassword(r.getString("password"));
				p.setEmail(r.getString("email"));
				p.setTp(r.getString("tp"));
				p.setRole(r.getString("role"));
				ls.add(p);
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
	
	public User validateLogin(String username, String password) {
		Connection con = null;
		User usr = null;
		
		try {
			//Create Connection to DB
			con = DBConnection.createConnection();
			
			//SQL Query
			String sqlQ = "SELECT * FROM user WHERE username = ? AND password = ?";
			PreparedStatement st = con.prepareStatement(sqlQ);
			
			st.setString(1, username);
			st.setString(2, password);
			
			ResultSet r = st.executeQuery();
			
			if (r.next()) {
				usr = new User();
				usr.setUsername(username);
				usr.setRole(r.getString("role"));
				usr.setPassword(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usr;
	}
	
	public void updateStatus(String username) {
		Connection con = null;
		con = DBConnection.createConnection();
	    try {
		      Connection conn = DBConnection.createConnection();
		      PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET status = 1 WHERE username = ?");
		      preparedStatement.setString(1, username);
		      preparedStatement.executeUpdate();
		      con.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public int updateStatusLogout(String username) {
		Connection con = DBConnection.createConnection();
		int i = 0;
	    try {
		      Connection conn = DBConnection.createConnection();
		      PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET status = 0 WHERE username = ?");
		      preparedStatement.setString(1, username);
		      i = preparedStatement.executeUpdate();
		      con.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return i;
	}
	
	
	public String registerData(RegisterData rd) {
		String firstname = rd.getFirstname();
		String middlename = rd.getMiddlename();
		String lastname = rd.getLastname();
		String address = rd.getAddress();
		String birthday = rd.getBirthday();
		String gender = rd.getGender();
		String email = rd.getEmail();
		String telephone = rd.getTelephone();
		boolean status = true;
		String username = rd.getUsername();

		Connection con = null;
		PreparedStatement preparedStatement = null;
		
		try {
			PreparedStatement pst = null;
			con = DBConnection.createConnection();
			pst = con.prepareStatement("SELECT * FROM user WHERE email = ?");
			pst.setString(1, email);
			ResultSet result = pst.executeQuery();
			
			if(result.next()) {
				System.out.println("Email is already taken!");
			}
				
			pst = null;
			con = DBConnection.createConnection();
			pst = con.prepareStatement("SELECT * FROM user WHERE tp = ?");
			pst.setString(1, telephone);
			result = pst.executeQuery();
				
			if(result.next()) {
				System.out.println("Phone Number is already taken!");
			}
			
			else {
				try {
					String q = "UPDATE user SET firstName = ?, middleName = ?, lastName = ?, address = ?, dob = ?, gender = ?, email = ?, tp = ?, status = ? WHERE username = ?";
					preparedStatement = con.prepareStatement(q);
					
					preparedStatement.setString(1, firstname);
					preparedStatement.setString(2, middlename);
					preparedStatement.setString(3, lastname);
					preparedStatement.setString(4, address);
					preparedStatement.setString(5, birthday);
					preparedStatement.setString(6, gender);
					preparedStatement.setString(7, email);
					preparedStatement.setString(8, telephone);
					preparedStatement.setBoolean(9, status);
					preparedStatement.setString(10, username);
						
					int i = preparedStatement.executeUpdate();
					
					if(i != 0)
						return "SUCCESS";
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		return "Error!";
	}
	
	
	//Register Customer Data
		public String registerUser(Register r) {
			String username = r.getUsername();
			String password = r.getPassword();
			String role = r.getRole();
			
			Connection con = null;
			PreparedStatement preparedStatement = null;
			
			try {
				PreparedStatement pst = null;
				con = DBConnection.createConnection();
				pst = con.prepareStatement("SELECT * FROM user WHERE username = ?");
				pst.setString(1, username);
				ResultSet result = pst.executeQuery();
				
				if(result.next()) {
					System.out.println("Username is already taken!");
				}
				else {
					try {
						String q = "INSERT INTO user(userid, username, password, role) VALUES (NULL, ?, ?, ?)";
						preparedStatement = con.prepareStatement(q);
						
						preparedStatement.setString(1, username);
						preparedStatement.setString(2, password);
						preparedStatement.setString(3, role);
						
						int i = preparedStatement.executeUpdate();
						
						if(i != 0)
							return "SUCCESS";
					}
					catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			return "Error!";
		}
		
		//Register Customer Image
		public Vector ImageList() throws SQLException
		{
		Vector vList=new Vector();

		try
		{
		Connection con = null;
		con = DBConnection.createConnection();
		Statement stmt=con.createStatement();
		String strSql= "select Image_id,Image_name from upload_image_user";
		System.out.println("ImageList query–" + strSql);
		ResultSet rs=stmt.executeQuery(strSql);

		while(rs.next()){
			String temp[]=new String[2];
			temp[0]=rs.getString("Image_id");
			temp[1]=rs.getString("Image_name");
			vList.add(temp);
		}

		}
		catch(Exception e) {
			System.err.print("ImageList Exception : " + e);
			System.err.println("ImageList Exception : " + e.getMessage());
		}

		return vList;
		}
		
		
		
		public List findData(String username) {
			ArrayList ls = new ArrayList();
			Connection con = null;

			try {
				con = DBConnection.createConnection();
				String sqlQ = "SELECT * FROM user WHERE username = ?";
				PreparedStatement st = con.prepareStatement(sqlQ);
				
				st.setString(1, username);
				
				ResultSet r = st.executeQuery();
				
				while(r.next()) {
					Person p = new Person();
					p.setUserID(r.getInt("userid"));
					p.setFirstname(r.getString("firstName"));
					p.setMiddlename(r.getString("middleName"));
					p.setLastname(r.getString("lastName"));
					p.setAddress(r.getString("address"));
					p.setDob(r.getString("dob"));
					p.setGender(r.getString("gender"));
					p.setUsername(r.getString("username"));
					p.setPassword(r.getString("password"));
					p.setEmail(r.getString("email"));
					p.setTp(r.getString("tp"));
					p.setRole(r.getString("role"));
					ls.add(p);
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
		
		public int UpdateUser(Person p) {
		    int i = 0;
		    try {
		      Connection conn = DBConnection.createConnection();
		      PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET firstName = ?, middleName = ?, lastName = ?, address = ?, dob = ?, gender = ?, email = ?, tp = ? WHERE username = ?");
		      preparedStatement.setString(1, p.getFirstname());
		      preparedStatement.setString(2, p.getMiddlename());
		      preparedStatement.setString(3, p.getLastname());
		      preparedStatement.setString(4, p.getAddress());
		      preparedStatement.setString(5, p.getDob());
		      preparedStatement.setString(6, p.getGender());
		      preparedStatement.setString(7, p.getEmail());
		      preparedStatement.setString(8, p.getTp());
		      preparedStatement.setString(9, p.getUsername());
		      i = preparedStatement.executeUpdate();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return i;
		  }


}
