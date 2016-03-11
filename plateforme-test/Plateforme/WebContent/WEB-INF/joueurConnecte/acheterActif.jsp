<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<%@page import="java.util.Vector" %>
<%@page import="modele.Portefeuille" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
		<title>Acheter un actif</title>
	</head>
	
	<body>
		<c:import url="/inc/menuConnecte.jsp" />
		
		<h1>Acheter un actif</h1>
	
		<form method="post" action="<c:url value="/achat" />">
			Titre :
			Quantite :
			<input type="submit" value="Acheter" >
		</form>
			
	</body>
</html>