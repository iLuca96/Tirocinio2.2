package gestioneUtente.model;

/**
 * 
 * Andamento è una classe con 14 metodi set e get, questa classe è utilizzata per modellare un'Andamento. 
 *
 */
public class Andamento {
	
	/**
	 * Costruttore che inizializza le variabili globali come elementi vuoti
	 */
	public Andamento() {
		this.id = -1;
		this.dataT = "";
		this.ora_inizio = "";
		this.ora_fine = "";
		this.tirocinioID = -1;
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
	 * Metodo che restituisce un dataT
	 * @return dataT tipo String, variabile contente il valore di un dataT
	 */
	public String getDataT() {
		return dataT;
	}
	/**
	 * Metodo che setta un dataT
	 * @param dataT tipo String, variabile contente il valore di un dataT
	 */
	public void setDataT(String dataT) {
		this.dataT = dataT;
	}
	
	/**
	 * Metodo che restituisce un ora_inizio
	 * @return ora_inizio tipo String, variabile contente il valore di un ora_inizio
	 */
	public String getOra_inizio() {
		return ora_inizio;
	}
	/**
	 * Metodo che setta un ora_inizio
	 * @param ora_inizio tipo String, variabile contente il valore di un ora_inizio
	 */
	public void setOra_inizio(String ora_inizio) {
		this.ora_inizio = ora_inizio;
	}
	
	/**
	 * Metodo che restituisce un ora_fine
	 * @return ora_fine tipo String, variabile contente il valore di un ora_fine
	 */
	public String getOra_fine() {
		return ora_fine;
	}
	/**
	 * Metodo che setta un ora_fine
	 * @param ora_fine tipo String, variabile contente il valore di un ora_fine
	 */
	public void setOra_fine(String ora_fine) {
		this.ora_fine = ora_fine;
	}
	
	/**
	 * Metodo che restituisce un tirocinioID
	 * @return tirocinioID tipo Integer, variabile contente il valore di un tirocinioID
	 */
	public int getTirocinioID() {
		return tirocinioID;
	}
	/**
	 * Metodo che setta un tirocinioID
	 * @param tirocinioID tipo Integer, variabile contente il valore di un tirocinioID
	 */
	public void setTirocinioID(int tirocinioID) {
		this.tirocinioID = tirocinioID;
	}

	/**
	 * Metodo che restituisce un NomeCognome
	 * @return NomeCognome tipo Integer, variabile contente il valore di un NomeCognome
	 */
	public String getNomeCognome() {
		return NomeCognome;
	}
	/**
	 * Metodo che setta un NomeCognome
	 * @param nomeCognome tipo String, variabile contente il valore di un Nome Cognome
	 */
	public void setNomeCognome(String nomeCognome) {
		NomeCognome = nomeCognome;
	}
	
	/**
	 * Metodo che restituisce un NomeCognomeStudent
	 * @return NomeCognomeStudent tipo Integer, variabile contente il valore di un NomeCognomeStudent
	 */
	public String getNomeCognomeStudent() {
		return NomeCognomeStudent;
	}
	/**
	 * Metodo che setta un NomeCognomeStudent
	 * @param nomeCognomeStudent tipo Integer, variabile contente il valore di un NomeCognomeStudent
	 */
	public void setNomeCognomeStudent(String nomeCognomeStudent) {
		NomeCognomeStudent = nomeCognomeStudent;
	}

	private int id;
	private String dataT;
	private String ora_inizio;
	private String ora_fine;
	private int  tirocinioID;
	private String NomeCognome;
	private String NomeCognomeStudent;
	
}
