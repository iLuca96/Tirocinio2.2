package gestioneProfessore_TutorAziendale.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gestioneUtente.model.*;
import gestioneProfessore_TutorAziendale.model.*;

/**
 * 
 * AndamentoControl è una classe che si occupa di modificare/inserire la data e l'ora di un lavoro di uno studente.
 *
 */
public class AndamentoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	String return_path = "/index.jsp";
	
	public AndamentoControl() {
		super();
	}

	/**
	 * Il metodo doGet cerca di modificare/inserire la data e l'ora di un lavoro di uno studente.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		
	    AndamentoModel andamentoModel = null;
	
		try {
			if (action != null) {
				
				if (action.equalsIgnoreCase("insert_time_work"))
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
						
						String data = request.getParameter("data");
						String ora_inizio = request.getParameter("ora_inizio");
						String ora_fine = request.getParameter("ora_fine");
						
						if(id != null)
						{
							andamentoModel = new AndamentoModel();
							
							Andamento bean = new Andamento();
							bean.setDataT(data);
							bean.setOra_inizio(ora_inizio);
							bean.setOra_fine(ora_fine);
							bean.setTirocinioID(id_int);
							andamentoModel.doSave(bean);
							
							request.setAttribute("message_success_training", "Ore di lavoro Aggiunte.");
							
							return_path = "/TrendTraining.jsp";	
						}
					}
				}
				else
					if (action.equalsIgnoreCase("modify_time_work"))
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
							
							String data = request.getParameter("data");
							String ora_inizio = request.getParameter("ora_inizio");
							String ora_fine = request.getParameter("ora_fine");
							
							if(id != null)
							{
								andamentoModel = new AndamentoModel();
								
								Andamento bean = new Andamento();
								bean.setId(id_int);
								bean.setDataT(data);
								bean.setOra_inizio(ora_inizio);
								bean.setOra_fine(ora_fine);
								bean.setTirocinioID(id_int);
								andamentoModel.doModify(bean);
								
								request.setAttribute("message_success_training", "Ore di lavoro Modificate.");
								
								return_path = "/ModifyTimeTrend.jsp";	
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