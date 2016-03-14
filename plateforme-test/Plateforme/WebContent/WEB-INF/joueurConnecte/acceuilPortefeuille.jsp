<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil</title>

		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
</head>
<body>
	  	<c:import url="/inc/menuConnecte.jsp" />

		<br>
		
		<table>
			<tr> 	<td> Aller vers son portefeuille </td>
					<td> Aller vers la bourse </td>
			</tr>
			<tr>	<td> <a href="<c:url value="/portefeuilleGeneral"/>" > <img src="<c:url value="/inc/portefeuille.jpg"/>" width="200" height="50" alt="Portefeuille" /> </a> </td>
					<td> <a href="<c:url value="/bourse"/>" > <img src="<c:url value="/inc/bourse.jpg"/>" width="200" height="50" alt="Bourse" /> </a>  </td>
			</tr>
		</table>		

</body>
</html>