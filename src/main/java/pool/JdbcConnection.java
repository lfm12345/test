package pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {

	private String jdbcName = "root";

	private String jdbcUrl = "jdbc:mysql://localhost:3306/mydata";

	private String jdbcpassword = "root";

	private Connection conn = null;

	// 获取连接
	public JdbcConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, jdbcName, jdbcpassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() {

		return conn;
	}

	public void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
