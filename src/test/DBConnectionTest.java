package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import activeRecord.DBConnection;

public class DBConnectionTest {

	@Test
	public void testGetConnection() throws SQLException  {
		
		// Preparation des données
		Connection c1;
		Connection c2;
		
		// Lancement de la methode à tester
		c1 = DBConnection.getInstance().getConnection();
		c2 = DBConnection.getInstance().getConnection();
		
		// Validation des résultats
		assertEquals("ça devrait être la meme connection", c1, c2);
	
	}
}
