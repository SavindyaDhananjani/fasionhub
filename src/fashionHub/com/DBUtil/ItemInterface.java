package fashionHub.com.DBUtil;

import java.util.List;

import fashionHub.com.Object.Item;

public interface ItemInterface {
	String addItem(Item itm);
	List showData();
	List getImages(String item_code);
	List getItemData(String item_code);
	List SearchItem(String search);
}
