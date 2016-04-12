<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Accueil</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	</head>
	
	<body>
	  	<c:import url="/inc/menuConnecte.jsp" />
		<br/>
		
		<div id="menu">
		<table>
			<tr> 	<td> Aller vers son portefeuille </td>
					<td> Aller vers la bourse </td>
			</tr>
			<tr>	<td> <a href="<c:url value="/portefeuilleGeneral"/>" > <img src="<c:url value="/inc/portefeuille.jpg"/>" height="200" alt="Portefeuille" /> </a> </td>
					<td> <a href="<c:url value="/bourse"/>" > <img src="<c:url value="/inc/bourse.jpg"/>" height="200" alt="Bourse" /> </a>  </td>
			</tr>
		</table>	
		</div>
</body>
</html>