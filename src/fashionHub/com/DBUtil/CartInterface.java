package fashionHub.com.DBUtil;

import java.util.List;

public interface CartInterface {
	String addToCart(String username, String item_code);
	List getImages(String username);
	List getCart(String username);
	String removeCart(String item_code);
	String addToCart(String username, String item_code, int count);
	void removeCartAll(String username);
}
