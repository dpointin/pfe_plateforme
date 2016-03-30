<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

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
			  	['Obligation', 	${portefeuille.camembertQuantite[0]}],
			  	['Action',  	${portefeuille.camembertQuantite[1]}],
			  	['Indice',  	${portefeuille.camembertQuantite[2]}],
			  	['Option',  	${portefeuille.camembertQuantite[3]}]
			]);
			
			var options = {
					backgroundColor: '#d2d2d2',
			 		title: 'Répartition du portefeuille selon la quantité',
			 		is3D: true,
					width:400,
                    height:300
			};
			
			var chart = new google.visualization.PieChart(document.getElementById('camembertQ'));
			
			chart.draw(data, options);
		}
	            
     	google.charts.setOnLoadCallback(drawChart1);
      	function drawChart1() {
			var data = google.visualization.arrayToDataTable([
			    ['string', 		'Somme argent investi'],
			  	['Obligation', 	${portefeuille.camembertPrix[0]}],
			  	['Action',  	${portefeuille.camembertPrix[1]}],
			  	['Indice',  	${portefeuille.camembertPrix[2]}],
			  	['Option',  	${portefeuille.camembertPrix[3]}]
			]);
			
			var options = {
					backgroundColor: '#d2d2d2',
			 		title: 'Répartition du portefeuille selon la somme investie',
			 		is3D: true,
			 		width:400,
                    height:300
			};
			
			var chart = new google.visualization.PieChart(document.getElementById('camembertP'));
			
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
					<fmt:formatDate var="date2" type="date" dateStyle="short" pattern="dd/MM/yyyy" value="${date.time}"/>
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
					//backgroundColor: '#d2d2d2',
					backgroundColor: {stroke:'black', fill:'#d2d2d2',strokeSize: 1},
	    			title: 'Cours de fermeture sur la derniere periode',
			        //curveType: 'function',


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
		<div style="text-align:center">
			<table border="1" style="width:100%">
				<tr><th colspan=2>Résumé du portefeuille</th>
					<th colspan=2>Distribution des actifs</th>
				</tr>
				<tr>
					<td> Argent disponible : <br/> ${sessionScope['portefeuille'].argentDisponible} € </td>
					<c:set var="rendement" value="${sessionScope['portefeuille'].rendement*10000}"/>
					<c:set var="r" value="${fn:substringBefore(rendement,'.')}"/>
					<td> Rendement du portefeuille : <br/> ${r/10000} %</td>
					<td style="width:30%">
						<div id="camembertQ"></div>
					</td>
					<td style="width:30%">
						<div id="camembertP"></div>
					</td> 
				</tr>
				<tr><th colspan=4>Evolution des titres en base 100</th>
				</tr>
				<tr>
					<td colspan=4> <div id="chart" style="width: 80%; height: 400px; text-align: center"></div>	</td>
				</tr>
			</table>
		</div>
	</body>
</html>