package 解答例;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Ren1_8
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:65534/jdbc","user1","pass1");
			String sql = "SELECT 商品ID, SUM(数量) AS 数量合計 FROM 受注明細 GROUP BY 商品ID HAVING SUM(数量) >= ? ORDER BY 数量合計 DESC, 商品ID ASC;";
			stmt = con.prepareStatement(sql);

			int datum;
			System.out.print("数量合計の基準：");
			datum = sc.nextInt();
			while(datum > 0)
			{
				stmt.setInt(1, datum);
				rs = stmt.executeQuery();
				System.out.println("商品ID\t\t数量合計");
				while(rs.next() == true)
				{
					String pId = rs.getString("商品ID");
					int pSum = rs.getInt("数量合計");
					System.out.println(pId + "\t\t" + pSum);
				}
				rs.close();
				System.out.println();

				System.out.print("数量合計の基準：");
				datum = sc.nextInt();
			}
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("JDBCドライバが見つかりません。");
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			System.out.println("DBアクセス時にエラーが発生しました。");
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
			try
			{
				if(con != null)
				{
					con.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}
		}
		sc.close();
	}
}