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
	</head>
	
	<body>
		<c:import url="/inc/menuConnecte.jsp" />
		<br>
		<h1> Historique du cours : ${sessionScope['code']} </h1>
	
		
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