package fashionHub.com.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import fashionHub.com.Object.DBConnection;

public class GetImage {
	public Vector ImageList() throws SQLException
	{
	Vector vList=new Vector();

	try
	{
	Connection con = null;
	con = DBConnection.createConnection();
	Statement stmt=con.createStatement();
	String strSql= "select username,Image_name from upload_image_user";
	System.out.println("ImageList query–" + strSql);
	ResultSet rs=stmt.executeQuery(strSql);

	while(rs.next()){
		String temp[]=new String[2];
		temp[0]=rs.getString("username");
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
}
