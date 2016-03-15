<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<%@page import="java.util.Vector" %>
<%@page import="modele.Portefeuille" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">
	      google.charts.load('current', {'packages':['corechart']});
	      google.charts.setOnLoadCallback(drawChart);
	      function drawChart() {
	
	        var data = google.visualization.arrayToDataTable([
	            ['Type', 		'Quantité'],
	          	['Obligation', 	${portefeuille.camembert[0]}],
	          	['Action',  	${portefeuille.camembert[1]}],
	          	['Indice',  	${portefeuille.camembert[2]}],
	          	['Option',  	${portefeuille.camembert[3]}]
	        ]);
	
	        var options = {
	          title: 'Répartition du portefeuille',
	          is3D: true
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('camembert'));
	
	        chart.draw(data, options);
	      }
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
													                <c:forEach items="${sessionScope['titres'].valeurs}" var="entry">
																		<c:set var="date" value="${entry.key}"/>
																		<fmt:formatDate var="date2" type="date" dateStyle="short" value="${date.time}"/>
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
		<title>Gestion de mon portefeuille</title>
	</head>
	
	<body>
		<c:import url="/inc/menuPortefeuille.jsp" />

		<h1>Indicateurs :</h1>
		<table border="1px" style="width:100%">
			<tr><th>Distribution des actifs</th>
				<th>Evolution des titres durant les 100 derniers jours de la bourse</th>
			</tr>
			<tr><td> <div id="camembert" style="width: 400px; height: 400px;"></div></td>
				<td>Courbe des titres</td>
			</tr>
			
		</table>
		
		<h1>Problème d'optimisation de Markowitz :</h1>
		<table>
			<tr>
				<td>Objet financier</td>
				<td>Quantité idéale</td>				
			</tr>
			<tr> 
				<c:forEach var="objetsFinanciers" items="${requestScope['markowitz']}" >
					<td>${objetsFinanciers.key}</td>
					<td>${objetsFinanciers.value}</td>
				</c:forEach>
		</table>		
	</body>
</html>