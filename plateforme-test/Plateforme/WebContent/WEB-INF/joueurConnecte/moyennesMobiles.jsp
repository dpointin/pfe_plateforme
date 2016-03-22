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
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/datepicker.css"/>" />
		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
				<script>
		 	$(function(){
		        $('#datepicker').datepicker({
		            inline: true,
		            showOtherMonths: true,
		            altField: "#datepicker",
		            closeText: 'Fermer',
		            prevText: 'Précédent',
		            nextText: 'Suivant',
		            currentText: 'Aujourd\'hui',
		            monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
		            monthNamesShort: ['Janv.', 'Févr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Août', 'Sept.', 'Oct.', 'Nov.', 'Déc.'],
		            dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
		            dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
		            dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
		            weekHeader: 'Sem.',
		            dateFormat: 'dd-mm-yy'
		        });
	    	});
		 	$(function(){
		    	$('#datepicker2').datepicker({
		        	inline: true,
		            showOtherMonths: true,
		            altField: "#datepicker2",
		            closeText: 'Fermer',
		            prevText: 'Précédent',
		            nextText: 'Suivant',
		            currentText: 'Aujourd\'hui',
		            monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
		            monthNamesShort: ['Janv.', 'Févr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Août', 'Sept.', 'Oct.', 'Nov.', 'Déc.'],
		            dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
		            dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
		            dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
		            weekHeader: 'Sem.',
		            dateFormat: 'dd-mm-yy'
		        });
			});
		</script>
		
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">

       	google.charts.load('current', {'packages':['corechart']});
       	
  	  	google.charts.setOnLoadCallback(drawChart);
		 
	  	function drawChart() {
			  var data = new google.visualization.DataTable(); 
			  
			  // on cree les colonnes
	 			data.addColumn('string', 'Date');
		  		data.addColumn('number', 'Moyenne Mobile');
		  		data.addColumn('number', 'Cours');
				 
				 data.addRows(${baseCent.size()});
				 var i=0;
				 <c:forEach items="${sessionScope['mm']}" var="entry">
				 	<c:set var="date" value="${entry.key}"/>
					<fmt:formatDate var="date2" type="date" dateStyle="short" pattern="dd/MM/yyyy" value="${date.time}"/>
					<c:set var="vecteur" value="${entry.value}"/>
						var j=0;
						data.setCell(i, j, '${date2}');
					<c:forEach items="${vecteur}" var="v" >
						j++;
						data.setCell(i, j, ${v});
					</c:forEach>
					i=i+1;
				 </c:forEach>	
			  
		        // Set chart options
		     	var options = {
					//backgroundColor: '#d2d2d2',
					backgroundColor: {stroke:'black', fill:'#d2d2d2',strokeSize: 1},
					width: 1100,
			        height: 500,
	    			title: 'Cours de fermeture sur la derniere periode',
			        //curveType: 'function',


			    };
	
		        // Instantiate and draw our chart, passing in some options.
		        var chart = new google.visualization.LineChart(document.getElementById('chart'));
		        chart.draw(data, options);
	  	}

		</script>
		<title>Moyennes mobiles</title>
	</head>
	
	<body>
		<c:import url="/inc/menuBourse.jsp" />
		
		<h1> Graphe du cours : ${sessionScope['code']} </h1>
		
		<form method="post" action="<c:url value="/cours" />">
			<table border="1px" style="width:100%">
				<tr>
				<td>Date début : <input type="text" id="datepicker" name="dateDebut"></td>
				<td>Date fin : <input type="text" id="datepicker2" name="dateFin"></td>
				<td><select name="typeGraphe">
				 		<option value="CHART" selected>Courbes</option>
						<option value="CHANDELIER">Chandeliers</option>
						<option value="VOLUMES">Volumes</option>
						<option value="MOYENNEMOBILE">Moyennes Mobiles</option>		
						<option value="TABLEAU">Tableau de valeurs</option>				
					</select>
				</td>
				<td>	
					<input type="submit" value="ChargerCours"/>
				</td>
				</tr>

			</table>
		</form>
		<br/>

		<div id="chart" style="width: 900px; height: 500px"></div>			
	</body>
</html>