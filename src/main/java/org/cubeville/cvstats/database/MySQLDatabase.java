package org.cubeville.cvstats.database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;
import java.util.List;

public class MySQLDatabase {
	private HikariDataSource dataSource;
	private final String hostname, database, username, password;
	private final int port;

	public MySQLDatabase(String hostname, int port, String database, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	protected void setupPool() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(
				"jdbc:mysql://" +
						hostname +
						":" +
						port +
						"/" +
						database
		);
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setUsername(username);
		config.setPassword(password);
		config.setMinimumIdle(10);
		config.setMaximumPoolSize(10);
		config.setConnectionTimeout(30000);
		config.setConnectionTestQuery("SELECT 1");
		dataSource = new HikariDataSource(config);
	}

	public void update(String sql) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, null);
		}
	}

	public void closePool() {
		if (dataSource != null && !dataSource.isClosed()) {
			dataSource.close();
		}
	}

	public void close(Connection conn, PreparedStatement ps, ResultSet res) {
		if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
		if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
		if (res != null) try { res.close(); } catch (SQLException ignored) {}
	}
}
