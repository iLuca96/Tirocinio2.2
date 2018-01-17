package gestioneStudente.control;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import gestioneStudente.model.*;

/**
 * UploadFile è una classe che permetterà di salvare un file curriculum nel server in modo tale da farlo diventare accessibile.
 */
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static StudenteModel model;
	
	static {
		
			model = new StudenteModel();
		
	}
	
	public UploadFile() {
		super();
	}
	
	/**
	 * Il metodo doPost, tenterà di inserire un curriculum come file in una cartella specifica dell'utente che fa richiesta. 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		if(ServletFileUpload.isMultipartContent(request))
		{
			try
			{
				Studente sessione_student = (Studente) request.getSession().getAttribute("student");
				
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				
				String name_folder = null,dir = null,action= null, file_pdf = null;
				int i = 0;
				
				for(FileItem item : multiparts)
				{
					if(!item.isFormField())
					{						
						String name = new File(item.getName()).getName();					
						
						if(sessione_student!=null && i==0)
						{	
							sessione_student.setMatricola(sessione_student.getMatricola().replaceAll("^\\s+", "")); //toglie lo spazio all'inizio
							sessione_student.setMatricola(sessione_student.getMatricola().replaceAll("\\s+$", "")); //toglie lo spazio alla fine
							name_folder = Folder(sessione_student.getMatricola(),sessione_student.getMatricola().length());
							dir = creaDir(name_folder);
							i=1;
						}
						
						if(file_pdf==null)
							file_pdf = "/Tirocinio2.5/Users/Students/" + name_folder + "/" + name;
						else
							file_pdf = file_pdf + name_folder + "/" + name;
						
						item.write(new File(dir + File.separator + name));
					}
					else
					{							
						if ("action".equals(item.getFieldName()))
						{
							action = item.getString();
						}		
					}
				}
				
				if (action.equalsIgnoreCase("insert_curriculum")) {
					
					Studente bean = new Studente();
					bean.setLink_curriculum(file_pdf);
					bean.setMatricola(sessione_student.getMatricola());
					model.doModifyCurriculum(bean);	
					
					request.getSession().setAttribute("student", model.doRetrieveByKey(sessione_student.getMatricola()));
					request.setAttribute("message_success", "Curriculum inserito con successo.");
				}
			}
			catch(Exception ex)
			{
				request.setAttribute("message_danger", "File upload failed due to : " + ex);
			}
		}
		else
		{
			request.setAttribute("message_danger", "Sorry this servlet only handles file upload request.");
		}
		
	      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/PersonalArea.jsp");
		  dispatcher.forward(request, response);
	}
	
	/**
	 * Il metodo è utile per pulire un nome dagli spazi e dagli * sostituiendoli con il _
	 * @param name tipo String, variabile che in input ha un possibile nome di una cartella
	 * @param n tipo int, variabile che misura la lunghezza del nome di una cartella
	 * @return name_folder tipo String, variabile che restituisce il nome di una cartella pulita da (spazi) e * con _
	 */
	public String Folder(String name,int n)
	{		
		String name_folder = "";
		
		for(int i=0;i<n;i++)
		{						
			String comparison = name.substring(i, i+1);
			
			if((comparison.equalsIgnoreCase(" "))||(comparison.equalsIgnoreCase("*")))
			{	
				name_folder = name_folder + "_";
			}
			else
			{
				name_folder = name_folder + name.substring(i, i+1);
				
			}
		}
		
	 return name_folder;
	}
	
	/**
	 * Il metodo aggiunge alla variabile dir il nome della cartella che gli viene passato.
	 * @param name_folder tipo String, variabile che riceve il nome della cartella  
	 * @return Dir tipo String, variabile che contiene il path dove verrà memorizzata una cartella
	 * 
	 */
	private static String creaDir(String name_folder)
	  {
	    //String Dir = "C:/Users/ciro9/eclipse-workspace/Tirocinio2.5/WebContent/Users/Students/" + name_folder;
		String Dir = "C:/apache-tomcat-8.5.11/webapps/Tirocinio2.5/Users/Students/" + name_folder;
		
	    new File(Dir).mkdir();
	    return Dir;
	  }
}