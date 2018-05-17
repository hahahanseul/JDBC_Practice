import java.io.Serializable;

public class ProductBean implements Serializable {

	private String id;
	private String name;
	private int price;

	public ProductBean(String id, String name, int price) {
		setId(id);
		setName(name);
		setPrice(price);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
