package gestioneUtente.model;

/**
 * 
 * Tirocinio è una classe con 6 metodi set e get, questa classe è utilizzata per modellare una Tirocinio. 
 *
 */
public class Tirocinio {
	
	/**
	 * Costruttore che inizializza le variabili globali come elementi vuoti
	 */
	public Tirocinio() {
		this.id = -1;
		this.stato = "";
		this.tipo = "";
		this.matricola_studente = "";
		this.segreteria_username = "";
		this.tutor_username = "";
	}
	
	/**
	 * Metodo che restituisce un id
	 * @return id tipo Integer, variabile contente il valore di un id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Metodo che setta un id
	 * @param id tipo Integer, variabile contente il valore di un id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Metodo che restituisce un stato
	 * @return stato tipo String, variabile contente il valore di un stato
	 */
	public String getStato() {
		return stato;
	}
	/**
	 * Metodo che setta un stato
	 * @param stato tipo String, variabile contente il valore di un stato
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	/**
	 * Metodo che restituisce un tipo
	 * @return tipo tipo String, variabile contente il valore di un tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Metodo che setta un tipo
	 * @param tipo tipo String, variabile contente il valore di un tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Metodo che restituisce un matricola_studente
	 * @return matricola_studente tipo String, variabile contente il valore di un matricola_studente
	 */
	public String getMatricola_studente() {
		return matricola_studente;
	}
	/**
	 * Metodo che setta un matricola_studente
	 * @param matricola_studente tipo String, variabile contente il valore di un matricola_studente
	 */
	public void setMatricola_studente(String matricola_studente) {
		this.matricola_studente = matricola_studente;
	}
	
	/**
	 * Metodo che restituisce un segreteria_username
	 * @return segreteria_username tipo String, variabile contente il valore di un segreteria_username
	 */
	public String getSegreteria_username() {
		return segreteria_username;
	}
	/**
	 * Metodo che setta un segreteria_username
	 * @param segreteria_username tipo String, variabile contente il valore di un segreteria_username
	 */
	public void setSegreteria_username(String segreteria_username) {
		this.segreteria_username = segreteria_username;
	}
	
	/**
	 * Metodo che restituisce un tutor_username
	 * @return tutor_username tipo String, variabile contente il valore di un tutor_username
	 */
	public String getTutor_username() {
		return tutor_username;
	}
	/**
	 * Metodo che setta un tutor_username
	 * @param tutor_username tipo String, variabile contente il valore di un tutor_username
	 */
	public void setTutor_username(String tutor_username) {
		this.tutor_username = tutor_username;
	}
	
	/**
	 * Metodo che restituisce un NomeCognome
	 * @return NomeCognome tipo String, variabile contente il valore di un NomeCognome
	 */
	public String getNomeCognome() {
		return NomeCognome;
	}
	/**
	 * Metodo che setta un NomeCognome
	 * @param nomeCognome tipo String, variabile contente il valore di un nomeCognome
	 */
	public void setNomeCognome(String nomeCognome) {
		NomeCognome = nomeCognome;
	}

	/**
	 * Metodo che restituisce un TipoTutorProfessore
	 * @return TipoTutorProfessore tipo String, variabile contente il valore di un TipoTutorProfessore
	 */
	public String getTipoTutorProfessore() {
		return TipoTutorProfessore;
	}
	/**
	 * Metodo che setta un TipoTutorProfessore
	 * @param tipoTutorProfessore tipo String, variabile contente il valore di un tipoTutorProfessore
	 */
	public void setTipoTutorProfessore(String tipoTutorProfessore) {
		TipoTutorProfessore = tipoTutorProfessore;
	}
	
	/**
	 * Metodo che restituisce un student_usename
	 * @return student_usename tipo String, variabile contente il valore di un student_usename
	 */
	public String getStudent_usename() {
		return student_usename;
	}
	/**
	 * Metodo che setta un student_usename
	 * @param student_usename tipo String, variabile contente il valore di un student_usename
	 */
	public void setStudent_usename(String student_usename) {
		this.student_usename = student_usename;
	}

	/**
	 * Metodo che restituisce un student_email
	 * @return student_email tipo String, variabile contente il valore di un student_email
	 */
	public String getStudent_email() {
		return student_email;
	}
	/**
	 * Metodo che setta un student_email
	 * @param student_email tipo String, variabile contente il valore di un student_email
	 */
	public void setStudent_email(String student_email) {
		this.student_email = student_email;
	}

	/**
	 * Metodo che restituisce un NomeCognomeStudent
	 * @return NomeCognomeStudent tipo String, variabile contente il valore di un NomeCognomeStudent
	 */
	public String getNomeCognomeStudent() {
		return NomeCognomeStudent;
	}
	/**
	 * Metodo che setta un NomeCognomeStudent
	 * @param nomeCognomeStudent tipo String, variabile contente il valore di un nomeCognomeStudent
	 */
	public void setNomeCognomeStudent(String nomeCognomeStudent) {
		NomeCognomeStudent = nomeCognomeStudent;
	}


	private int id;
	private String stato;
	private String tipo;
	private String matricola_studente;
	private String segreteria_username;
	private String tutor_username;
	private String NomeCognome;
	private String TipoTutorProfessore;
	private String NomeCognomeStudent;
	private String student_usename;
	private String student_email;
	
}
