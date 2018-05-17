package 解答例;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ren1_2 {
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/受注管理DB", "user1", "pass1");
			stmt = con.createStatement();
			rs = stmt.executeQuery(
					"SELECT 商品ID, 商品名, 単価 FROM 商品マスタ WHERE 単価 BETWEEN '1500' AND '2000' ORDER BY 単価 DESC, 商品ID ASC;");
			System.out.println("商品ID\t商品名\t\t単価");
			while (rs.next() == true) {
				String pId = rs.getString("商品ID");
				String pName = rs.getString("商品名");
				int pPrice = rs.getInt("単価");
				System.out.println(pId + "\t" + pName + "\t\\" + pPrice);
			}
			rs.close();
			stmt.close();
			con.close();
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
