package 解答例;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//OrderControlDBAccessクラス（DAO）の定義
public class OrderControlDBAccess1_10
{
	//DBとの接続を確立する
	private Connection createConnection()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/受注管理DB","user1","pass1");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("JDBCドライバが見つかりません。");
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			System.out.println("DB接続時にエラーが発生しました。");
			e.printStackTrace();
		}
		return con;
	}

	//DBとの接続を閉じる
	private void closeConnection(Connection con)
	{
		try
		{
			if(con != null)
			{
				con.close();
			}
		}
		catch(SQLException e)
		{
			System.out.println("DB切断時にエラーが発生しました。");
			e.printStackTrace();
		}
	}

	//商品リストを取得する
	public ArrayList<ProductBean> findAllProducts()
	{
		Connection con = createConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		try
		{
			if(con != null)
			{
				String sql = "SELECT  商品ID, 商品名, 単価  FROM  商品マスタ;";
				//一度だけ最適化を行う
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next() == true)
				{
					String pId = rs.getString("商品ID");
					String pName = rs.getString("商品名");
					int pPrice = rs.getInt("単価");
					ProductBean bean = new ProductBean(pId, pName, pPrice);
					list.add(bean);
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println("DBアクセス時にエラーが発生しました（商品検索）。");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println(
						"DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
			try
			{
				if(stmt != null)
				{
					stmt.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
		}
		closeConnection(con);
		return list;
	}

	//顧客リストを取得する
	public ArrayList<CustomerBean> findAllCustomers()
	{
		Connection con = createConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<CustomerBean> list = new ArrayList<CustomerBean>();
		try
		{
			if(con != null)
			{
				String sql = "SELECT  顧客ID,  顧客名 FROM  顧客マスタ;";
				//一度だけ最適化を行う
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next() == true)
				{
					String cID = rs.getString("顧客ID");
					String cName = rs.getString("顧客名");
					CustomerBean bean = new CustomerBean(cID, cName);
					list.add(bean);
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println("DBアクセス時にエラーが発生しました（顧客検索）。");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
			try
			{
				if(stmt != null)
				{
					stmt.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
		}
		closeConnection(con);
		return list;
	}

	//都道府県リストを取得する
	public ArrayList<DivisionBean> findAllDivisions()
	{
		Connection con = createConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<DivisionBean> list = new ArrayList<DivisionBean>();
		try
		{
			if(con != null)
			{
				String sql = "SELECT 顧客マスタ.都道府県, SUM(商品マスタ.単価 * 受注明細.数量) AS 売上合計 FROM ((顧客マスタ INNER JOIN 受注 ON 顧客マスタ.顧客ID = 受注.顧客ID) INNER JOIN 受注明細 ON 受注.受注ID = 受注明細.受注ID) INNER JOIN 商品マスタ ON 受注明細.商品ID = 商品マスタ.商品ID GROUP BY 顧客マスタ.都道府県 ORDER BY SUM(商品マスタ.単価 * 受注明細.数量) DESC;";
				//一度だけ最適化を行う
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next() == true)
				{
					String dName = rs.getString("都道府県");
					int dPrice = rs.getInt("売上合計");
					DivisionBean bean = new DivisionBean(dName, dPrice);
					list.add(bean);
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println("DBアクセス時にエラーが発生しました（都道府県検索）。");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
			try
			{
				if(stmt != null)
				{
					stmt.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
		}
		closeConnection(con);
		return list;
	}
}