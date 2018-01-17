<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,gestioneStudente.model.Studente"%>
    
<html>
<head>
<title>
	Tiroini Vecchi
</title>

<%@ include file="fragments/head.html" %>
<%@ include file="fragments/nav.jsp" %>
</head>
<body>

<%  
	
	
	if(sessione_student!=null)		
	{	 if(sessione_student.getEmail().length()>0)
		 { 
		  %>  
			<div class="container">
			<h2>Inserimento Tirocini Vecchi</h2>
			<div class="col-sm-3"> 
			</div>
			<div class="col-sm-5"> 
				<form action="oldTraining" method="post"> 
				  	<input type="hidden" name="action" value="insert_old_training">
					  
				    <div class="form-group"> 
				      <label for="company">Company:</label>
					  <input name="company" type="text" maxlength="64" required class="form-control" placeholder="Inserisci Company">
				    </div>
				    <div class="form-group">
				      <label for="first_last_name">Nome Cognome Tutor Aziendale:</label>
					  <input name="first_last_name" type="text" maxlength="50"  required class="form-control" placeholder="Inserisci Nome Cognome">
				    </div>
				    <div class="form-group">
				      <label for="job">Durata Lavoro:</label>
				      <input type="text" maxlength="10" class="form-control" required placeholder="Inserisci durata del lavoro (ore)" name="job">
				    </div>
				    <div class="form-group">
				      <label for="start_date">Data Inizio:</label>
				      <input type="text" maxlength="10" class="form-control" required placeholder="gg/mm/aaaa" name="start_date">
				    </div>
				    <div class="form-group">
				      <label for="end_date">Data Fine:</label>
				      <input type="text" maxlength="10" class="form-control" required placeholder="gg/mm/aaaa" name="end_date">
				    </div>
				    <div class="form-group">
				      <label for="mansioni">Compiti e mansioni svolte:</label>
				      <textarea type="text" maxlength="252" rows="7" cols="25" class="form-control" required placeholder="Inserisci matricola" name="mansioni"></textarea>
				    </div>
				    <button type="submit" value="Send" class="btn btn-success">Invia </button> 
				    <button type="reset" value="Reset" class="btn btn-danger"> Svuota Campi </button> 
				</form>
			</div>
			
			</div>
<% 		}
   }
   else
   {%>
		<script>  window.location.href = "index.jsp"; </script> 
 <%}%>
	 	
</body>
</html>