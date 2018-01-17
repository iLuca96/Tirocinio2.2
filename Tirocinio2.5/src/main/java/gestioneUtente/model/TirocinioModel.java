package gestioneUtente.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import gestioneStorage.ConnectionDB;

/**
 * 
 * TirocinioModel è una classe con 5 metodi, questa classe si occupa di gestire varie operazioni di inserimento, modifica e cancellazione delle tuple della tabella tirocinio. 
 *
 */
public class TirocinioModel {

	static ConnectionDB database = new ConnectionDB();
	private static DataSource ds = database.getDS();

	private static final String TABLE_NAME = "tirocinio";

	/**
	 * Il metodo doSave, viene utilizzato per poter salvare le informazioni del tirocinio nel DataBase.
	 * @param tirocinio tipo Tirocinio, variabile che ci da accesso a tutti i metodi set e get.
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized void doSave(Tirocinio tirocinio) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "insert into " + TirocinioModel.TABLE_NAME
				+ " (Stato, Tipo, MatricolaStudente, SegreteriaUsername, TutorUsername) values (?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			//preparedStatement.setInt(1, tirocinio.getId());
			preparedStatement.setString(1, tirocinio.getStato());
			preparedStatement.setString(2, tirocinio.getTipo());
			preparedStatement.setString(3, tirocinio.getMatricola_studente());
			preparedStatement.setString(4, tirocinio.getSegreteria_username());
			preparedStatement.setString(5, tirocinio.getTutor_username());
			
			preparedStatement.executeUpdate();
			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}
	
	/**
	 * Il metodo doMdofify, viene utilizzato per poter modificare i dati di un tirocinio
	 * @param stato tipo String, variabile che contiene lo stato di un tirocinio,
	 * @param id tipo int, varibaile che contiene l'id di un tirocinio.
	 * @return result tipo int, restituisce il risultato della query di UPDATE.
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized boolean doModify(String stato, int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int result = 0;
		
		String insertSQL = "UPDATE " + TirocinioModel.TABLE_NAME + " SET Stato = ? where id = ?";
		

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, stato);
			preparedStatement.setInt(2, id);
			
			result = preparedStatement.executeUpdate();

			connection.commit();
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return (result != 0);
	}
	
	/**
	 * Il metodo doRetrieveByKey restituisce informazioni su un Tirconio in base al suo id.
	 * @param id tipo String, variabile che contiente un possibile riferimento ad una tupla in un DataBase
	 * @return bean tipo Tirocinio, variabile che ci da accesso a tutti i metodi set e get.
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized Tirocinio doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Tirocinio bean = new Tirocinio();

		String selectSQL = "select * from " + TirocinioModel.TABLE_NAME + " where id = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setStato(rs.getString("Stato"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setMatricola_studente(rs.getString("MatricolaStudente"));
				bean.setSegreteria_username(rs.getString("SegreteriaUsername"));
				bean.setTutor_username(rs.getString("TutorUsername"));
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return bean;
	}

	/**
	 * Il Metodo doDelete, cancella una tupla nel database in base al suo id.
	 * @param id tipo String, variabile che contiente un possibile riferimento ad una tupla in un DataBase
	 * @return result tipo Integer, variabile che restituisce un valore della query di Delete.
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "delete from " + TirocinioModel.TABLE_NAME + " where id = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);

			result = preparedStatement.executeUpdate();
			connection.commit();
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return (result != 0);
	}

	/**
	 * Il metodo doRetrieveAll, permette di ricevere tutte le tuple di un tirocinio.
	 * @param order tipo String, variabile che verrà utilizzata nella query per poter specificare un ordine.
	 * @return trainings tipo Collection di Tirocinio, Array che conterrà tutte le informazioni sullo Tirocinio
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized Collection<Tirocinio> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Tirocinio> trainings = new LinkedList<Tirocinio>();

		String selectSQL = "select * from " + TirocinioModel.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " order by " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Tirocinio bean = new Tirocinio();
				
				bean.setId(rs.getInt("id"));
				bean.setStato(rs.getString("Stato"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setMatricola_studente(rs.getString("MatricolaStudente"));
				bean.setSegreteria_username(rs.getString("SegreteriaUsername"));
				bean.setTutor_username(rs.getString("TutorUsername"));
				trainings.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return trainings;
	}
	
	/**
	 * Il metodo doRetrieveAllQuestions, permette di ricevere tutte le tuple di una domanda di tirocinio nello stato di "In Attesa".
	 * @param order tipo String, variabile che verrà utilizzata nella query per poter specificare un ordine.
	 * @return trainings tipo Collection di Tirocinio, Array che conterrà tutte le informazioni sullo Tirocinio
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized Collection<Tirocinio> doRetrieveAllQuestions(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Tirocinio> trainings = new LinkedList<Tirocinio>();

		String selectSQL ="select * from studente S, tirocinio T, professore_tutoraziendale PT "
				+ "where S.Matricola=T.MatricolaStudente AND PT.Username=T.TutorUsername AND T.Stato = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " order by " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "In Attesa");
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Tirocinio bean = new Tirocinio();
				
				bean.setId(rs.getInt("id"));
				bean.setStato(rs.getString("Stato"));
				bean.setTipo(rs.getString("T.Tipo"));
				bean.setMatricola_studente(rs.getString("MatricolaStudente"));
				bean.setSegreteria_username(rs.getString("SegreteriaUsername"));
				bean.setTutor_username(rs.getString("TutorUsername"));
				bean.setNomeCognomeStudent(rs.getString("S.Nome") + " " + rs.getString("S.Cognome"));
				bean.setStudent_usename(rs.getString("S.Username"));
				bean.setStudent_email(rs.getString("S.Email"));
				bean.setMatricola_studente(rs.getString("S.Matricola"));
				bean.setNomeCognome(rs.getString("PT.Nome") + " " + rs.getString("PT.Cognome"));
				bean.setTipoTutorProfessore(rs.getString("PT.Tipo"));
				trainings.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return trainings;
	}
	
	/**
	 * Il metodo doRetrieveAllCompleted, permette di ricevere tutte le tuple di una domanda di tirocinio nello stato di "Completato".
	 * @param order tipo String, variabile che verrà utilizzata nella query per poter specificare un ordine.
	 * @return trainings tipo Collection di Tirocinio, Array che conterrà tutte le informazioni sullo Tirocinio
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized Collection<Tirocinio> doRetrieveAllCompleted(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Tirocinio> trainings = new LinkedList<Tirocinio>();

		String selectSQL ="select * from studente S, tirocinio T, professore_tutoraziendale PT "
				+ "where S.Matricola=T.MatricolaStudente AND PT.Username=T.TutorUsername AND T.Stato = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " order by " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "Completato");
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Tirocinio bean = new Tirocinio();
				
				bean.setId(rs.getInt("id"));
				bean.setStato(rs.getString("Stato"));
				bean.setTipo(rs.getString("T.Tipo"));
				bean.setMatricola_studente(rs.getString("MatricolaStudente"));
				bean.setSegreteria_username(rs.getString("SegreteriaUsername"));
				bean.setTutor_username(rs.getString("TutorUsername"));
				bean.setNomeCognomeStudent(rs.getString("S.Nome") + " " + rs.getString("S.Cognome"));
				bean.setStudent_usename(rs.getString("S.Username"));
				bean.setStudent_email(rs.getString("S.Email"));
				bean.setMatricola_studente(rs.getString("S.Matricola"));
				bean.setNomeCognome(rs.getString("PT.Nome") + " " + rs.getString("PT.Cognome"));
				bean.setTipoTutorProfessore(rs.getString("PT.Tipo"));
				trainings.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return trainings;
	}
	
	/**
	 * Il metodo doRetrieveAllConvalidate, permette di ricevere tutte le tuple di una domanda di tirocinio nello stato di "Convalidato".
	 * @param order tipo String, variabile che verrà utilizzata nella query per poter specificare un ordine.
	 * @return trainings tipo Collection di Tirocinio, Array che conterrà tutte le informazioni sullo Tirocinio
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized Collection<Tirocinio> doRetrieveAllConvalidate(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Tirocinio> trainings = new LinkedList<Tirocinio>();

		String selectSQL ="select * from studente S, tirocinio T, professore_tutoraziendale PT "
				+ "where S.Matricola=T.MatricolaStudente AND PT.Username=T.TutorUsername AND T.Stato = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " order by " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "Convalidato");
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Tirocinio bean = new Tirocinio();
				
				bean.setId(rs.getInt("id"));
				bean.setStato(rs.getString("Stato"));
				bean.setTipo(rs.getString("T.Tipo"));
				bean.setMatricola_studente(rs.getString("MatricolaStudente"));
				bean.setSegreteria_username(rs.getString("SegreteriaUsername"));
				bean.setTutor_username(rs.getString("TutorUsername"));
				bean.setNomeCognomeStudent(rs.getString("S.Nome") + " " + rs.getString("S.Cognome"));
				bean.setStudent_usename(rs.getString("S.Username"));
				bean.setStudent_email(rs.getString("S.Email"));
				bean.setMatricola_studente(rs.getString("S.Matricola"));
				bean.setNomeCognome(rs.getString("PT.Nome") + " " + rs.getString("PT.Cognome"));
				bean.setTipoTutorProfessore(rs.getString("PT.Tipo"));
				trainings.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return trainings;
	}
	
	/**
	 * Il metodo MyTraining, mostra tutte le domande ti tirocinio filtrate per Matricola Studente
	 * @param order tipo String, variabile che verrà utilizzata nella query per poter specificare un ordine.
	 * @param matricola tipo String, variabile che verrà utilizzata nella query per poter specificare una Matricola Studente.
	 * @return trainings tipo Collection di Tirocinio, Array che conterrà tutte le informazioni sullo Tirocinio
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized Collection<Tirocinio> MyTraining(String order, String matricola) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Tirocinio> trainings = new LinkedList<Tirocinio>();

		String selectSQL = "select * from tirocinio, professore_tutoraziendale "
				+ "WHERE professore_tutoraziendale.Username=tirocinio.TutorUsername AND tirocinio.MatricolaStudente = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " order by " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, matricola);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Tirocinio bean = new Tirocinio();
				
				bean.setId(rs.getInt("id"));
				bean.setStato(rs.getString("Stato"));
				bean.setTipo(rs.getString("tirocinio.Tipo"));
				bean.setMatricola_studente(rs.getString("MatricolaStudente"));
				bean.setSegreteria_username(rs.getString("SegreteriaUsername"));
				bean.setTutor_username(rs.getString("TutorUsername"));
				bean.setNomeCognome(rs.getString("Nome") + " " + rs.getString("Cognome"));
				bean.setTipoTutorProfessore(rs.getString("professore_tutoraziendale.Tipo"));
				trainings.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return trainings;
	}
	
	/**
	 * Il metodo RequestTraining, mostra tutte le domande ti tirocinio filtrate per username di Professore Tutor Aziendale.
	 * @param order tipo String, variabile che verrà utilizzata nella query per poter specificare un ordine.
	 * @param username tipo String, variabile che verrà utilizzata nella query per poter specificare una username di Professore Tutor Aziendale.
	 * @return trainings tipo Collection di Tirocinio, Array che conterrà tutte le informazioni sullo Tirocinio
	 * @throws SQLException eccezione che viene lanciata quando viene rilevato un errore nell'esecuzione di una query.
	 */
	public synchronized Collection<Tirocinio> RequestTraining(String order, String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Tirocinio> trainings = new LinkedList<Tirocinio>();
		
		String selectSQL ="select * from studente S, tirocinio T, professore_tutoraziendale PT "
				+ "where S.Matricola=T.MatricolaStudente AND PT.Username=T.TutorUsername AND T.TutorUsername = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " order by " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Tirocinio bean = new Tirocinio();
				
				bean.setId(rs.getInt("id"));
				bean.setStato(rs.getString("Stato"));
				bean.setTipo(rs.getString("T.Tipo"));
				bean.setMatricola_studente(rs.getString("MatricolaStudente"));
				bean.setSegreteria_username(rs.getString("SegreteriaUsername"));
				bean.setTutor_username(rs.getString("TutorUsername"));
				bean.setNomeCognomeStudent(rs.getString("S.Nome") + " " + rs.getString("S.Cognome"));
				bean.setStudent_usename(rs.getString("S.Username"));
				bean.setStudent_email(rs.getString("S.Email"));
				bean.setMatricola_studente(rs.getString("S.Matricola"));
				trainings.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return trainings;
	}
}

