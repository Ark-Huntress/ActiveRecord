package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() throws SQLException {
        // variables a modifier en fonction de la base
		String userName = "root";
		String password = "";
		String serverName = "localhost";
		//Attention, sous MAMP, le port est 8889
		String portNumber = "3306";
		String tableName = "personne";

		// iL faut une base nommee testPersonne !
		String dbName = "testpersonne";

		// creation de la connection
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		String urlDB = "jdbc:mysql://" + serverName + ":";
		urlDB += portNumber + "/" + dbName;
		connection = DriverManager.getConnection(urlDB, connectionProps);
    }
    
    public static synchronized DBConnection getInstance() throws SQLException{
        if(instance== null) instance = new DBConnection();
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }
}