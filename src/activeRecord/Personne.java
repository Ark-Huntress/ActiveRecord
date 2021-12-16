package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Personne {

	private int id;
	private String nom;
	private String prenom;

	public Personne(String n, String p) {
		this.nom = n;
		this.prenom = p;
		this.id = -1;
	}

	public static ArrayList<Personne> findAll() throws SQLException {

		Connection connect = DBConnection.getInstance().getConnection();
		String SQLPrep = "SELECT * FROM Personne";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		
		ArrayList<Personne> liste = new ArrayList<Personne>();
		
		while (rs.next()) {
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			int id = rs.getInt("id");
			
			Personne p = new Personne(nom, prenom);
			p.setId(id);
			liste.add(p);
		}
		return liste;
	}

	public static Personne findById(int i) throws SQLException {
		Personne temp = null;
	
		String SQLPrep = "SELECT * FROM Personne WHERE id=?";
		Connection connect = DBConnection.getInstance().getConnection();
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setInt(1, i);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		if (rs.next()) {
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			int id = rs.getInt("id");
			temp = new Personne(nom, prenom);
			temp.setId(id);
			
		}
		return temp;
	}

	public static ArrayList<Personne> findByName(String n) throws SQLException {
		ArrayList<Personne> liste = new ArrayList<Personne>();
		
		String SQLPrep = "SELECT * FROM Personne WHERE nom=?";
		Connection connect = DBConnection.getInstance().getConnection();
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setString(1, n);
		prep1.execute();  
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		while (rs.next()) {
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			int id = rs.getInt("id");
			Personne plits = new Personne(nom, prenom);
			plits.setId(id);
			liste.add(plits);	
		}
		return liste;
	}

	public String toString() {
		return nom + " " + prenom + ", ID : " + id;
	}

	//Getteurs
	public int getId() {
		return id;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public void delete() {
		String SQLprep = "DELETE FROM Personne WHERE id=?";
		try {
			PreparedStatement prep = DBConnection.getInstance().getConnection().prepareStatement(SQLprep);
			prep.setInt(1, this.id);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.id = -1;
	}

	public static void createTable() throws SQLException {
		String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
				+ "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
		Connection connect = DBConnection.getInstance().getConnection();
		Statement stmt = connect.createStatement();
		stmt.executeUpdate(createString);
		System.out.println("1) creation table Personne\n");
	}

	public static void deleteTable() throws SQLException {
		String drop = "DROP TABLE Personne";
        Connection connect = DBConnection.getInstance().getConnection();
		Statement stmt = connect.createStatement();
		stmt.executeUpdate(drop);
	}
	
	public void save() throws SQLException{
		if(id == -1) saveNew();
        else update();
	}
	
	private void saveNew() throws SQLException {
			//Insertion en SQL
			String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
			PreparedStatement prep;
			prep = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, this.nom);
			prep.setString(2, this.prenom);
			prep.executeUpdate();
			
			//Mise à  jour de l'id de l'objet Java
			int autoInc = -1;
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				autoInc = rs.getInt(1);
			}
			id = autoInc;
	}
	
	void update() throws SQLException {
			//Update en SQL
			String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
			PreparedStatement prep = DBConnection.getInstance().getConnection().prepareStatement(SQLprep);
			prep.setString(1, this.nom);
			prep.setString(2, this.prenom);
			prep.setInt(3, this.id);
			prep.execute();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personne other = (Personne) obj;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}

}