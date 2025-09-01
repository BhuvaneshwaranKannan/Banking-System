import java.sql.*;

public class JdbcServiceHistory {

    public void insertSRecords(int accNo, String type, double amount, double balance, Integer targetAccNo)
            throws Throwable {
        String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";

        String query = "INSERT INTO TransHistory (accNo, type, amount, balance, targetAccNo) "
                     + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, accNo);
            pst.setString(2, type);
            pst.setDouble(3, amount);
            pst.setDouble(4, balance);

            if (targetAccNo != null) {
                pst.setInt(5, targetAccNo);     
            } else {
                pst.setNull(5, java.sql.Types.INTEGER);  
            }

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void readHistory(int anum) throws Exception {
		String url = "jdbc:mysql://localhost:3306/jdbcBank?useSSL=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url, "root", "Vishal2725@#");

		String query = "SELECT * FROM TransHistory WHERE accNo = ?";
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, anum);

		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
            String type = rs.getString("type");
            
            if(type.equals("Transferred")){
                
                System.out.println("Transaction ID : " + rs.getInt("transId") + "\n" +
                                "On ["  + rs.getTimestamp("transDate") + "]" + 
                                " You have " + rs.getString("type") + 
                                " Rs. " + rs.getDouble("amount") + 
                                " to Account Number #Acc " + rs.getObject("targetAccNo") + 
                                " | Balance : " + rs.getDouble("balance") + "\n");
            
            }else if(type.equals("Received")){
                 System.out.println("Transaction ID : " + rs.getInt("transId") + "\n" +
                                "On ["  + rs.getTimestamp("transDate") + "]" + 
                                " You have " + rs.getString("type") + 
                                " Rs. " + rs.getDouble("amount") + 
                                " from Account Number #Acc " + rs.getObject("targetAccNo") + 
                                " | Balance : " + rs.getDouble("balance") + "\n");
            } else {

                System.out.println("Transaction ID : " + rs.getInt("transId") + "\n" +
                                "On ["  + rs.getTimestamp("transDate") + "]" + 
                                " You have " + rs.getString("type") + "ed" + 
                                " Rs. " + rs.getDouble("amount") + 
                                " | Balance : " + rs.getDouble("balance") + "\n");
            }
        }
		con.close();
	}
}
