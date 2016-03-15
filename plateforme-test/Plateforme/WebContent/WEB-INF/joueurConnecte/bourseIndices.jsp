<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>Indices</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	</head>
	
	<body>
		<c:import url="/inc/menuBourse.jsp" />
		<br>
		<h1> Indices </h1>
			
		<table>
			<tr> 	<th>Voir historique</th> <th>Code</th> <th>Libellé</th>
					<th>Type</th> <th>Nombre disponible</th>
			</tr>

			<c:forEach var="entry" items="${sessionScope['titres']}" >
				<tr> 
				<c:set var="type" value="${fn:substringAfter(entry['class'],'.')}" />
				<c:if test="${type eq 'Indice'}"> 
					<td> <input type="button" value="Historique" onclick="window.location='cours?code=${entry.code}'" ></td>
					<td>${entry.code}</td> <td>${entry.libelle}</td> <td>${type}</td>
					<td>${entry.nombreDisponible}</td>
				</c:if>
		 		</tr>
			</c:forEach>
			
		</table>
	</body>
</html>