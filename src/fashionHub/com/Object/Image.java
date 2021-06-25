package fashionHub.com.Object;

public class Image {
	private String username;
	private String image_name;
	private String image_length;
	private byte[] image;
	private String itemcode;
	
	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String getImage_length() {
		return image_length;
	}

	public void setImage_length(String image_length) {
		this.image_length = image_length;
	}
	
	//@Column(name = "image")
	public byte[] getImage() {
		return this.image;
	}
	
	private String base64Image;
	
	public String getBase64Image;
	
	public String getBase64Image() {
		return base64Image;
	}
	
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
}
