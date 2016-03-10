<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>Historique</title>
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
  
	</head>
	
	<body>
		<c:import url="/inc/menuConnecte.jsp" />
		<br>
		<h1> Historique du cours : ${sessionScope['code']} </h1>
	
		
		<form method="post" action="<c:url value="/cours" />">
		<!--	<input type="date" name="dateDebut"/>
					<input type="date" name="dateFin"/>  -->
			<p class="ll-skin-vigo">Date début : <input type="text" id="datepicker" class="ll-skin-vigo" name="dateDebut"></p>
			<p class="ll-skin-vigo">Date fin : <input type="text" id="datepicker2" class="ll-skin-vigo" name="dateFin"></p>
			<select name="typeGraphe">
		 		<option value="CHART" selected>Courbes</option>
				<option value="CHANDELIER">Chandeliers</option>
				<option value="TABLEAU">Tableau de valeurs</option>				
			</select>
			<input type="submit" value="ChargerCours"/>
		</form>
		
		<table>
			<tr> 	<td>Date</td> <td>Ouverture</td> <td>Haut</td> <td>Bas</td> 
					<td>Fermeture</td> <td>Volume</td> <td>Fermeture ajustée</td>
			</tr>

			<c:forEach var="entry" items="${sessionScope['cours'].valeurs}" >
				<c:set var="date" value="${entry.key}"/>
				<c:set var="vecteur" value="${entry.value}" />
				<tr>
					
					<fmt:formatDate var="date2" type="both" dateStyle="full" value="${date.time}"/>
					<td>${date2}</td> <td>${vecteur[0]}</td> <td>${vecteur[1]}</td>
					<td>${vecteur[2]}</td> <td>${vecteur[3]}</td>
					<td>${vecteur[4]}</td> <td>${vecteur[5]}</td>
				</tr>
			</c:forEach>
			
		</table>
	</body>
</html>