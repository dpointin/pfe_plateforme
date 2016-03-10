<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>Graphe</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	  	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
 		<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
		<script>
			 $(function() {
			   $( "#datepicker" ).datepicker();
			 });
			 $(function() {
				   $( "#datepicker2" ).datepicker();
			 });
		</script>
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
 		<script type="text/javascript">
		    // Load the Visualization API and the piechart package.
		    google.load('visualization', '1.0', {
		        'packages' : [ 'corechart' ]
		    });
		 
		    // Set a callback to run when the Google Visualization API is loaded.
		    google.setOnLoadCallback(drawChart);
		 
		    // Callback that creates and populates a data table, instantiates the pie chart,
		    // passes in the data and draws it.
		    function drawChart() {
		        // Create the data table. 
		        var data = google.visualization.arrayToDataTable([
													                <c:forEach items="${sessionScope['cours'].valeurs}" var="entry">
																		<c:set var="date" value="${entry.key}"/>
																		//<fmt:formatDate var="date2" type="both" dateStyle="full" value="${date.time}"/>
																		<c:set var="vecteur" value="${entry.value}"/>
													                    [ '${date2}',  ${vecteur[2]} ],
													                </c:forEach>
																], true);

		                    		 
		        // Set chart options
		     	var options = {
	    			title: 'Cours d\'ouverture',
			        //curveType: 'function',
			        legend: { position: 'bottom' }
			    };


		 
		        // Instantiate and draw our chart, passing in some options.
		        var chart = new google.visualization.LineChart(document.getElementById('chart'));
		        chart.draw(data, options);
		    }
		</script>
	</head>
	
	<body>
		<c:import url="/inc/menuConnecte.jsp" />
		
		<h1> Graphe du cours : ${sessionScope['code']} </h1>
		
		<form method="post" action="<c:url value="/cours" />">
			<p>Date début : <input type="text" id="datepicker" name="dateDebut"></p>
			<p>Date fin : <input type="text" id="datepicker2" name="dateFin"></p>
			<select name="typeGraphe">
		 		<option value="CHART" selected>Courbes</option>
				<option value="CHANDELIER">Chandeliers</option>
				<option value="TABLEAU">Tableau de valeurs</option>				
			</select>
			<input type="submit" value="ChargerCours"/>
		</form>
		<br/>

		<div id="chart" style="width: 900px; height: 500px"></div>		
	</body>
</html>