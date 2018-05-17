package 解答例;
import java.io.Serializable;

//DivisionBeanクラス（DTO）の定義
public class DivisionBean implements Serializable
{
	private String name;		//都道府県名
	private int price;			//売上合計

	public DivisionBean(String name, int price)
	{
		setName(name);
		setPrice(price);
	}

	public void setName(String name)	//都道府県名を設定する
	{
		this.name = name;
	}

	public String getName()		//都道府県名を取得する
	{
		return name;
	}

	public void setPrice(int price)	//売上合計を設定する
	{
		this.price = price;
	}

	public int getPrice()			//売上合計を取得する
	{
		return price;
	}
}
