<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>Actions</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	</head>
	
	<body>
		<c:import url="/inc/menuBourse.jsp" />
		<br>
		<h1> Actions </h1>
		
		<table border="1px" style="width:100%">
			<tr> 	<th>Voir historique</th> <th>Code</th> <th>Libellé</th>
					<th>Rendement dividende</th> <th>Nombre disponible</th>
			</tr>

			<c:forEach var="entry" items="${sessionScope['titres']}" >
				<tr> 
				<c:set var="type" value="${fn:substringAfter(entry['class'],'.')}" />
				<c:if test="${type eq 'Action'}"> 
					<td> <input type="button" value="Historique" onclick="window.location='cours?code=${entry.code}'" ></td>
					<td>${entry.code}</td> <td>${entry.libelle}</td> 
					<c:set var="dividende" value="${entry.dividende*10000}"/>
					<c:set var="divid" value="${fn:substringBefore(dividende,'.')}"/>
					<td>${divid/100} %</td> 
					<td>${entry.nombreDisponible}</td> 
				</c:if>
				</tr>	
			</c:forEach>
			
		</table>
	</body>
</html>