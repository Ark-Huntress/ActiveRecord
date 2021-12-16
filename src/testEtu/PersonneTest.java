package testEtu;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import activeRecord.Film;
import activeRecord.Personne;

public class PersonneTest {
	
	private Personne p1;
	private Personne p2;

	@Before
	public void avant() throws SQLException{
		 Personne.createTable();
		 Film.createTable();
	     p1 = new Personne("TOTO", "toto");
	     p1.save();
	     p2 = new Personne("TATA", "tata");
	     p2.save();
	}

	@After
	public void apres() throws SQLException  {
		Personne.deleteTable();
		Film.deleteTable();
	}

	@Test
	public void testFindAll() throws SQLException {
		// Preparation des donn�es
		ArrayList<Personne> liste1;
		ArrayList<Personne> liste2 = new ArrayList<Personne>();
		liste2.add(p1);
		liste2.add(p2);
		// Lancement de la methode � tester
		liste1 = Personne.findAll();
		// Validation des r�sultats
		assertTrue("La m�thode devrait retourner deux personne (toto TOTO et tata TATA)", liste2.equals(liste1));
	}

	
	@Test
	public void testFindByIdPresent() throws SQLException {
		// Preparation des donn�es
		Personne p;
		// Lancement de la methode � tester
		p = Personne.findById(p1.getId());
		//Instruction de test
		assertEquals("p et p1 devraient etre les memes personnes", p1, p);
	}

	
	@Test
	public void testFindByIdNonPresent() throws SQLException {
		// Preparation des donn�es
		boolean liste;
		// Lancement de la methode � tester
		liste= (Personne.findById(-2)==null);
		//Instruction de test
		assertTrue("p devrait etre null", liste);
	}


	
	@Test
	public void testFindByName() throws SQLException {
		// Preparation des donn�es
		List<Personne> liste1;
		List<Personne> liste2 = new ArrayList<Personne>();
		liste2.add(p1);
		// Lancement de la methode � tester
		liste1 = Personne.findByName("TOTO");
		// Validation des r�sultats
		assertTrue("La m�thode devrait retourner une toto TOTO", liste2.equals(liste1));
	}
	
	
	@Test
	public void testDelete() throws SQLException  {
		// Preparation des donn�es
		int id = p1.getId();
		// Lancement de la methode � tester
		p1.delete();
		// Validation des r�sultats
		assertEquals("La personne p1 devrait avoir l'id -1", -1, p1.getId());
		assertEquals("Il ne devrait plus y avoir p1 en SQL", null, Personne.findById(id));
	}
	
	
	@Test
	public void testUpdate() throws SQLException{
		// Preparation des donn�es
		p1.setPrenom("Sarah");
		p1.save();
		// Lancement de la methode � tester
		Personne p = Personne.findById(p1.getId());
		// Validation des r�sultats
		assertEquals("La personne p1 devrait s'appeller Sarah", "Sarah", p.getPrenom());
	}
	
	
	@Test
	public void testSaveNew() throws SQLException {
		// Preparation des donn�es
		Personne p = new Personne("Querty", "Azerty");
		// Lancement de la methode � tester
		p.save();
		// Validation des r�sultats
		assertEquals("L'id de Clavier devrait �tre 3", 3, p.getId());
	}
}