package fashionHub.com.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fashionHub.com.Object.DBConnection;
import fashionHub.com.Object.Feedback;

public class FeedbackDBUtil implements FeedbackInterface{
	public String sendFeedback(Feedback fb) {
		String name = fb.getName();
		String email = fb.getEmail();
		String subject = fb.getSubject();
		String company = fb.getCompany();
		String message = fb.getMessage();
		
		Connection con = null;
		PreparedStatement preparedStatement = null;
		String str = "xx";
		
		try {
			PreparedStatement pst = null;
			con = DBConnection.createConnection();
				try {
					String q = "INSERT INTO feedback(feedbackid, name, email, subject, company, message) VALUES (NULL, ?, ?, ?, ?, ?)";
					preparedStatement = con.prepareStatement(q);
					
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, email);
					preparedStatement.setString(3, subject);
					preparedStatement.setString(4, company);
					preparedStatement.setString(5, message);
					
					int i = preparedStatement.executeUpdate();
					
					if(i != 0)
						str = "SUCCESS";
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	public List showFeedback() {
		ArrayList ls = new ArrayList();
		Connection con = null;

		try {
			con = DBConnection.createConnection();
			String sqlQ = "SELECT * FROM feedback";
			PreparedStatement st = con.prepareStatement(sqlQ);
			
			ResultSet r = st.executeQuery();
			
			while(r.next()) {
				Feedback fb = new Feedback();
				fb.setSubject(r.getString("subject"));
				fb.setName(r.getString("name"));
				fb.setEmail(r.getString("email"));
				fb.setCompany(r.getString("company"));
				fb.setMessage(r.getString("message"));
				fb.setId(r.getString("feedbackid"));
				ls.add(fb);
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

	public int deleteFeedback(String id) {
		int i = 0;
		  try {
			Connection con = DBConnection.createConnection();
		    PreparedStatement stmt = con.prepareStatement("DELETE FROM feedback WHERE feedbackid=?");
		    stmt.setString(1, id);
		    i = stmt.executeUpdate();
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  return i;
	}
}
