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
	          title: 'Répartition du portefeuille'
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('camembert'));
	
	        chart.draw(data, options);
	      }
	    </script>
		<title>Gestion de mon portefeuille</title>
	</head>
	
	<body>
		<c:import url="/inc/menuConnecte.jsp" />
		
		<h1>Indicateurs :</h1>
		<table>
			<tr><th>Distribution des actifs</th>
				<th>Evolution des titres durant les 100 derniers jours de la bourse</th>
			</tr>
			<tr><td> <div id="piechart" style="width: 400px; height: 400px;"></div></td>
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