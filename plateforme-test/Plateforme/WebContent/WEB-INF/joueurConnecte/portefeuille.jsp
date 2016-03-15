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
		<title>Portefeuille</title>
	</head>
	
	<body>
		<c:import url="/inc/menuPortefeuille.jsp" />
		
		<h1>Mon Portefeuille d'Actifs</h1>
		
		<h2>Résumé du portefeuille</h2>
		<table>
			<tr>
				<td>Argent disponible : ${sessionScope['portefeuille'].argentDisponible}<br/>
				Rendement : ${sessionScope['portefeuille'].rendement}</td>
			
				<td>
					<form method="post" action="<c:url value="/portefeuilleGeneral" />">
						<input type="submit" value="Gestion de mon portefeuille" >
					</form>
				</td>
			</tr>
		</table>
		
		<h2>Actifs du portefeuille</h2>
		<table>
			<tr>
				<td>Objet financier</td> <td>Type</td> <td>Quantité</td>
				<td>Prix Unitaire</td> <td>Détail</td> <td>Vente</td>
			</tr>
		
			<c:forEach var="objetsFinanciers" items="${sessionScope['portefeuille'].quantiteObjetFinancier}" >
				<c:set var="objetFinancier" value="${objetsFinanciers.key}"/>
				<c:set var="quantite" value="${objetsFinanciers.value}" />
				<c:set var="prix" value="${sessionScope['portefeuille'].prixObjetFinancier[objetsFinanciers.key]}" />
				<c:set var="type" value="${fn:substringAfter(entry['class'],'.')}" />
				<tr>
					<c:choose> 
						<c:when test="${type eq 'Obligation'}"> 
							<td>${objetFinancier.emmetteur}</td>
						</c:when>
						<c:when test="${type eq 'Option'}"> 
							<td>${objetFinancier.titre.code}</td>
						</c:when>
						<c:otherwise> 
							<td>${objetFinancier.code}</td> 
						</c:otherwise>
					</c:choose>
					<td>${type}</td>
				 	<td>${quantite}</td>
					<td>${prix}</td>
					<td>Bouton détail</td>
					<td>Bouton vente</td>
				</tr>
			</c:forEach>
		</table>		
	</body>
</html>