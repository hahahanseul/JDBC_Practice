import java.sql.Connection;			// DB接続管理
import java.sql.DriverManager;		// JDBCドライバ管理
import java.sql.ResultSet;			// SQL文の実行結果
import java.sql.SQLException;		// SQL関連の例外
import java.sql.Statement;			// SQL文の実行管理
import java.util.Scanner;			// キーボード入力用

class SQLExecutionOption
{
	public static void main(String[] args)
	{
		// インスタンス変数の宣言／初期化
		Connection con = null;		// Connectionインタフェース型の変数
		Statement stmt = null;		// Statementインタフェース型の変数
		ResultSet rs = null;		// ResultSetインタフェース型の変数

		// キーボード入力の準備
		Scanner sc = new Scanner(System.in);

		// 検索する商品IDの入力	
		System.out.print("商品ID入力：");
		String sId = sc.next();

		try
		{
			// JDBCドライバのロード
			Class.forName("com.mysql.jdbc.Driver");

			// DBMSとのコネクション確立（Connectionインスタンスの生成）
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/jdbc?useSSL=false", "user1", "pass1");

			// DBMSにSQL文を送信する準備（Statementインスタンスの生成）
			stmt = con.createStatement();

			// DBMSにSQL文[SELECT文]を送信する／DBMSから実行結果を受信する（ResultSetインスタンスの生成）
			rs = stmt.executeQuery("SELECT  商品ID, 商品名, 単価 "
								   + "FROM  商品マスタ "
								   + "WHERE 商品ID = '" + sId + "';");

			// 実行結果を含んだResultSetインスタンスからデータを取り出して表示
			if(rs.next() == true)											// カーソル位置に行がある間
			{
				String pId = rs.getString("商品ID");							// 商品IDをpIdに取得
				String pName = rs.getString("商品名");							// 商品名をpNameに取得
				int pPrice = rs.getInt("単価");									// 単価をpPriceに取得
				System.out.println("商品ID\t商品名\t\t\t単価");						// 見出し出力
				System.out.println(pId + "\t" + pName + "\t\t\\" + pPrice);		// 取得したデータを表示
			}else {
				System.out.println("該当の商品IDは存在しません。");
			}
		}
		catch(ClassNotFoundException e)
		{
			// JDBCドライバが見つからなかった場合の処理
			System.out.println("JDBCドライバが見つかりません。");
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			// DBアクセスで例外（エラー）が発生した場合の処理
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
		finally
		{
			// DBMSとのコネクション切断

			// ResultSetインスタンスの解放処理
			try
			{
				if(rs != null)
				{
					rs.close();				// ResultSetインスタンスの解放
				}
			}
			catch(SQLException e)
			{
				// ResultSetインスタンスの解放に失敗した場合の処理
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}

			// Statementインスタンスの解放処理
			try
			{
				if(stmt != null)
				{
					stmt.close();			// Statementインスタンスの解放
				}
			}
			catch(SQLException e)
			{
				// Statementインスタンスの解放に失敗した場合の処理
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}

			// Connectionインスタンスの解放処理
			try
			{
				if(con != null)
				{
					con.close();			// Connectionインスタンスの解放
				}
			}
			catch(SQLException e)
			{
				// Connectionインスタンスの解放に失敗した倍の処理
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}

			// キーボード入力の後処理
			sc.close();
		}
	}
}