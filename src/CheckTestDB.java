import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckTestDB {
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// DriverManagerでDBアクセスの準備を行う
			// DBのアクセス先や、ポート番号、ログイン時のIDやパスワードを設定
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/TestDB?useSSL=false", "user1", "pass1");

			// StatementでSQLを送信する準備を行う
			stmt = con.createStatement();
			// executeQueryメソッドで、SQLを実行し、その結果をResultSet型の変数で受け取る
			rs = stmt.executeQuery("select * from employee");
			System.out.println("TABLE : \tEMPLOYEE");
			// rsには、"select * from employee"の結果が格納されている。
			// rs.next()で、rsの中をチェックして、1レコード(行)分読み込む。
			while (rs.next()) {
				// 読み込んだレコード1行分から、指定したカラムの値を取得
				// 取得した値のそれぞれの変換に代入
				String id = rs.getString("ID");
				String name = rs.getString("NAME");
				System.out.println("ID    : \t" + id);
				System.out.println("NAME  : \t" + name);
				// whileループを使って、上の処理を行数分繰り返す。
			}
		} catch (ClassNotFoundException e) {
			System.out.println("JDBCドライバが見つかりません。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		} finally {
			// try～catch句の結果が正常であろうと、異常(エラー)であろうと、
			// どちらでも必ず実行される処理をここにかく！
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
	}
}
