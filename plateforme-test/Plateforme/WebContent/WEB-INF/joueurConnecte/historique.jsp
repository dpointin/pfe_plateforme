<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8"/>
		<title>Historique</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/datepicker.css"/>" />
	  	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
		<script>
		 	$(function(){
		        $('#datepicker').datepicker({
		            inline: true,
		            showOtherMonths: true,
		            monthNames: [ "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" ],
		            dayNamesMin: ['Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam'],
		        });
	    	});
		 	$(function(){
		    	$('#datepicker2').datepicker({
		        	inline: true,
		            showOtherMonths: true,
		            monthNames: [ "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" ],
		            dayNamesMin: ['Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam'],
		        });
			});
		</script>
  
	</head>
	
	<body>
		<c:import url="/inc/menuBourse.jsp" />
		<br>
		<h1> Historique du cours : ${sessionScope['code']} </h1>
	
		
		<form method="post" action="<c:url value="/cours" />">
			<table border="1px" style="width:100%">
				<tr>
				<td>Date début : <input type="text" id="datepicker" name="dateDebut"></td>
				<td>Date fin : <input type="text" id="datepicker2" name="dateFin"></td>
				<td><select name="typeGraphe">
				 		<option value="CHART" selected>Courbes</option>
						<option value="CHANDELIER">Chandeliers</option>
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
		<br/>
		<table border="1px" style="width:100%">
			<tr> 	<th>Date</th> <th>Ouverture</th> <th>Haut</th> <th>Bas</th> 
					<th>Fermeture</th> <th>Volume</th> <th>Fermeture ajustée</th>
			</tr>

			<c:forEach var="entry" items="${sessionScope['cours'].valeurs}" >
				<c:set var="date" value="${entry.key}"/>
				<c:set var="vecteur" value="${entry.value}" />
				<tr>
					<fmt:formatDate var="date2" type="date" dateStyle="short" value="${date.time}"/>
					<td>${date2}</td> <td>${vecteur[0]}</td> <td>${vecteur[1]}</td>
					<td>${vecteur[2]}</td> <td>${vecteur[3]}</td>
					<td>${vecteur[4]}</td> <td>${vecteur[5]}</td>
				</tr>
			</c:forEach>
			
		</table>
	</body>
</html>