import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Ren1_7_hs {
	public static void main(String[] args) {
		// インスタンス変数の宣言／初期化
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// キーボード入力の準備
		Scanner sc = new Scanner(System.in);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/jdbc?useSSL=false", "user1", "pass1");
			String sql = "select o.受注日, c.顧客ID, c.顧客名,c.フリガナ from 顧客マスタ c inner join 受注 o on c.顧客ID = o. 顧客ID where o.受注日 like ? order by o.受注日 desc;";
			stmt = con.prepareStatement(sql);
			System.out.print("受注日の西暦年：");
			String year = sc.next();
			while (!year.equals("0")) {
				stmt.setString(1, year + "%");
				rs = stmt.executeQuery();
				System.out.println("受注日\t\t\t\t顧客ID\t\t顧客名\t\t\t\t\tフリガナ");
				while (rs.next() == true) {
					String oDate = rs.getString("受注日");
					int cId = rs.getInt("顧客ID");
					String cName = rs.getString("顧客名");
					String cFuri = rs.getString("フリガナ");
					System.out.println(
							oDate + "\t\t\t" + cId  + "\t\t\t" + cName + "\t\t\t\t" + cFuri);
				}

				System.out.println();
				System.out.print("受注日の西暦年：");
				year = sc.next();
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
