<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="monmenu">

	<ul class="niveau1">
		<li> Bourse
			<ul class="niveau2">
				<li> <a href="<c:url value="/bourse?redir=actions"/>"> Actions </a> </li>
				<li> <a href="<c:url value="/bourse?redir=indices"/>"> Indices </a> </li>
				<li> <a href="<c:url value="/bourse?redir=obligations"/>"> Obligations </a> </li>
			</ul>
		</li>
		<li> Portefeuille
			<ul class="niveau2">
				<li> <a href="<c:url value="/portefeuilleGeneral"/>"> Accueil </a> </li>
				<li> <a href="<c:url value="/achat"/>"> Acheter </a> </li>
				<li> <a href="<c:url value="/vente"/>"> Vendre </a> </li>
				<li> <a href="<c:url value=""/>"> Indicateurs </a> </li>
				<li> <a href="<c:url value=""/>"> Exporter </a> </li>		
			</ul>
		</li>
	</ul>
<!-- <li> <a href="<c:url value="/bourse"/>"> Bourse </a> </li>
		
		<li class="active"> <a href="<c:url value="/portefeuilleGeneral"/>"> Portefeuille </a> </li>
		<li> <a href="<c:url value="/achat"/>"> Acheter </a> </li>
		<li> <a href="<c:url value="/deconnexion"/>"> Deconnexion </a> </li>
	</ul>

	<ul>
		<li> <a href="<c:url value="/bourse?redir=actions"/>"> Accueil </a> </li>
		<li> <a href="<c:url value="/bourse?redir=indices"/>"> Acheter </a> </li>
		<li> <a href="<c:url value="/bourse?redir=obligations"/>"> Vendre </a> </li>
		<li> <a href="<c:url value="/bourse?redir=obligations"/>"> Indicateurs </a> </li>
		<li> <a href="<c:url value="/bourse?redir=obligations"/>"> Exporter </a> </li>
	</ul>
 -->
</div> 