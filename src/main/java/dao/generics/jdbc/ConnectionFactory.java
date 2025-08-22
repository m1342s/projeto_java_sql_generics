package dao.generics.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static Connection connection;
	
	private ConnectionFactory(Connection connection){
		
	}
	
	public static Connection getConnection() throws SQLException{
		if(connection == null) {
			connection = innitConnection();
			return connection;
		} else if(connection.isClosed()){
			connection=innitConnection();
			return connection;
		} else {
			return connection;
		}
	}

	private static Connection innitConnection() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://localhost:15432/vendas_online_2","postgres","1256");
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
