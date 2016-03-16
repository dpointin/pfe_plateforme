<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>Obligations</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	</head>
	
	<body>
		<c:import url="/inc/menuBourse.jsp" />
		<br>
		<h1> Obligations </h1>
					
		<table border="1px" style="width:100%">
			<tr> 	<th>Emetteur</th> <th>Prix</th>
					<th>Taux d'intérêt</th> <th>Nombre disponible</th>
			</tr>

			<c:forEach var="entry" items="${sessionScope['obligations']}" >
				<tr> <td>${entry.emetteur}</td> <td>${entry.prix}</td>
					 <c:set var="tauxInterets" value="${entry.tauxInterets*10000}"/>
					 <c:set var="tauxInt" value="${fn:substringBefore(tauxInterets,'.')}"/>
					 <td>${tauxInt/100} %</td> <td>${entry.nombreDisponible}</td>
				</tr>
			</c:forEach>
			
		</table>
	</body>
</html>