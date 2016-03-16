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
			<% Vector<ObjetFinancier> objetsFinanciersVec = (Vector<ObjetFinancier>) request.getAttribute("objetsFinanciers") ; 
			
			if(objetsFinanciersVec!=null) {			
 				for(int i=0;i<objetsFinanciersVec.size();i++){	
 					out.print("<tr>");		%>	
					<form method="post" action="<c:url value="/achat" />">
					<%
 					if (objetsFinanciersVec.get(i) instanceof Titre) {
 						out.print("<td>"+ ((Titre)objetsFinanciersVec.get(i)).getCode() +"</td>");
 						out.print("<td>"+ ((Titre)objetsFinanciersVec.get(i)).getLibelle() +"</td>");
 						out.print("<td>"+ ((Titre)objetsFinanciersVec.get(i)).getPrix() +"€</td>");
 					} else {
 						out.print("<td>"+ ((Obligation)objetsFinanciersVec.get(i)).getEmetteur() +"</td>");
 						out.print("<td> 10 ans </td>");
 						out.print("<td>"+ ((Obligation)objetsFinanciersVec.get(i)).getPrix() +"€</td>");
 					}	
 					out.print("<td>"+ objetsFinanciersVec.get(i).getNombreDisponible() +"</td>");
 					
 					out.print("<td> <input type=\"text\" name=\"quantite\" > </td>");
 					if (objetsFinanciersVec.get(i) instanceof Titre) {
 						out.print("<td> <input type=\"submit\" name=\"" + ((Titre)objetsFinanciersVec.get(i)).getCode() + "\" value=\"Acheter\"> </td>");
 					} else {
 						out.print("<td> <input type=\"submit\" name=\"" + ((Obligation)objetsFinanciersVec.get(i)).getEmetteur() + "\" value=\"Acheter\"> </td>");
 	  				}%>
 					</form>
					<%out.print("</tr>");
 				}			
 			} %>
 		</table> 	
	</body>
</html>