package 解答例;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Ren1_7
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

			// SQLを横に書くのは、大変なのでStringBuilderを使って、最後に、String型に直す方法もある。
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT\n");
			sb.append("  受注日\n");
			sb.append("  , 顧客マスタ.顧客ID\n");
			sb.append("  , 顧客名\n");
			sb.append("  , フリガナ \n");
			sb.append("FROM\n");
			sb.append("  受注 \n");
			sb.append("  INNER JOIN 顧客マスタ \n");
			sb.append("    ON 受注.顧客ID = 顧客マスタ.顧客ID \n");
			sb.append("WHERE\n");
			sb.append("  受注日 BETWEEN ? AND ? \n");
			sb.append("ORDER BY\n");
			sb.append("  受注日 DESC; \n");

			// prepareStatementメソッドはString型しか受付ないので、Stringへ最後に直す。
			String sql = sb.toString();
//			String sql = "SELECT 受注日, 顧客マスタ.顧客ID, 顧客名, フリガナ FROM 受注 INNER JOIN 顧客マスタ ON 受注.顧客ID = 顧客マスタ.顧客ID WHERE 受注日 BETWEEN ? AND ? ORDER BY 受注日 DESC;";
			stmt = con.prepareStatement(sql);

			int year;
			System.out.print("受注日の西暦年：");
			year = sc.nextInt();
			while(year > 0)
			{
				stmt.setString(1, year + "/01/01");
				stmt.setString(2, year + "/12/31");
				rs = stmt.executeQuery();
				System.out.println("受注日\t\t顧客ID\t\t顧客名\t\tフリガナ");
				while(rs.next() == true)
				{
					String date = rs.getString("受注日");
					String cID = rs.getString("顧客ID");
					String cName = rs.getString("顧客名");
					String pKana = rs.getString("フリガナ");
					System.out.println(date + "\t" + cID + "\t\t" + cName + "\t" + pKana);
				}
				rs.close();
				System.out.println();

				System.out.print("受注日の西暦年：");
				year = sc.nextInt();
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