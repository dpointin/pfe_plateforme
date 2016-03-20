<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>Chandeliers</title>
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
													                <c:forEach items="${cours.valeurs}" var="entry">
																		<c:set var="date" value="${entry.key}"/>
																		<fmt:formatDate var="date2" type="date" dateStyle="short" pattern="dd/MM/yyyy" value="${date.time}"/>
																		<c:set var="vecteur" value="${entry.value}" />
													                    [ '${date2}',  ${vecteur[2]}, ${vecteur[0]}, ${vecteur[3]}, ${vecteur[1]} ],
													                </c:forEach>
																], true);
		                    		 
		        // Set chart options
		        var options = {
		          title: 'Chandeliers',
		         //curveType: 'function',
		          legend: { position: 'bottom' }
		        };

		 
		        // Instantiate and draw our chart, passing in some options.
		        var chart = new google.visualization.CandlestickChart(document.getElementById('chandelier'));
		        chart.draw(data, options);
		    }
		</script>
	</head>
	
	<body>
		<c:import url="/inc/menuBourse.jsp" />

		<h1> Chandeliers du cours : ${sessionScope['code']} </h1>
			
		<form method="post" action="<c:url value="/cours" />">
			<table border="1px" style="width:100%">
				<tr>
				<td>Date début : <input type="text" id="datepicker" name="dateDebut"></td>
				<td>Date fin : <input type="text" id="datepicker2" name="dateFin"></td>
				<td><select name="typeGraphe">
				 		<option value="CHART" selected>Courbes</option>
						<option value="CHANDELIER">Chandeliers</option>
						<option value="VOLUMES">Volumes</option>
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
		
		<div id="chandelier" style="width: 900px; height: 500px"></div>	
	</body>
</html>