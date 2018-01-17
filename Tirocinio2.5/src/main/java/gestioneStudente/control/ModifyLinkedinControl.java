package gestioneStudente.control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gestioneStudente.model.*;

/**
 * 
 * ModifyLinkedinControl è una classe che permette ad uno studente di modifcare il link di linkedin nel database.
 *
 */
public class ModifyLinkedinControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static StudenteModel model;
	
	static {
		
			model = new StudenteModel();
		
	}
	
	String return_path = "/PersonalArea.jsp";
	
	public ModifyLinkedinControl() {
		super();
	}

	/**
	 * Il metodo doGet cerca di modificare il link di linkedin nel database.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String link = request.getParameter("link");
		
		String action = request.getParameter("action");
		
		try {
			if (link != null) 
			{									    
				Studente sessione_student = (Studente) request.getSession().getAttribute("student");
				
				if(sessione_student.getMatricola().length()>0)
				{
					Studente bean = new Studente();
					bean.setLinkedin(link);
					bean.setMatricola(sessione_student.getMatricola());
					model.doModifyLinkedin(bean);	
							
					request.getSession().setAttribute("student", model.doRetrieveByKey(sessione_student.getMatricola()));							
					
					request.setAttribute("Edit_Linkedin", "Il profilo linkedin è stato aggiornato!");
				}
			}
			
			if(action!=null)
				if(action.equalsIgnoreCase("delete_link"))
				{					
					Studente sessione_student = (Studente) request.getSession().getAttribute("student");
					if(sessione_student.getMatricola().length()>0)
					{
						Studente bean = new Studente();
						bean.setMatricola(sessione_student.getMatricola());
						bean.setLink_curriculum("");
						model.doModifyLink(bean);	
								
						request.getSession().setAttribute("student", model.doRetrieveByKey(sessione_student.getMatricola()));							
						
						request.setAttribute("Edit_Link", "Il curriculum è stato eliminato!");
					}
					
				}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			
			String email = request.getParameter("email");
			HttpSession session = request.getSession();
		    				      
		    session.setAttribute("editdata_fault", email);		      
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