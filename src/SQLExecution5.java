import java.util.ArrayList;

public class SQLExecution5 {
	public static void main(String[] args) {
		OrderControlDBAccess dao = new OrderControlDBAccess();
		ArrayList<DivisionBean> list = dao.findAllDivisions();
		System.out.println("都道府県名\t\t売上合計");
		for(DivisionBean bean : list) {
			System.out.println( bean.getName() + "\t\t\t\\"+bean.getPrice());

		}
	}
}
