package gestioneUtente.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gestioneUtente.model.*;
import gestioneStudente.model.*;
import gestioneProfessore_TutorAziendale.model.*;
import gestioneSegreteria.model.*;

/**
 * 
 * LoginControl è una classe con 5 metodi, che permette di effettuare il login allo studente, professore, tutor aziendale, segreteria. 
 *
 */
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	String return_path = "/index.jsp";
	
	public LoginControl() {
		super();
	}

	/**
	 * Il metodo doGet permette di effettuare il login allo studente, professore, tutor aziendale, segreteria. 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		
		StudenteModel model = null;
	    ProfessoreTutorAziendaleModel Tutormodel = null;
	    SegreteriaModel segreteriaModel = null;
	    
		try {
			if (action != null) {
				
				if (action.equalsIgnoreCase("login"))
					{
						String email = request.getParameter("email");
						String psw = request.getParameter("psw");
						
						psw = md5.hashCode(psw, "MD5");
						
						request.removeAttribute("student");
						request.removeAttribute("teacher");
						request.removeAttribute("tutor");
						
						if(ValidateEmail(email))
						{
							if(email.equalsIgnoreCase("segreteria@segreteria.unisa.it"))
							{
								segreteriaModel = new SegreteriaModel();
								request.getSession().setAttribute("segreteria", segreteriaModel.loginSegreteria(email,psw));
							}
							else
							{	
								if(IsStudent(email))
								{
									model = new StudenteModel();
									request.getSession().setAttribute("student", model.loginStudente(email,psw));
								}
								else
								{
									
									if(IsTeacher(email))
									{
										Tutormodel = new ProfessoreTutorAziendaleModel();
										request.getSession().setAttribute("teacher", Tutormodel.loginProfessoreTutor(email,psw));
									}
									else
									{
										Tutormodel = new ProfessoreTutorAziendaleModel();
										request.getSession().setAttribute("tutor", Tutormodel.loginProfessoreTutor(email,psw));
									}
								}
							}
						}
						else
						{
							HttpSession session_login = request.getSession();
							
							segreteriaModel = new SegreteriaModel();
							request.getSession().setAttribute("segreteria", segreteriaModel.loginSegreteria(email,psw));
							
							Segreteria sessione_segreteria = (Segreteria) request.getSession().getAttribute("segreteria");
							
							if(sessione_segreteria!=null)		
								 if(sessione_segreteria.getEmail().length()<=0)
								 {
									 session_login.removeAttribute("segreteria");
									 
									 model = new StudenteModel();
										request.getSession().setAttribute("student", model.loginStudente(email,psw));
										
										Studente sessione_student = (Studente) request.getSession().getAttribute("student");

										if(sessione_student!=null)		
											 if(sessione_student.getEmail().length()<=0)
											 {
												 session_login.removeAttribute("student");
												 
												 Tutormodel = new ProfessoreTutorAziendaleModel();
													request.getSession().setAttribute("teacher", Tutormodel.loginProfessoreTutor(email,psw));
													
													ProfessoreTutorAziendale sessione_teacher = (ProfessoreTutorAziendale) request.getSession().getAttribute("teacher");

													if(sessione_teacher!=null)		
														 if(sessione_teacher.getEmail().length()<=0)
														 {
															 session_login.removeAttribute("teacher");
															 Tutormodel = new ProfessoreTutorAziendaleModel();
																request.getSession().setAttribute("tutor", Tutormodel.loginProfessoreTutor(email,psw));
															
																ProfessoreTutorAziendale sessione_tutor = (ProfessoreTutorAziendale) request.getSession().getAttribute("tutor");
																
																if(sessione_tutor!=null)		
																	 if(sessione_tutor.getEmail().length()<=0)
																	 {
																		 session_login.removeAttribute("tutor");
																	 }
														 }
											 }
								 }
						}
							
						return_path = "/Login.jsp";			    
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
	 * Il metodo confronta l'email passata con una espressione regolare, per verificare se la variabile passata è una email valida per studente
	 * @param email tipo Boolean, Variabile che viene cofrontata con le espressioni regolari per verificare se è una email valida per studente
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
	 * Il metodo confronta l'email passata con una espressione regolare, per verificare se la variabile passata è una email valida per professore
	 * @param email tipo Boolean, Variabile che viene cofrontata con le espressioni regolari per verificare se è una email valida per professore
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