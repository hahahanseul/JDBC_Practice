import java.util.ArrayList;



public class SQLExecution3 {

	public static void main(String[] args) {

		OrderControlDBAccess dao = new OrderControlDBAccess();

		ArrayList<ProductBean> list = dao.findAllProducts();

		System.out.println("商品ID\t\t商品名\t\t\t\t\t\t\t単価");
		for (ProductBean bean : list) {
			System.out.println(bean.getId() + "\t\t"
					+bean.getName()+ "\t\t\t" + bean.getPrice());
		}
	}

}
