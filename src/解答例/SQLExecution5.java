package 解答例;
import java.util.ArrayList;
class SQLExecution5
{
	public static void main(String[] args)
	{
		//DAOを生成する
		OrderControlDBAccess1_10 dao = new OrderControlDBAccess1_10();
		//DAOから都道府県リストを取得する
		ArrayList<DivisionBean> list = dao.findAllDivisions();
		System.out.println("都道府県名\t売上合計");
		for(DivisionBean bean : list)
		{
			System.out.println(bean.getName() + "\t\\" + bean.getPrice());
		}
	}
}