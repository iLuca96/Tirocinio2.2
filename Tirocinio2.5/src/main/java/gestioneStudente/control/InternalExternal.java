package gestioneStudente.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gestioneProfessore_TutorAziendale.model.*;
import gestioneStudente.model.Studente;
import gestioneUtente.model.*;

/**
 * 
 * InternalExternal è una classe che permette di far effettuare una domanda di tirocino interno esterno a uno studente. 
 *
 */
public class InternalExternal extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String return_path = "/PersonalArea.jsp";
	
	public InternalExternal() {
		super();
	}

	/**
	 * Il metodo doGet cerca di inserire una domanda di tirocinio interno o esterno, inviata dallo studente. 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		TirocinioModel tirocinioModel = null;
	    ProfessoreTutorAziendaleModel Tutormodel = null;
	
		try {
			if (action != null) {
				
				if (action.equalsIgnoreCase("insert_training_internal"))
				{
					String email = request.getParameter("email");
					Tutormodel = new ProfessoreTutorAziendaleModel();
					
					ProfessoreTutorAziendale sessione_teacher = (ProfessoreTutorAziendale) Tutormodel.doRetrieveByInternal(email);
					
					if(sessione_teacher!=null)		
					{
						if(sessione_teacher.getEmail().length()>0)
						 {
							Studente sessione_student = (Studente) request.getSession().getAttribute("student");
							tirocinioModel = new TirocinioModel();
							
							Tirocinio bean = new Tirocinio();
							bean.setStato("In Attesa");
							bean.setTipo("Interno");
							bean.setMatricola_studente(sessione_student.getMatricola());
							bean.setSegreteria_username("segreteria");
							bean.setTutor_username(sessione_teacher.getUsername());
							tirocinioModel.doSave(bean);
							
							request.setAttribute("session_training", "Il tirocinio Interno con il professore \"" + sessione_teacher.getEmail() + "\" è stato inserito nei tuoi tirocini, per visualizzarlo clicca su \"I Miei Tirocini\"");
						 }
						else 
						{
							request.setAttribute("session_training_fault", "Il professore con email \"" + email + "\" non è stato trovato!");
						}
					}
					else 
					{
						request.setAttribute("session_training_fault", "Il professore con email \"" + email + "\" non è stato trovato!");
					}
				}
				else
					if (action.equalsIgnoreCase("insert_training_external"))
					{
						String email = request.getParameter("email");
						
						Tutormodel = new ProfessoreTutorAziendaleModel();
						
						ProfessoreTutorAziendale sessione_tutor = (ProfessoreTutorAziendale) Tutormodel.doRetrieveByExternal(email);
						
						if(sessione_tutor!=null)		
						{
							if(sessione_tutor.getEmail().length()>0)
							 {
								Studente sessione_student = (Studente) request.getSession().getAttribute("student");
								tirocinioModel = new TirocinioModel();
								
								Tirocinio bean = new Tirocinio();
								bean.setStato("In Attesa");
								bean.setTipo("Esterno");
								bean.setMatricola_studente(sessione_student.getMatricola());
								bean.setSegreteria_username("segreteria");
								bean.setTutor_username(sessione_tutor.getUsername());
								tirocinioModel.doSave(bean);
								
								request.setAttribute("session_training", "Il tirocinio Esterno con il Turor Aziendale \"" + sessione_tutor.getEmail() + "\" è stato inserito nei tuoi tirocini, per visualizzarlo clicca su \"I Miei Tirocini\"");
							 }
							else 
							{
								request.setAttribute("session_training_fault", "Il Turor Aziendale con email \"" + email + "\" non è stato trovato!");
							}
						}
						else 
						{
							request.setAttribute("session_training_fault", "Il Turor Aziendale con email \"" + email + "\" non è stato trovato!");
						}
					}
					
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());	      
		}	
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(return_path);
		dispatcher.forward(request, response);
	}
	
	/**
	 * Il metodo riceve una richiesta dal client e richiama il medoto doGet(request, response) per svolgere la procedura aspettata.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * Il metodo confronta l'email passata con una espressione regolare, per verificare se la variabile passata è una email valida
	 * @param email tipo Boolean, Variabile che viene cofrontata con le espressioni regolari per verificare se è una email valida
 	 * @return true/false valore boolean che se è false allora il parametro passato non è una email valida, true altrimenti.
	 */
	public boolean ValidateEmail(String email)
	{
		Pattern pattern = Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
		Matcher matcher = pattern.matcher(email);
		 
		if(matcher.matches())
			return true;
		else
			return false;	
	}
	
	/**
	 * Il metodo confronta l'email passata con una espressione regolare, per verificare se la variabile passata è una email valida come uno studente
	 * @param email tipo Boolean, Variabile che viene cofrontata con le espressioni regolari per verificare se è una email valida come uno studente
 	 * @return true/false valore boolean che se è false allora il parametro passato non è una email valida, true altrimenti.
	 */
	public boolean IsStudent(String email)
	{
		Pattern pattern = Pattern.compile("[a-zA-Z0-9._%-]+@[studenti]+\\.[unisa]+\\.[a-zA-Z]{2,4}");
		Matcher matcher = pattern.matcher(email);
		 
		if(matcher.matches())
			return true;
		else
			return false;	
	}
	
	/**
	 * Il metodo confronta l'email passata con una espressione regolare, per verificare se la variabile passata è una email valida come un professore
	 * @param email tipo Boolean, Variabile che viene cofrontata con le espressioni regolari per verificare se è una email valida come un professore
 	 * @return true/false valore boolean che se è false allora il parametro passato non è una email valida, true altrimenti.
	 */
	public boolean IsTeacher(String email)
	{
		Pattern pattern = Pattern.compile("[a-zA-Z0-9._%-]+@[unisa]+\\.[a-zA-Z]{2,4}");
		Matcher matcher = pattern.matcher(email);
		 
		if(matcher.matches())
			return true;
		else
			return false;	
	}
}