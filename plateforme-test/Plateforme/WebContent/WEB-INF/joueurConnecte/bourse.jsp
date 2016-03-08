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
		<c:import url="/inc/menuConnecte.jsp" />
		<br>
		<h1> Titres </h1>
		
		<table>
			<tr> 	<td>Voir historique</td> <td>Code</td> <td>Libellé</td>
					<td>Type</td> <td>Rendement dividende</td> <td>Nombre disponible</td>
			</tr>

			<c:forEach var="entry" items="${sessionScope['titres']}" >
				<tr> <td> <input type="button" value="Historique" onclick="window.location='cours?code=${entry.code}'" ></td>
				<c:set var="type" value="${fn:substringAfter(entry['class'],'.')}" />
				<td>${entry.code}</td> <td>${entry.libelle}</td> <td>${type}</td>
				<c:choose>  
					<c:when test="${type eq 'Action'}"> 
						<c:set var="dividende" value="${entry.dividende*10000}"/>
						<c:set var="test" value="${fn:substringBefore(dividende,'.')}"/>
						<td>  ${test/100} % </td> 
					</c:when>
					<c:when test="${type eq 'Indice'}"> <td> - </td> </c:when>
				</c:choose>
				<td>${entry.nombreDisponible}</td> </tr>
			</c:forEach>
			
		</table>
	</body>
</html>