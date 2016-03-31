<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@page import="modele.Portefeuille"%> 

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Exporter le portefeuille au format .csv</title>
	</head>
	<body>
		<%		Portefeuille p=(Portefeuille) session.getAttribute("portefeuille");
				out.print(p.ecrire());
		%>
	</body>
</html>