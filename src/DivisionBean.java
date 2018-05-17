import java.io.Serializable;

public class DivisionBean implements Serializable{
	private String name;
	private int price;

	public DivisionBean(String name, int price) {
		setName(name);
		setPrice(price);
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
