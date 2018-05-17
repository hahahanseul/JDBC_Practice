package 解答例;
import java.sql.Connection;			// DB接続管理
import java.sql.DriverManager;		// JDBCドライバ管理
import java.sql.PreparedStatement;	// SQL文の実行管理
import java.sql.ResultSet;			// SQL文の実行結果
import java.sql.SQLException;		// SQL関連の例外
import java.util.ArrayList;			// コレクション用

//OrderControlDBAccessクラス（DAO）の定義
class OrderControlDBAccess1_9
{
	//DBとの接続を確立する
	private Connection createConnection()
	{
		// ローカル変数の宣言／初期化
		Connection con = null;			// Connectionインタフェース型の変数

		try
		{
			// JDBCドライバのロード
			Class.forName("com.mysql.jdbc.Driver");

			// DBMSとのコネクション確立（Connectionインスタンスの生成）
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/受注管理DB?useSSL=false", "user1", "pass1");
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
			System.out.println("DB接続時にエラーが発生しました。");
			e.printStackTrace();
		}

		// DBMSとのコネクション（Connectionインスタンスの参照）返却
		return con;
	}

	//DBとの接続を閉じる
	private void closeConnection(Connection con)
	{
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
	}

	//商品リストを取得する
	public ArrayList<ProductBean> findAllProducts()
	{
		// DBMSとのコネクション（Connectionインスタンスの参照）取得
		Connection con = createConnection();

		// インスタンス変数の宣言／初期化
		PreparedStatement stmt = null;			// PreparedStatementインタフェース型の変数
		ResultSet rs = null;					// ResultSetインタフェース型の変数
		ArrayList<ProductBean> list
			= new ArrayList<ProductBean>();		// 商品リスト（ArrayList型のインスタンス）を生成

		try
		{
			// コネクションの確立に成功しているかを確認
			if(con != null)
			{
				// SQL文の準備
				String sql =
						"SELECT  商品ID, 商品名, 単価 "
						+ "FROM  商品マスタ;";

				// DBMSにSQL文を送信する準備（PreparedStatementインスタンスの生成）
				stmt = con.prepareStatement(sql);

				// DBMSにSQL文[SELECT文]を送信する／DBMSから実行結果を受信する（ResultSetインスタンスの生成）
				rs = stmt.executeQuery();

				// 実行結果を含んだResultSetインスタンスからデータを取り出して処理
				while(rs.next() == true)										// カーソル位置に行がある間
				{
					String pId = rs.getString("商品ID");						// 商品IDをpIdに取得
					String pName = rs.getString("商品名");						// 商品名をpNameに取得
					int pPrice = rs.getInt("単価");								// 単価をpPriceに取得
					ProductBean bean = new ProductBean(pId, pName, pPrice);		// DTOのインスタンスを生成
					list.add(bean);												// DTOのインスタンスを商品リストに追加
				}
			}
		}
		catch(SQLException e)
		{
			// DBアクセスで例外（エラー）が発生した場合の処理
			System.out.println("DBアクセス時にエラーが発生しました（商品検索）。");
			e.printStackTrace();
		}
		finally
		{
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

			// PreparedStatementインスタンスの解放処理
			try
			{
				if(stmt != null)
				{
					stmt.close();			// PreaparedStatementインスタンスの解放
				}
			}
			catch(SQLException e)
			{
				// PreaparedStatementインスタンスの解放に失敗した場合の処理
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
		}

		// DBMSとのコネクションを切断（Connectionインスタンスの解放）
		closeConnection(con);

		// 商品リストを返却
		return list;
	}

	//顧客リストを取得する
	public ArrayList<CustomerBean> findAllCustomers()
	{
		// DBMSとのコネクション（Connectionインスタンスの参照）取得
		Connection con = createConnection();

		// インスタンス変数の宣言／初期化
		PreparedStatement stmt = null;			// PreparedStatementインタフェース型の変数
		ResultSet rs = null;					// ResultSetインタフェース型の変数
		ArrayList<CustomerBean> list
			= new ArrayList<CustomerBean>();	// 顧客リスト（ArrayList型のインスタンス）を生成

		try
		{
			// コネクションの確立に成功しているかを確認
			if(con != null)
			{
				// SQL文の準備
				String sql =
						"SELECT  顧客ID, 顧客名 "
						+ "FROM  顧客マスタ;";

				// DBMSにSQL文を送信する準備（PreparedStatementインスタンスの生成）
				stmt = con.prepareStatement(sql);

				// DBMSにSQL文[SELECT文]を送信する／DBMSから実行結果を受信する（ResultSetインスタンスの生成）
				rs = stmt.executeQuery();

				// 実行結果を含んだResultSetインスタンスからデータを取り出して処理
				while(rs.next() == true)										// カーソル位置に行がある間
				{
					String cId = rs.getString("顧客ID");						// 顧客IDをcIdに取得
					String cName = rs.getString("顧客名");						// 顧客名をcNameに取得
					CustomerBean bean = new CustomerBean(cId, cName);			// DTOのインスタンスを生成
					list.add(bean);												// DTOのインスタンスを顧客リストに追加
				}
			}
		}
		catch(SQLException e)
		{
			// DBアクセスで例外（エラー）が発生した場合の処理
			System.out.println("DBアクセス時にエラーが発生しました（商品検索）。");
			e.printStackTrace();
		}
		finally
		{
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

			// PreparedStatementインスタンスの解放処理
			try
			{
				if(stmt != null)
				{
					stmt.close();			// PreaparedStatementインスタンスの解放
				}
			}
			catch(SQLException e)
			{
				// PreaparedStatementインスタンスの解放に失敗した場合の処理
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
		}

		// DBMSとのコネクションを切断（Connectionインスタンスの解放）
		closeConnection(con);

		// 顧客リストを返却
		return list;
	}
}