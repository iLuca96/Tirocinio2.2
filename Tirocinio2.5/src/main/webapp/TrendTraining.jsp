<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,gestioneprofessoretutoraziendale.model.ProfessoreTutorAziendale,gestioneutente.model.AndamentoModel,gestioneutente.model.Andamento,gestioneutente.model.Time,gestioneutente.model.TirocinioModel"%>
    
<html>
<head>
<title>
	I Miei Tirocini
</title>

<%@ include file="fragments/head.html" %>
<%@ include file="fragments/nav.jsp" %>
</head>
<body>

<%  
	String id = request.getParameter("id");
	int id_int = 0;
	
	if(id != null)
		id_int = Integer.parseInt(id);
	
	AndamentoModel andamentoModel;
	
	andamentoModel = new AndamentoModel();
	
	//Tirocinio tirocinio = (Tirocinio) request.getAttribute("order");
	
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
			request.setAttribute("trend", andamentoModel.requestTrend("",id_int));
		
			Collection<?> trend = (Collection<?>) request.getAttribute("trend");
		  %>  
			<div class="container">
			<h2>Andamento Tirocinio di (<span id="nomecognome"></span>) </h2>
			<%
			if(request.getAttribute("message_success_training")!=null)
			{
				%> 
				<div class="alert alert-success">
			      <strong> <%=request.getAttribute("message_success_training") %> </strong>
				</div>
			  <%
			}
			
			if(request.getAttribute("message_fault_training")!=null)
			{
				%> 
				<div class="alert alert-danger">
			      <strong> <%=request.getAttribute("message_fault_training") %> </strong>
				</div>
			  <%
			}
			%>
			<div class="col-lg-12">
               <div class="panel panel-default">
					<div class="panel-heading">
					<%
					if(sessione_teacher!=null)		
						 if(sessione_teacher.getEmail().length()>0)
						 {%>
							 Professore <%=sessione_teacher.getNome()%> <%=sessione_teacher.getCognome()%>
						 <%}
							
					if(sessione_tutor!=null)		
						 if(sessione_tutor.getEmail().length()>0)
						 {%>
						 	Tutor Aziendale <%=sessione_tutor.getNome()%> <%=sessione_tutor.getCognome()%>
					 <%}%>
                       		
			        </div>
			        <!-- /.panel-heading -->
			        <div class="panel-body">
                        <%
                        int i = 0;
                        if (trend != null && trend.size() != 0) {
							Iterator<?> it_trends = trend.iterator();
							
							Calendar start = Calendar.getInstance();
							Calendar end = Calendar.getInstance();
							
							long tothour = 0;
							long totminute = 0;
							int idTirocinio = 0;
							%>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                           <%
							while (it_trends.hasNext()) {
								Andamento andamento = (Andamento) it_trends.next();
								if(i==0)
								{
									
								%>
									<script> document.getElementById("nomecognome").innerHTML = "<%=andamento.getNomeCognomeStudent()%>"; </script>
                                        <tr>
                                            <th>Data</th>
                                            <th>Ora Inizio</th>
                                            <th>Ora Fine</th>
                                            <th>Ore di Lavoro</th>
                                            <th>Azione</th>
                                        </tr>
                                <% i=1;
                                }
                                
                                int year = Integer.parseInt(andamento.getDataT().substring(0,4));
                                int month = Integer.parseInt(andamento.getDataT().substring(5,7));
                                int day = Integer.parseInt(andamento.getDataT().substring(8,10));
                                int time_hour_start = Integer.parseInt(andamento.getOra_inizio().substring(0,2));
                                int time_minute_start = Integer.parseInt(andamento.getOra_inizio().substring(3,5));
                                
                                int time_hour_end = Integer.parseInt(andamento.getOra_fine().substring(0,2));
                                int time_minute_end = Integer.parseInt(andamento.getOra_fine().substring(3,5));
                                
                                start.set(year,month,day,time_hour_start,time_minute_start);
                                
                                end.set(year,month,day,time_hour_end,time_minute_end);
                             
								Time time = new Time();
								tothour = tothour + time.calcHour(start.getTimeInMillis(), end.getTimeInMillis());
								totminute = totminute + time.calcMinute(start.getTimeInMillis(), end.getTimeInMillis(),time.calcHour(start.getTimeInMillis(), end.getTimeInMillis()));
                                %>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><%=andamento.getDataT()%></td>
                                            <td><%=andamento.getOra_inizio().subSequence(0, 5)%></td>
                                            <td><%=andamento.getOra_fine().subSequence(0, 5)%></td>
                                            <td><%=time.calcTime(start.getTimeInMillis(),end.getTimeInMillis())%></td>
                                            <td><a href="ModifyTimeTrend.jsp?id=<%=andamento.getId()%>">Modifica Ore</a></td>
                                        </tr>
                             <%
                             	idTirocinio = andamento.getTirocinioId();
								}%>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                            <br><br><br>
                                 <%
                              	int k = 0;
                                String stamp = "";
                                String stamp2 = "";
                                
                              	for(k = 0; totminute>=60; k++)
                            	 {
                            	  	totminute = totminute - 60;
                            	 }
                              	
                              	 if(k>0)
                              		tothour = tothour + k;
                            	 
                            	 if(totminute>0 && totminute<10)
                            	 {
                            		 stamp = "0"+totminute+" minuti";
                            		 stamp2 = stamp;
                            	 }
                            	 else
                            		 if(totminute>=10)
                            		 {
                            			 stamp = totminute+" minuti";
                            			 stamp2 = stamp;
                            		 }
                            	 
                            	 long hours = 0;
                            	  
                            	 if(tothour==0)
                            	 {
                            		 hours = 150-1;
                            		 
                            		 stamp2="" + (60 - totminute) + " minuti";
                            	 }
                            	 else
                            	 {
                            		 if(totminute>0)
                            		 {
                            			 stamp2="" + (60 - totminute) + " minuti";
                            			 hours = 150-tothour-1;
                            		 }
                            		 else
                            			 hours = 150-tothour;
                            	 }
                            	 
                            	 if(hours<=0)
                            	 {
                            		 TirocinioModel tirocinioModel = new TirocinioModel();
                            		 tirocinioModel.doModify("Completato",idTirocinio);
                            	 }
                            	 
                            	 %>
                                Ore di lavoro Totali:	<strong> <%if(tothour>0){%><%=tothour%> ore <%if(stamp!=""){%>e <%}}%><%=stamp%></strong> <br><br>
                                Ore di lavoro Rimanenti: <strong>  <%if(hours>0){%><%=hours%> ore <%if(stamp!=""){%>e <%}%> <%=stamp2%><%}else{%>Tirocinio Completato<%}%></strong>
                            <%
							}
							else
							{%>
								Andamento non Disponibile riprovare più tardi.
								<a href="PersonalAreaTutorProfessore.jsp">Area Riservata</a>
							<%}%>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <div class="col-sm-4">
	                <h2>Inserimento ore di Lavoro</h2>
	                <form action="timeWork" method="post">
					   <input type="hidden" name="action" value="insert_time_work">
					   <input type="hidden" name="id" value="<%=id_int%>"> 
					    <div class="form-group">
					      <label for="data">Data *</label> 
					      <input type="text" class="form-control" required maxlength="10" placeholder="aaaa-mm-gg" name="data">
					    </div>
					    <div class="form-group">
					      <label for="ora_inizio">Ora Inizio *</label> 
					      <input type="text" class="form-control" required maxlength="5" placeholder="hh:mm" name="ora_inizio">
					    </div>
					    <div class="form-group">
					      <label for="ora_fine">Ora Fine *</label> 
					      <input type="text" class="form-control" required maxlength="5" placeholder="hh:mm" name="ora_fine">
					    </div>
					    * Tutti i campi sono obbligatori &ensp;&ensp;&ensp;
					    <button type="submit" value="Send" class="btn btn-primary">Aggiungi Ore</button>
				  	</form>
			  	</div>
                <!-- /.col-lg-6 -->		
			</div>
<%
   }
   else
   {%>
		<script>  window.location.href = "index.jsp"; </script> 
 <%}%>
	 	
</body>
</html>