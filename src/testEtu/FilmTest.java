package testEtu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;

public class FilmTest {

	private Personne p1;
	private Personne p2;
	private Film f1;
	private Film f2;

	@Before
	public void avant() throws RealisateurAbsentException, SQLException {
		Personne.createTable();
		Film.createTable();
		p1 = new Personne("PEYNOT", "Paulin");
		p1.save();
		p2 = new Personne("ARIES", "Lucas");
		p2.save();

		f1 = new Film("SARAH", p1);
		f1.save();
		f2 = new Film("Even le retour", p2);
		f2.save();
	}

	@After
	public void apres() throws SQLException {
		Personne.deleteTable();
		Film.deleteTable();
	}

	@Test
	public void testFindByIdNonPresent() throws SQLException {
		// Preparation des données
		Film f;
		// Lancement de la methode à tester
		f = Film.findById(-1);
		//Instruction de test
		assertEquals("Aucun film ne devrait correspondre", null, f);
	}

	@Test
	public void testFindByPersonneNonPresente() throws SQLException {
		// Preparation des données
		ArrayList<Film> liste1;
		Personne fauxReal = new Personne("IM", "GAY");
		// Lancement de la methode à tester
		liste1 = Film.findByRealisateur(fauxReal);
		// Validation des résultats
		assertTrue("La méthode ne devrait pas retourner de film", liste1.isEmpty());
	}
	
	@Test
	public void testUpdate() throws SQLException {
		// Preparation des données
		f1.setTitre("SUPER NANO");
		try {
			f1.save();
		} catch (RealisateurAbsentException e) {
			e.printStackTrace();
		}
		// Lancement de la methode à tester
		Film f = Film.findById(f1.getId());
		// Validation des résultats
		assertEquals("La personne p1 devrait s'appeller Paulin", "SUPER NANO", f.getTitre());
	}
	
	@Test(expected = RealisateurAbsentException.class)
	public void testUpdateRealisateurInvalide() throws RealisateurAbsentException, SQLException {
		// Preparation des données
		f1.setId_real(-1);
		// Lancement de la methode à tester
		f1.save();
	}
	
	
	@Test
	public void testUpdateRealisateurValide() throws RealisateurAbsentException, SQLException {
		// Preparation des données
		f1.setId_real(p1.getId());
		// Lancement de la methode à tester
		f1.save();
	}
	
	@Test
	public void testSaveNew() throws SQLException {
		// Preparation des données
		Film f = new Film("Je suis dieu", p1);
		// Lancement de la methode à tester
		try {
			f.save();
		} catch (RealisateurAbsentException e) {
			e.printStackTrace();
		}
		// Validation des résultats
		assertEquals("L'id de 'Je suis dieu' devrait être 3", 3, f.getId());
	}
	
	@Test
	public void testDelete() throws SQLException {
		// Preparation des données
		int id = f1.getId();
		// Lancement de la methode à tester
		f1.delete();
		// Validation des résultats
		assertEquals("L'id de 'Inception' devrait être -1", -1, f1.getId());
		assertEquals("Le film ne devrait pas existé en SQL", null, Film.findById(id));
	}

}