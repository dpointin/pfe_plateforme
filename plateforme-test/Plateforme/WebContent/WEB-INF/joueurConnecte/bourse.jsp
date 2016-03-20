<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>Bourse</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	</head>
	
	<body>
		<c:import url="/inc/menuBourse.jsp" />
		<br/>
		
		<h2> Faire une recherche : </h2>
		<form method="post" action="<c:url value="/bourse" />">
			<input type="text" name="motscles" placeholder="Recherche...">
			<select name="type">
				<option value="TOUS" selected>Tous les types</option>
				<option value="ACTION">Action</option>
				<option value="INDICE">Indice</option>
				<option value="OBLIGATION">Obligation</option>
			</select>		
			<input type="submit" value="Recherche">	
		</form>
		
		<br/>	
		
		<h2> Actifs de la Bourse : </h2>

		<table border="1px" style="width:100%">
			<tr> 	<th>Prix / Historique</th> <th>Objet financier</th>
					<th>Type</th> <th>Rendement dividende</th> <th>Nombre disponible</th>
			</tr>

			<c:forEach var="entry" items="${objetsFinanciers}" >
				<tr> 
				<c:set var="type" value="${fn:substringAfter(entry['class'],'.')}" />
				<c:choose>  
					<c:when test="${type eq 'Action'}">
						<td> <input type="button" value="Historique" onclick="window.location='cours?code=${entry.code}'" ></td> 
						<td>${entry.libelle} (${entry.code})</td>
						<td>${type}</td>
						<c:set var="dividende" value="${entry.dividende*10000}"/>
						<c:set var="test" value="${fn:substringBefore(dividende,'.')}"/>
						<td>  ${test/100} % </td> 
					</c:when>
					<c:when test="${type eq 'Indice'}"> 
						<td> <input type="button" value="Historique" onclick="window.location='cours?code=${entry.code}'" ></td>
						<td>${entry.libelle} (${entry.code})</td>
						<td>${type}</td>
						<td> - </td>
					</c:when>
					<c:otherwise>
						<td>${entry.prix}€</td>
						<td>${entry.emetteur} (10 ans)</td>
						<td>${type}</td>
						<c:set var="tauxInterets" value="${entry.tauxInterets*10000}"/>
					 	<c:set var="tauxInt" value="${fn:substringBefore(tauxInterets,'.')}"/>
					 	<td>${tauxInt/100} %</td>				
					</c:otherwise>
				</c:choose>
				<td>${entry.nombreDisponible}</td> </tr>
			</c:forEach>
			
		</table>
	</body>
</html>