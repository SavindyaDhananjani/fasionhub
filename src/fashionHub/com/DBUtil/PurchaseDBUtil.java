package fashionHub.com.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fashionHub.com.Object.DBConnection;
import fashionHub.com.Object.Purchase;

public class PurchaseDBUtil {

	public String addPurchase(Purchase pr) {
		String firstname = pr.getFirstname();
		String middlename = pr.getMiddlename();
		String companyname = pr.getCompanyname();
		String email = pr.getEmail();
		String tp = pr.getTp();
		String address = pr.getAddress();
		String country = pr.getCountry();
		String raod = pr.getRaod();
		String city = pr.getCity();
		String distric = pr.getDistric();
		String zip = pr.getZip();
		String sfirstname = pr.getSfirstname();
		String slastname = pr.getSlastname();
		String scompanyname = pr.getScompanyname();
		String semail = pr.getSemail();
		String stp = pr.getStp();
		String saddress = pr.getSaddress();
		String scountry = pr.getScompanyname();
		String sroad = pr.getSroad();
		String scity = pr.getScity();
		String sdistric = pr.getSdistric();
		String szip = pr.getSzip();
		String notes = pr.getNotes();
		String subtotal = pr.getSubtotal();
		String tax = pr.getTax();
		String total = pr.getTotal();
		String methodpay = pr.getMethodpay();
		String username = pr.getUsername();
		
		Connection con = null;
		PreparedStatement preparedStatement = null;
		String str = "xx";
		
		try {
			PreparedStatement pst = null;
			con = DBConnection.createConnection();
				try {
					String q = "INSERT INTO purchase (firstname, middlename, email, tp, address, country, road, distric, zip, sfirstname, slastname, scompanyname, semail, stp, saddress, scountry, sroad, scity, sdistric, szip, notes, subtotal, tax, total, methodpay, owner) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					preparedStatement = con.prepareStatement(q);
					
					preparedStatement.setString(1, firstname);
					preparedStatement.setString(2, middlename);
					preparedStatement.setString(3, email);
					preparedStatement.setString(4, tp);
					preparedStatement.setString(5, address);
					preparedStatement.setString(6, country);
					preparedStatement.setString(7, raod);
					preparedStatement.setString(8, distric);
					preparedStatement.setString(9, zip);
					preparedStatement.setString(10, sfirstname);
					preparedStatement.setString(11, slastname);
					preparedStatement.setString(12, scompanyname);
					preparedStatement.setString(13, semail);
					preparedStatement.setString(14, stp);
					preparedStatement.setString(15, saddress);
					preparedStatement.setString(16, scountry);
					preparedStatement.setString(17, sroad);
					preparedStatement.setString(18, scity);
					preparedStatement.setString(19, sdistric);
					preparedStatement.setString(20, szip);
					preparedStatement.setString(21, notes);
					preparedStatement.setString(22, subtotal);
					preparedStatement.setString(23, tax);
					preparedStatement.setString(24, total);
					preparedStatement.setString(25, methodpay);
					preparedStatement.setString(26, username);
					
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
	
}
