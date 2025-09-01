import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUserService {
	public void insertSRecords(UserService us) throws Throwable {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		try (Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");
			PreparedStatement pst = con.prepareStatement("INSERT INTO service(accNo, balance, password, isActive) VALUES (?, ?, ?, ?)")) {

			pst.setInt(1, us.getAccNo());
			pst.setDouble(2, us.getBalance());
			pst.setString(3, us.getSecurityKey());
			pst.setInt(4, 0);

			 pst.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error inserting record: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void updateActive(int anum) throws Throwable {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		String query = "update service set isActive = ? where accNo = ?";
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, 1);
		pst.setInt(2, anum);
		pst.executeUpdate();

		con.close();
	}

	public boolean checkSisActive(int aNumber) throws Exception {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT isActive FROM service WHERE accNo = " + aNumber);

		while (rs.next()) {
			if (rs.getInt("isActive") == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean checkSAcNo(int aNumber) throws Exception {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		try (Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");
				PreparedStatement pst = con.prepareStatement("SELECT accNo FROM service WHERE accNo=?")) {

			pst.setInt(1, aNumber);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next();
			}
		}
	}

	public String getSPass(int anum) throws Exception {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT accNo, password FROM service");

		while (rs.next()) {
			if (rs.getInt("accNo") == anum) {
				String pass = rs.getString("password");
				return pass;
			}
		}
		return null;
	}

	public Double getSBalance(int anum) throws Exception {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT accNo, balance FROM service");

		while (rs.next()) {
			if (rs.getInt("accNo") == anum) {
				Double balance = rs.getDouble("balance");
				return balance;
			}
		}
		return null;
	}

	public void updatePass(int anum, String newPass) throws Throwable {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		String query = "update service set password = ? where accNo = ?";
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, newPass);
		pst.setInt(2, anum);
		pst.executeUpdate();

		con.close();
	}

	public void updateBalance(int type, int anum, double amount) throws Exception {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		try (Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#")) {

			if (type == 2) {
				double currentBalance = getSBalance(anum);
				if (currentBalance < amount) {
					System.out.println("\u001B[31mInsufficient Balance!\u001B[0m");
					return;
				}
			}
			String query = (type == 1) ? "UPDATE service SET balance = balance + ? WHERE accNo = ?"
									: "UPDATE service SET balance = balance - ? WHERE accNo = ?";

			try (PreparedStatement pst = con.prepareStatement(query)) {
				pst.setDouble(1, amount);
				pst.setInt(2, anum);
				pst.executeUpdate();
			}
		}
	}

}
