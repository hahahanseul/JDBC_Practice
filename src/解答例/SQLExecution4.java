package 解答例;
import java.util.ArrayList;			// コレクション用

class SQLExecution4
{
	public static void main(String[] args)
	{
		// DAOを生成
		OrderControlDBAccess1_9 dao
				= new OrderControlDBAccess1_9();

		// DAOから顧客リストを取得
		ArrayList<CustomerBean> list
				= dao.findAllCustomers();

		// 顧客リストの情報を表示
		System.out.println("顧客ID\t顧客名");			// 見出し出力
		for(CustomerBean bean : list)					// 顧客リストにインスタンスがある間
		{
			System.out.println(bean.getId() + "\t"
									+ bean.getName());	// 顧客IDと顧客名を表示
		}
	}
}
