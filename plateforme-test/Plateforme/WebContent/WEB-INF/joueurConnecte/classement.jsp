<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="utf-8" />
		<title>Classement</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	</head>
	
	<body>
		<c:import url="/inc/menuJeu.jsp" />
		<br/>
		
		<h2> Classement des joueurs : </h2>

		<table border="1" style="width:100%">
			<tr> 	<th>Login du joueur</th>
					<th>Argent</th>		
			</tr>
			
			<c:set var="joueur" value="${sessionScope['sessionJoueur']}"/>
			<c:forEach var="entry" items="${classement}" >
				<tr>
					<c:choose>  
						<c:when test="${joueur.login.equals(entry.login)}">
							<td><font color="red">${entry.login}</font></td>					
							<td><font color="red">${entry.calculScore()}</font></td>
						</c:when>
						<c:otherwise>
							<td>${entry.login}</td>					
							<td>${entry.calculScore()}</td>
						</c:otherwise>
					</c:choose>

				</tr>
			</c:forEach>
			
		</table>
	
	</body>
</html>