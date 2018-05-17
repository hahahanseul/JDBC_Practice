import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Ren1_8_hs {
	public static void main(String[] args) {
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				Scanner sc = new Scanner(System.in);

				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:65534/jdbc?useSSL=false", "user1", "pass1");
					StringBuilder sb = new StringBuilder();
					sb.append("SELECT\n");
					sb.append("  商品ID, \n");
					sb.append("  sum(数量) AS 数量合計 \n");
					sb.append("FROM\n");
					sb.append("  受注明細 \n");
					sb.append("GROUP BY\n");
					sb.append("  商品ID \n");
					sb.append("HAVING\n");
					sb.append("  sum(数量) >= ?; \n");
					sb.append("\n");
					String sql = sb.toString();
					stmt = con.prepareStatement(sql);
					System.out.print("数量合計の基準：");
					int quantity = sc.nextInt();
					while (quantity >0) {
						stmt.setInt(1, quantity);
						rs = stmt.executeQuery();
						System.out.println("商品ID\t\t\t\t数量合計");
						while (rs.next() == true) {
							String pId = rs.getString("商品ID");
							int sum = rs.getInt("数量合計");
							System.out.println(
									pId + "\t\t\t\t" + sum );
						}

						System.out.println();
						System.out.print("数量合計の基準：");
						quantity = sc.nextInt();
					}
				} catch (ClassNotFoundException e) {
					// JDBCドライバが見つからなかった場合の処理
					System.out.println("JDBCドライバが見つかりません。");
					e.printStackTrace();
				} catch (SQLException e) {
					// DBアクセスで例外（エラー）が発生した場合の処理
					System.out.println("DBアクセス時にエラーが発生しました。");
					e.printStackTrace();
				} finally {
					// PreparedStatement, Connectionインスタンスの解放処理
					try {
						if (stmt != null) {
							stmt.close();
						}
						if (con != null) {
							con.close();
						}
					} catch (SQLException e) {
						//  PreparedStatement, Connectionインスタンスの解放に失敗した場合の処理
						System.out.println("DBアクセス時にエラーが発生しました。");
						e.printStackTrace();
					}
					// キーボード入力の後処理
					sc.close();
				}
	}
}
