import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Hanseul Kim
 * */
public class OrderControlDBAccess {
	private Connection createConnection() {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/jdbc?useSSL=false",
					"user1",
					"pass1");

		} catch (ClassNotFoundException e) {
			System.out.println("JDBCドライバが見つかりません。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("DB切断時にエラーが発生しました。");
			e.printStackTrace();
		}
	}

	public ArrayList<ProductBean> findAllProducts() {
		Connection con = createConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductBean> list = new ArrayList<>();
		try {
			if (con != null) {
				String sql = "SELECT 商品ID,rpad(商品名,10,'　') as 商品名,単価 FROM 商品マスタ;";
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next() == true) {
					String pId = rs.getString("商品ID");
					String pName = rs.getString("商品名");
					int pPrice = rs.getInt("単価");
					ProductBean bean = new ProductBean(pId, pName, pPrice);
					list.add(bean);
				}
			}
		} catch (SQLException e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		closeConnection(con);
		return list;
	}

	public ArrayList<CustomerBean> findAllCustomers() {
		Connection con = createConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<CustomerBean> list = new ArrayList<>();
		try {
			if (con != null) {
				String sql = "SELECT 顧客ID,顧客名 FROM 顧客マスタ;";
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next() == true) {
					int cId = rs.getInt("顧客ID");
					String cName = rs.getString("顧客名");
					CustomerBean bean = new CustomerBean(cId, cName);
					list.add(bean);
				}
			}
		} catch (SQLException e) {
			System.out.println("DBアクセス時にエラーが発生しました。(顧客検索)。");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		closeConnection(con);
		return list;
	}
	public ArrayList<DivisionBean> findAllDivisions() {
		Connection con = createConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<DivisionBean> list = new ArrayList<>();
		try {
			if (con != null) {
				StringBuilder sb = new StringBuilder();
				sb.append("SELECT\n");
				sb.append("  rpad(c.都道府県,4,'　') AS 都道府県名, \n");
				sb.append("  sum(t2.数量 * t2.単価) AS 売上合計 \n");
				sb.append("FROM\n");
				sb.append("  顧客マスタ c \n");
				sb.append("  INNER JOIN ( \n");
				sb.append("    SELECT\n");
				sb.append("      o.受注ID, \n");
				sb.append("      o.受注日, \n");
				sb.append("      o.顧客ID, \n");
				sb.append("      t.明細ID, \n");
				sb.append("      t.商品ID, \n");
				sb.append("      t.数量, \n");
				sb.append("      t.単価 \n");
				sb.append("    FROM\n");
				sb.append("      受注 o \n");
				sb.append("      INNER JOIN ( \n");
				sb.append("        SELECT\n");
				sb.append("          d.明細ID, \n");
				sb.append("          d.受注ID, \n");
				sb.append("          d.商品ID, \n");
				sb.append("          d.数量, \n");
				sb.append("          p.単価 \n");
				sb.append("        FROM\n");
				sb.append("          受注明細 d \n");
				sb.append("          INNER JOIN 商品マスタ p \n");
				sb.append("            ON d.商品ID = p.商品ID\n");
				sb.append("      ) t \n");
				sb.append("        ON o.受注ID = t.受注ID\n");
				sb.append("  ) t2 \n");
				sb.append("    ON c.顧客ID = t2.顧客ID \n");
				sb.append("GROUP BY\n");
				sb.append("  c.都道府県 \n");
				sb.append("ORDER BY\n");
				sb.append("  売上合計 DESC; \n");
				sb.append("\n");
				String sql = sb.toString();
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next() == true) {
					String division = rs.getString("都道府県名");
					int sum = rs.getInt("売上合計");
					DivisionBean bean = new DivisionBean(division, sum);
					list.add(bean);
				}
			}
		} catch (SQLException e) {
			System.out.println("DBアクセス時にエラーが発生しました。(顧客検索)。");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		closeConnection(con);
		return list;
	}
}
