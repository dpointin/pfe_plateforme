<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<%@page import="java.util.Vector" %>
<%@page import="modele.Portefeuille" %>
<%@page import="modele.ObjetFinancier" %>

<%@page import="modele.Titre" %>
<%@page import="modele.Obligation" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
		<title>Acheter un actif</title>
	</head>
	
	<body>
		<c:import url="/inc/menuPortefeuille.jsp" />
		
		<h1>Acheter un actif</h1>

		<% if (request.getAttribute("erreur")!=null) {
			out.print("<p style=\"color: #ff0000\" >" +request.getAttribute("erreur") + "</p>");
		}
		%>
		
		<h2>Résumé du portefeuille</h2>
		<div style="text-align:center">
			<table border="1px" style="width:50%; margin: 0 auto">
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
	
		<h2>Recherche d'un actif</h2>
		<form method="post" action="<c:url value="/achat" />">
			<input type="text" name="motscles" placeholder="Recherche...">
			<select name="type">
				<option value="TOUS" selected>Tous les types</option>
				<option value="ACTION">Action</option>
				<option value="INDICE">Indice</option>
				<option value="OBLIGATION">Obligation</option>
			</select>		
			<input type="submit" value="Recherche">	
		</form>
		<br/>	
			
		<table border="1px" style="width:100%">
			<tr>
				<th colspan=2>Objet Financier</th>
				<th>Prix unitaire</th>
				<th>Nombre disponible</th>
				<th>Quantité</th>
				<th>Acheter</th>
			</tr>
			
			<c:forEach var="entry" items="${objetsFinanciers}" >		
				<form method="post" action="<c:url value="/achat"/>">
					<c:set var="type" value="${fn:substringAfter(entry['class'],'.')}" />
					<c:choose>  
						<c:when test="${type eq 'Obligation'}">
							<tr>
								<td>${entry.emetteur} (10 ans)</td>
								<c:set var="tauxInteret" value="${entry.tauxInteret*10000}"/>
							 	<c:set var="tauxInt" value="${fn:substringBefore(tauxInteret,'.')}"/>
							 	<td>${tauxInt/100} %</td>
								<td>${entry.prix}€</td>	
								<td>${entry.nombreDisponible}</td>
								<td> <input type="text" name="quantite" > </td>
								<td> <input type="submit" name="${entry.emetteur}" value="Acheter"> </td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>${entry.libelle} (${entry.code})</td>
								<c:set var="d" value="0.0"/>
 								<c:if test="${type eq 'Action'}">
									<c:set var="dividende" value="${entry.dividende*10000}"/>
									<c:set var="d" value="${fn:substringBefore(dividende,'.')}"/>
								</c:if>
								<td>${d/100} % </td> 
								<td>${entry.prix}€</td>
								<td>${entry.nombreDisponible}</td>
								<td> <input type="text" name="quantite" > </td>
								<td> <input type="submit" name="${entry.code}" value="Acheter"> </td>
							</tr>
						</c:otherwise>
					</c:choose>
				 </form>
			</c:forEach>

 		</table> 	
	</body>
</html>