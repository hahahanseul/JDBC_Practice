import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Hanseul Kim
 * */
public class Ren1_6 {
	public static void main(String[] args) {
		// インスタンス変数の宣言／初期化
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// キーボード入力の準備
		Scanner sc = new Scanner(System.in);

		try {
			// JDBCドライバのロード
			Class.forName("com.mysql.jdbc.Driver");

			// DBMSとのコネクション確立（Connectionインスタンスの生成）
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/jdbc?useSSL=false", "user1", "pass1");

			// SQL文を準備（SQL文の中の未定の所にプレースホルダ（？）を入れる。）
			String sql = "select 商品ID, rpad(商品名,10,'　') as 商品名, 単価 from 商品マスタ where 単価 between ? and ? order by 単価 desc";

			// DBMSにSQL文を送信する準備（PreparedStatementインスタンスの生成）
			stmt = con.prepareStatement(sql);

			int lowerPrice;
			int upperPrice;
			System.out.print("単価の下限：");
			lowerPrice = sc.nextInt();
			System.out.print("単価の上限：");
			upperPrice = sc.nextInt();

			while (lowerPrice >0 && upperPrice>0) {
				// SQL文の1番目のプレースホルダにlowerPriceを埋め込む。
				stmt.setInt(1, lowerPrice);

				// SQL文の1番目のプレースホルダにupperPriceを埋め込む。
				stmt.setInt(2, upperPrice);

				// DBMSにSQL文[SELECT文]を送信する／DBMSから実行結果を受信する（ResultSetインスタンスの生成）
				rs = stmt.executeQuery();

				// 実行結果を含んだResultSetインスタンスからデータを取り出して表示
				System.out.println("商品ID\t\t商品名\t\t\t\t\t\t\t単価");
				while (rs.next() == true) {
					// 読み込んだレコード1行分から、指定したカラムの値を取得
					// 取得した値のそれぞれの変換に代入
					String pId = rs.getString("商品ID");
					String pName = rs.getString("商品名");
					int pPrice = rs.getInt("単価");
					System.out.println(
							pId + "\t\t" + pName + "\t\t\t" + pPrice);

					// whileループを使って、上の処理を行数分繰り返す。
				}

				System.out.println();
				System.out.print("単価の下限：");
				lowerPrice = sc.nextInt();
				System.out.print("単価の上限：");
				upperPrice = sc.nextInt();

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
