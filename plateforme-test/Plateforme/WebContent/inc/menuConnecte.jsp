<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
<nav>
	<ul>
		<li> <a href="<c:url value="/bourse"/>"> Bourse </a>
			<ul>
				<li> <a href="<c:url value="/bourse?redir=actions"/>"> Actions </a> </li>
				<li> <a href="<c:url value="/bourse?redir=indices"/>"> Indices </a> </li>
				<li> <a href="<c:url value="/bourse?redir=obligations"/>"> Obligations </a> </li>
			</ul>
		</li>
		<li> <a href="<c:url value="/portefeuilleGeneral"/>"> Portefeuille </a> 
			<ul>
				<li> <a href="<c:url value="/achat"/>"> Acheter </a> </li>
				<li> <a href="<c:url value="/vente"/>"> Vendre </a> </li>
				<li> <a href="<c:url value="/portefeuilleGeneral?redir=indicateurs"/>"> Indicateurs </a> </li>
				<li> <a href="<c:url value=""/>"> Exporter </a> </li>		
			</ul>
		</li> 
		<li> <a href="<c:url value="/deconnexion"/>"> Déconnexion </a>
		</li>
	</ul>
</nav>
</div> 