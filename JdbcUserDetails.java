import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcUserDetails {

	// public static void readAllRecords() throws Exception { 									// For checking purpose only
	// 	String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";

	// 	Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");
	// 	System.out.println("User Available : \n");

	// 	Statement stmt = con.createStatement();
	// 	ResultSet rs = stmt.executeQuery("SELECT * FROM user");

	// 	while (rs.next()) {
	// 		System.out.println("Account number : " + rs.getInt("accNo") + "\n"
	// 				+ rs.getString("name") + "    "
	// 				+ rs.getString("email") + "    "
	// 				+ rs.getString("phoneNo") + "\n");

	// 	}

	// 	con.close();
	// }

	public static void readRecords(int anum) throws Exception {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		String query = "SELECT accNo, name, email, phoneNo FROM user WHERE accNo = ?";
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, anum);

		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			System.out.println("Account number : " + rs.getInt("accNo") + "\n Name : "
					+ rs.getString("name") + "\n Email : "
					+ rs.getString("email") + "\n Phone Number : "
					+ rs.getString("phoneNo") );
		}

		con.close();
	}

	public static void insertRecords(UserDetails ud) throws Throwable {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		String query = "insert into user(accNo, name, email, phoneNo) values (?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(query);

		pst.setInt(1, ud.getAccNo());
		pst.setString(2, ud.getName());
		pst.setString(3, ud.getEmail());
		pst.setString(4, ud.getPhoneNo());

		pst.executeUpdate();
		con.close();
	}

	public boolean checkAccNo(int aNumber) throws Exception {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT accNo FROM user");
		while (rs.next()) {
			if (rs.getInt("accNo") == aNumber) {
				return false;
			}
		}
		return true;
	}

	public void updateName(int anum, String newName) throws Throwable {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		String query = "update user set name = ? where accNo = ?";
		PreparedStatement pst = con.prepareStatement(query);
		
		pst.setString(1, newName);
		pst.setInt(2, anum);
		pst.executeUpdate();

		System.out.println("\n\u001B[32mName updated Successfully!\u001B[0m\n");
		con.close();
	}

	public void updateEmail(int anum, String newEmail) throws Throwable {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		String query = "update user set email = ? where accNo = ?";
		PreparedStatement pst = con.prepareStatement(query);

		pst.setString(1, newEmail);
		pst.setInt(2, anum);
		pst.executeUpdate();

		System.out.println("\n\u001B[32mEmail updated Successfully!\u001B[0m\n");
		con.close();
	}

	public void updatePhoneNumber(int anum, String pn) throws Throwable {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		String query = "update user set phoneNo = ? where accNo = ?";
		PreparedStatement pst = con.prepareStatement(query);

		pst.setString(1, pn);
		pst.setInt(2, anum);
		pst.executeUpdate();

		System.out.println("\n\u001B[32mPhone Number updated Successfully!\u001B[0m\n");
		con.close();
	}
}
