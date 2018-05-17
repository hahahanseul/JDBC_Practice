package 解答例;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Ren1_6
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
			String sql = "SELECT 商品ID, 商品名, 単価 FROM 商品マスタ WHERE 単価 BETWEEN ? AND ? ORDER BY 単価 DESC, 商品ID ASC;";
			stmt = con.prepareStatement(sql);

			int lowerPrice;
			int upperPrice;
			System.out.print("単価の下限：");
			lowerPrice = sc.nextInt();	//単価の下限を読み込む
			System.out.print("単価の上限：");
			upperPrice = sc.nextInt();	//単価の上限を読み込む
			while(lowerPrice > 0 && upperPrice > 0)
			{
				stmt.setInt(1, lowerPrice);
				stmt.setInt(2, upperPrice);
				rs = stmt.executeQuery();
				System.out.println("商品ID\t商品名\t\t\t単価");
				while(rs.next() == true)
				{
					String pId = rs.getString("商品ID");
					String pName = rs.getString("商品名");
					int pPrice = rs.getInt("単価");
					System.out.println(pId + "\t" + pName + "\t\t\\" + pPrice);
				}
				rs.close();
				System.out.println();

				System.out.print("単価の下限：");
				lowerPrice = sc.nextInt();
				System.out.print("単価の上限：");
				upperPrice = sc.nextInt();
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
		System.out.println("検索が終了しました。");
		sc.close();
	}
}
