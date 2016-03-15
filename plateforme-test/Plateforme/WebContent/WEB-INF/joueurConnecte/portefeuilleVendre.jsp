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
		<title>Vente</title>
	</head>
	
	<body>
		<c:import url="/inc/menuPortefeuille.jsp" />
		
		<h1>Vente</h1>
		
		<h2>Actifs du portefeuille</h2>
		<table border="1px" style="width:100%">
			<tr>
				<th>Objet financier</th> <th>Type</th> <th>Quantité</th>
				<th>Prix Unitaire</th> <th>Quantité à vendre</th> <th>Vente</th>
			</tr>
		
			<c:forEach var="objetsFinanciers" items="${sessionScope['portefeuille'].quantiteObjetFinancier}" >
				<tr>
					<form method="post" action="<c:url value="/vente" />">
						<c:set var="objetFinancier" value="${objetsFinanciers.key}"/>
						<c:set var="quantite" value="${objetsFinanciers.value}" />
						<c:set var="prix" value="${sessionScope['portefeuille'].prixObjetFinancier[objetsFinanciers.key]}" />
						<c:set var="type" value="${fn:substringAfter(objetFinancier['class'],'.')}" />
					
						<c:choose> 
							<c:when test="${type eq 'Obligation'}"> 
								<td>${objetFinancier.emetteur} date fin</td>
							</c:when>
							<c:otherwise> 
								<td>${objetFinancier.code}</td> 
							</c:otherwise>
						</c:choose>
						<td>${type}</td>
					 	<td>${quantite}</td>
						<td>${prix} €</td>
						<td> <input type="text" name="quantite" > </td>
						<c:choose> 
							<c:when test="${type eq 'Obligation'}"> 
								<td> <input type="submit" name="${objetFinancier.emetteur}" value="Vendre">  </td>
							</c:when>
							<c:otherwise> 
								<td> <input type="submit" name="${objetFinancier.code}" value="Vendre">  </td>
							</c:otherwise>
						</c:choose>

					</form>
				</tr>
			</c:forEach>
		</table>		
	</body>
</html>