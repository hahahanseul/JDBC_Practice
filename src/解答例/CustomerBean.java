package 解答例;
import java.io.Serializable;		// シリアライゼーション用

//CustomerBeanクラス（DTO）の定義
public class CustomerBean implements Serializable
{
	// インスタンス変数の宣言
	private String id;				// 顧客ID
	private String name;			// 顧客名

	// コンストラクタ（顧客ID、顧客名）
	public CustomerBean(String id, String name)
	{
		setId(id);
		setName(name);
	}

	// id（顧客ID）の設定（セッター）
	public void setId(String id)
	{
		this.id = id;
	}

	// id（顧客ID）の取得（ゲッター）
	public String getId()
	{
		return id;
	}

	// name（顧客名）の設定（セッター）
	public void setName(String name)
	{
		this.name = name;
	}

	// name（顧客名）の取得（ゲッター）
	public String getName()
	{
		return name;
	}
}
