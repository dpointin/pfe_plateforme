<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
		<title>Vente</title>
	</head>
	
	<body>
		<c:import url="/inc/menuPortefeuille.jsp" />
		
		<h1>Vente</h1>
		
		<% if (request.getAttribute("erreur")!=null) {
			out.print("<p style=\"color: #ff0000\" >" +request.getAttribute("erreur") + "</p>");
		}
		%>
		
		<h2>Actifs du portefeuille</h2>
		<table border="1" style="width:100%">
			<tr>
				<th>Objet financier</th> <th>Type</th> <th>Quantité</th>
				<th>Prix Unitaire</th> <th>Quantité à vendre</th> <th>Vente</th>
			</tr>
		
			<c:forEach var="objetsFinanciers" items="${sessionScope['portefeuille'].quantiteObjetFinancier}" >
				<form method="post" action="<c:url value="/vente" />">
					<c:set var="objetFinancier" value="${objetsFinanciers.key}"/>
					<c:set var="quantite" value="${objetsFinanciers.value}" />
					<c:set var="prix" value="${sessionScope['portefeuille'].prixObjetFinancier[objetsFinanciers.key]}" />
					<c:set var="type" value="${fn:substringAfter(objetFinancier['class'],'.')}" />
					<c:choose> 
						<c:when test="${type eq 'Obligation'}"> 	
							<tr>						
								<c:set var="date" value="${objetFinancier.dateFin}"/>
								<fmt:formatDate var="date2" type="date" dateStyle="short" pattern="dd/MM/yyyy" value="${date.time}"/>
								<td>${objetFinancier.emetteur} (${date2})</td>
								<td>${type}</td>
							 	<td>${quantite}</td>
								<td>${prix} €</td>
								<td> <input type="text" name="quantite" > </td>
								<td> <input type="submit" name="${objetFinancier.emetteur}" value="Vendre">  </td>
							</tr>
						</c:when>
						<c:otherwise> 
							<tr>
								<td>${objetFinancier.code}</td> 
								<td>${type}</td>
							 	<td>${quantite}</td>
								<td>${prix} €</td>
								<td> <input type="text" name="quantite" > </td>
								<td> <input type="submit" name="${objetFinancier.code}" value="Vendre">  </td>
							</tr>
						</c:otherwise>
					</c:choose>
				</form>
			</c:forEach>
		</table>		
	</body>
</html>