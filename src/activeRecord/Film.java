package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Film {

	private String titre;
	private int id, id_real;

	public Film(String t, Personne p) {
		this.titre = t;
		this.id = -1;
		this.id_real = p.getId();
	}
	
	private Film(String t, int id, int idR) {
		this.titre = t;
		this.id = id;
		this.id_real = idR;
	}
	
	public static ArrayList<Film> findAll() throws SQLException {
		String rq = " SELECT ID, TITRE, ID_REA FROM FILM ;" ;
		Connection connect = DBConnection.getInstance().getConnection();
		Statement stmt = connect.createStatement();
		stmt.execute(rq);
		ResultSet rs = stmt.getResultSet();
		
		ArrayList<Film> list = new ArrayList<Film>();
		
		while(rs.next()) {
			int id = rs.getInt("id");
			String t = rs.getString("titre");
			int ir = rs.getInt("id_rea");
			Film film = new Film(t,id, ir);
			list.add(film);
		}
		return list;
	}
	
	public static Film findById(int i) throws SQLException {
		Film res = null;
		String SQLPrep = "SELECT * FROM Film WHERE id=?";
		Connection connect = DBConnection.getInstance().getConnection();
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setInt(1, i);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		if (rs.next()) {
			res = new Film(rs.getString("TITRE"),rs.getInt("ID"),rs.getInt("ID_REA"));
		}
		return res;
	}
	
	public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException {

		String SQLPrep = "SELECT * FROM Film WHERE id_rea=?";
		Connection connect = DBConnection.getInstance().getConnection();
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setInt(1, p.getId());
		prep1.execute();  
		ResultSet rs = prep1.getResultSet();
		
		ArrayList<Film> res = new ArrayList<Film>();

		// s'il y a un resultat
		while (rs.next()) {
			String titre = rs.getString("Titre");
			int id = rs.getInt("id");
			Film f = new Film(titre, id, p.getId());
			res.add(f);
		}
		return res;
	}
	
	public void delete() throws SQLException {
		String SQLprep = "DELETE FROM Film WHERE id=?";
		PreparedStatement prep = DBConnection.getInstance().getConnection().prepareStatement(SQLprep);
		prep.setInt(1, this.id);
		prep.execute();
		this.id = -1;
	}
	
	public Personne getRealisateur() throws SQLException {
		return Personne.findById(this.id_real);
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public void setId_real(int id_real) {
		this.id_real = id_real;
	}
	
	public int getId() {
		return id;
	}
	
	public int getId_real() {
		return id_real;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public void save() throws SQLException, RealisateurAbsentException {
		if(id == -1) {
			saveNew();
		} else {
			update();
		}
	}
	
	private void saveNew() throws RealisateurAbsentException, SQLException {
		if(this.id_real != -1) {
			//Insertion en SQL
			String SQLPrep = "INSERT INTO Film (titre, id_rea) VALUES (?,?);";
			PreparedStatement prep;
			prep = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, this.titre);
			prep.setInt(2, this.id_real);
			prep.executeUpdate();
			
			//Mise à jour de l'id de l'objet Java
			int autoInc = -1;
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				autoInc = rs.getInt(1);
			}
			this.id = autoInc;
		} 
		else {
			throw (new RealisateurAbsentException("Le rÃƒÂ©alisateur n'est pas renseignÃƒÂ©"));
		}
	}
	
	private void update() throws RealisateurAbsentException, SQLException {
		if(this.id_real != -1) {
			//Update en SQL
			String SQLprep = "update Film set titre=?, id_rea=? where id=?;";
			PreparedStatement prep = DBConnection.getInstance().getConnection().prepareStatement(SQLprep);
			prep.setString(1, this.titre);
			prep.setInt(2, this.id_real);
			prep.setInt(3, this.id);
			prep.execute();
		} 
		else {
			throw (new RealisateurAbsentException("Le rÃƒÂ©alisateur n'est pas renseignÃƒÂ©"));
		}
	}
	
	public static void createTable() throws SQLException {
		String createString = "CREATE TABLE IF NOT EXISTS `film` (\n" + 
				"  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" + 
				"  `TITRE` varchar(40) NOT NULL,\n" + 
				"  `ID_REA` int(11) DEFAULT NULL,\n" + 
				"  PRIMARY KEY (`ID`),\n" + 
				"  KEY `ID_REA` (`ID_REA`)\n" + 
				")";	
		Statement stmt = DBConnection.getInstance().getConnection().createStatement();
		stmt.executeUpdate(createString);
	}
	
	
	public static void deleteTable() throws SQLException {
		String createString = "DROP TABLE film";
		Statement stmt = DBConnection.getInstance().getConnection().createStatement();
		stmt.executeUpdate(createString);
	}
	
}