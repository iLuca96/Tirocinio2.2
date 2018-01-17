package gestioneUtente.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gestioneUtente.model.*;
import gestioneStudente.model.*;
import gestioneProfessore_TutorAziendale.model.*;
import gestioneSegreteria.model.*;

/**
 * 
 * TirocinioControl è una classe con 2 metodi e permeti di cancellare/convalidare/confermare/rifiutare/accettare una domanda di tirocinio. 
 * 
 */
public class TirocinioControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	String return_path = "/index.jsp";
	
	public TirocinioControl() {
		super();
	}

	/**
	 * Il metodo doGet cerca di cancellare/convalidare/confermare/rifiutare/accettare una domanda di tirocinio. 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		
	    TirocinioModel Tirociniomodel = null;
	
		try {
			if (action != null) {
				
				if (action.equalsIgnoreCase("delete_training_student"))
				{
					Studente sessione_student = (Studente) request.getSession().getAttribute("student");
					
					 if(sessione_student!=null)		
						 if(sessione_student.getEmail().length()>0)
						 {
							String id = request.getParameter("id");
							int id_int = Integer.parseInt(id);
									
							if(id != null)
							{
								Tirociniomodel = new TirocinioModel();
								
								Tirocinio tirocinio = (Tirocinio) Tirociniomodel.doRetrieveByKey(id_int);
								
								if(tirocinio.getStato().equalsIgnoreCase("In Attesa"))
								{
									Tirociniomodel.doDelete(id_int);
									request.setAttribute("message_success_training", "Richiesta di Tirocinio Cancellata.");
								}
								else
								{
									request.setAttribute("message_fault_training", "Per cancellare la richiesta di Tirocinio lo stato deve essere In Attesa.");
								}
								
								return_path = "/MyTraining.jsp";	
							}
						 }		    
				}
				else
					if (action.equalsIgnoreCase("confirm_student_training_student"))
					{
						Studente sessione_student = (Studente) request.getSession().getAttribute("student");
						
						 if(sessione_student!=null)		
							 if(sessione_student.getEmail().length()>0)
							 {
								String id = request.getParameter("id");
								int id_int = Integer.parseInt(id);
										
								if(id != null)
								{
									Tirociniomodel = new TirocinioModel();
									
									Tirocinio tirocinio = (Tirocinio) Tirociniomodel.doRetrieveByKey(id_int);
									
									if(tirocinio.getStato().equalsIgnoreCase("Accettata"))
									{
										Tirociniomodel.doModify("Confermata",id_int);
										request.setAttribute("message_success_training", "Richiesta di Tirocinio è stata Confermata.");
									}
									else
									{
										request.setAttribute("message_fault_training", "Per confermare la richiesta di Tirocinio lo stato deve essere in Accettata.");
									}
									
									return_path = "/MyTraining.jsp";	
								}
							 }		    
					}
					else
					if (action.equalsIgnoreCase("reject_student_training_student"))
					{
						Studente sessione_student = (Studente) request.getSession().getAttribute("student");
						
						 if(sessione_student!=null)		
							 if(sessione_student.getEmail().length()>0)
							 {
								String id = request.getParameter("id");
								int id_int = Integer.parseInt(id);
										
								if(id != null)
								{
									Tirociniomodel = new TirocinioModel();
									
									Tirocinio tirocinio = (Tirocinio) Tirociniomodel.doRetrieveByKey(id_int);
									
									if(tirocinio.getStato().equalsIgnoreCase("Accettata"))
									{
										Tirociniomodel.doModify("Rifiutata dallo Studente",id_int);
										request.setAttribute("message_success_training", "Richiesta di Tirocinio è stata Rifiutata.");
									}
									else
									{
										request.setAttribute("message_fault_training", "Per rifiutare la richiesta di Tirocinio lo stato deve essere in Accettata.");
									}
									
									return_path = "/MyTraining.jsp";	
								}
							 }		    
					}
					else
						if (action.equalsIgnoreCase("accept_training_student"))
						{
							ProfessoreTutorAziendale sessione_teacher = (ProfessoreTutorAziendale) request.getSession().getAttribute("teacher");
							ProfessoreTutorAziendale sessione_tutor = (ProfessoreTutorAziendale) request.getSession().getAttribute("tutor");
							
							boolean control = false;
							
							if(sessione_teacher!=null)		
								 if(sessione_teacher.getEmail().length()>0)
								 {
									 control = true;
								 }
							
							if(sessione_tutor!=null)		
								 if(sessione_tutor.getEmail().length()>0)
								 {
									 control = true;
								 }
							
							if(control)
							{
								String id = request.getParameter("id");
								int id_int = Integer.parseInt(id);
										
								if(id != null)
								{
									Tirociniomodel = new TirocinioModel();
									
									Tirocinio tirocinio = (Tirocinio) Tirociniomodel.doRetrieveByKey(id_int);
									
									if(tirocinio.getStato().equalsIgnoreCase("In Attesa"))
									{
										Tirociniomodel.doModify("Accettata",id_int);
										request.setAttribute("message_success_training", "Richiesta di Tirocinio Accettata.");
									}
									else
									{
										request.setAttribute("message_fault_training", "Per accettare la richiesta di Tirocinio lo stato deve essere In Attesa.");
									}
									
									return_path = "/RequestTraining.jsp";	
								}
							}
						}
						else
							if (action.equalsIgnoreCase("reject_training_student"))
							{
								ProfessoreTutorAziendale sessione_teacher = (ProfessoreTutorAziendale) request.getSession().getAttribute("teacher");
								ProfessoreTutorAziendale sessione_tutor = (ProfessoreTutorAziendale) request.getSession().getAttribute("tutor");
								
								boolean control = false;
								
								if(sessione_teacher!=null)		
									 if(sessione_teacher.getEmail().length()>0)
									 {
										 control = true;
									 }
								
								if(sessione_tutor!=null)		
									 if(sessione_tutor.getEmail().length()>0)
									 {
										 control = true;
									 }
								
								if(control)
								{
									String id = request.getParameter("id");
									int id_int = Integer.parseInt(id);
											
									if(id != null)
									{
										Tirociniomodel = new TirocinioModel();
										
										Tirocinio tirocinio = (Tirocinio) Tirociniomodel.doRetrieveByKey(id_int);
										
										if(tirocinio.getStato().equalsIgnoreCase("In Attesa"))
										{
											Tirociniomodel.doModify("Rifiutata",id_int);
											request.setAttribute("message_success_training", "Richiesta di Tirocinio Rifiutata.");
										}
										else
										{
											request.setAttribute("message_fault_training", "Per rifiutare la richiesta di Tirocinio lo stato deve essere In Attesa.");
										}
										
										return_path = "/RequestTraining.jsp";	
									}
								}
							}
							else
								if (action.equalsIgnoreCase("convalidate_training_student"))
								{
									Segreteria sessione_segreteria = (Segreteria) request.getSession().getAttribute("segreteria");
									
									boolean control = false;
									
									if(sessione_segreteria!=null)		
										 if(sessione_segreteria.getEmail().length()>0)
										 {
											 control = true;
										 }
									
									if(control)
									{
										String id = request.getParameter("id");
										int id_int = Integer.parseInt(id);
												
										if(id != null)
										{
											Tirociniomodel = new TirocinioModel();
											
											Tirocinio tirocinio = (Tirocinio) Tirociniomodel.doRetrieveByKey(id_int);
											
											if(tirocinio.getStato().equalsIgnoreCase("Completato"))
											{
												Tirociniomodel.doModify("Convalidato",id_int);
												request.setAttribute("message_success_training", "Tirocinio Convalidato.");
											}
											else
											{
												request.setAttribute("message_fault_training", "Per convalidare il Tirocinio lo stato deve essere in Completato.");
											}
											
											return_path = "/segreteria/CompletedTraining.jsp";	
										}
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
}