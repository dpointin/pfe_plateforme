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
		        var chart = new google.visualization.Chart(document.getElementById('curve_chart'));
		        chart.draw(data, options);
		    }
		</script>
	</head>
	
	<body>
		<c:import url="/inc/menuConnecte.jsp" />
		<br>
		<h1> Graphe du cours : ${requestScope['code']} </h1>
	
		
		<form method="post" action="<c:url value="/cours" />">
			<input type="date" name="dateDebut"/>
			<input type="date" name="dateFin"/>
			<select name="typeGraphe">
		 		<option value="CHART" selected>Courbes</option>
				<option value="CHANDELIER">Chandeliers</option>
				<option value="TABLEAU">Tableau de valeurs</option>				
			</select>
			<input type="submit" value="ChargerCours"/>
		</form>
		

			
		</table>
	</body>
</html>