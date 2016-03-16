<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%@page import="java.util.Vector" %>
<%@page import="modele.Portefeuille" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">
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
	      
       	google.charts.load('current', {'packages':['corechart']});
          
  	  	google.charts.setOnLoadCallback(drawChart2);
		 

	  	function drawChart2() {
			  var data = new google.visualization.DataTable(); 
			  
			  // on cree les colonnes
	 			data.addColumn('string', 'Date');
			  	<c:forEach items="${titres}" var="entry">
			  		data.addColumn('number', '${entry}');
			  	</c:forEach>

				 
				 data.addRows(${baseCent.size()});
				 var i=0;
				 <c:forEach items="${sessionScope['baseCent']}" var="entry">
				 	<c:set var="date" value="${entry.key}"/>
					<fmt:formatDate var="date2" type="date" dateStyle="short" value="${date.time}"/>
					<c:set var="vecteur" value="${entry.value}"/>
						j=0;
						data.setCell(i, j, '${date2}');
					<c:forEach items="${vecteur}" var="v" >
						j++;
						data.setCell(i, j, ${v});
					</c:forEach>
					i=i+1;
				 </c:forEach>	
			  
		        // Set chart options
		     	var options = {
	    			title: 'Cours de fermeture sur la derniere periode',
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
			<tr><td> <div id="camembert" style="width: 400px; height: 400px;"></div> </td> 
				<td> <div id="chart" style="width: 700px; height: 500px"></div>	</td>
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