import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author Hanseul Kim
 * */
public class SQLExecution_Renshu {
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("商品ID入力：");
			String searchId= sc.next();
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/jdbc?useSSL=false", "user1", "pass1");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select 商品ID,rpad(商品名,10,'　') as 商品名, 単価 from 商品マスタ where 商品ID = '"+searchId+"' ");

			if (rs.next() == true) {
				String pId = rs.getString("商品ID");
				String pName = rs.getString("商品名");
				int pPrice = rs.getInt("単価");
				System.out.println("商品ID\t\t商品名\t\t\t\t\t\t\t単価");
				System.out.println(
						pId + "\t\t" + pName + "\t\t\t" + pPrice);
			} else{
				System.out.println("該当の商品IDは存在しません。");
			}
			sc.close();
		} catch (ClassNotFoundException e) {
			System.out.println("JDBCドライバが見つかりません。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}

		}
	}
}
