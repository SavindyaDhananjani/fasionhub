package fashionHub.com.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import fashionHub.com.Object.Image;
import fashionHub.com.Object.Person;
import fashionHub.com.Object.Register;
import fashionHub.com.Object.RegisterData;
import fashionHub.com.Object.User;

public interface AccountInterface {
	long deleteAcc(String username);
	Image getImage(String username) throws SQLException, IOException;
	List displayPerson(String username);
	User validateLogin(String username, String password);
	void updateStatus(String username);
	String registerData(RegisterData rd);
	int updateStatusLogout(String username);
	String registerUser(Register r);
	Vector ImageList() throws SQLException;
	List findData(String username);
	int UpdateUser(Person r);
}
