import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Hanseul Kim
 * */
public class SQLExecution2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/jdbc?useSSL=false", "user1", "pass1");
			String sql = "select 商品ID, rpad(商品名,10,'　') as 商品名, 単価 from 商品マスタ where 商品ID like ?;";
			stmt = con.prepareStatement(sql);
			String keyword;
			System.out.print("商品IDの検索キーワード：");
			keyword = sc.next();
			while (keyword.equals("end") == false) {
				stmt.setString(1, keyword + "%");
				rs = stmt.executeQuery();
				System.out.println("商品ID\t\t商品名\t\t\t\t\t\t\t単価");
				while (rs.next() == true) {
					String pId = rs.getString("商品ID");
					String pName = rs.getString("商品名");
					int pPrice = rs.getInt("単価");
					System.out.println(
							pId + "\t\t" + pName + "\t\t\t" + pPrice);
				}
				rs.close();
				System.out.println();

				System.out.print("商品IDお検索キーワード：");
				keyword = sc.next();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("JDBCドライバが見つかりません。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}

		}
		sc.close();
	}
}
