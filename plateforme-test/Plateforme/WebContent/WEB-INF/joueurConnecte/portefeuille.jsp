<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="utf-8" />
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
		<title>Portefeuille</title>
	</head>
	
	<body>
		<c:import url="/inc/menuPortefeuille.jsp" />
		
		<h1>Mon Portefeuille d'Actifs</h1>
		
		<h2>Résumé du portefeuille</h2>
		<div style="text-align:center">
			<table border="1" style="width:50%; margin: 0 auto">
				<tr>
					<th> Argent disponible </th>
					<th> Rendement du portefeuille </th>
				</tr>
				<tr>
					<td> ${sessionScope['portefeuille'].argentDisponible} € </td>
					<c:set var="rendement" value="${sessionScope['portefeuille'].rendement*10000}"/>
					<c:set var="r" value="${fn:substringBefore(rendement,'.')}"/>
					<td> ${r/10000} %</td>
				</tr>
			</table>
		</div>
		
		<h2>Actifs du portefeuille</h2>
		<table border="1" style="width:100%">
			<tr>
				<th>Objet financier</th> <th>Type</th> <th>Quantité</th>
				<th>Prix Unitaire</th> <th>Détail</th>
			</tr>
		
			<c:forEach var="objetsFinanciers" items="${sessionScope['portefeuille'].quantiteObjetFinancier}" >
				<c:set var="objetFinancier" value="${objetsFinanciers.key}"/>
				<c:set var="quantite" value="${objetsFinanciers.value}" />
				<c:set var="prix" value="${sessionScope['portefeuille'].prixObjetFinancier[objetsFinanciers.key]}" />
				<c:set var="type" value="${fn:substringAfter(objetFinancier['class'],'.')}" />
				<tr>
					<c:choose> 
						<c:when test="${type eq 'Obligation'}"> 
							<c:set var="date" value="${objetFinancier.dateFin}"/>
							<fmt:formatDate var="date2" type="date" dateStyle="short" pattern="dd/MM/yyyy" value="${date.time}"/>
							<td>${objetFinancier.emetteur} (${date2})</td>
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
					<td>${prix} €</td>
					<td>
						<c:if test="${(type eq 'Action') or (type eq 'Indice')}">
							<input type="button" value="Détail/Historique" onclick="window.location='cours?code=${objetFinancier.code}'" >
						</c:if>	
					</td>
				</tr>
			</c:forEach>
		</table>		
	</body>
</html>