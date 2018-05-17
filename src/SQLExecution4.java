import java.util.ArrayList;

/**
 * @author Hanseul Kim
 * */
public class SQLExecution4 {
	public static void main(String[] args) {
		OrderControlDBAccess dao = new OrderControlDBAccess();
		ArrayList<CustomerBean> list = dao.findAllCustomers();
		System.out.println("顧客ID\t\t顧客名");
		for(CustomerBean bean : list) {
			System.out.println(bean.getId() +"\t\t\t" + bean.getName());
		}
	}
}
