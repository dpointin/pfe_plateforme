<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
<nav>
<ul>
	<li> <a href="<c:url value="/bourse?redir=actions"/>"> Actions </a> </li>
	<li> <a href="<c:url value="/bourse?redir=indices"/>"> Indices </a> </li>
	<li> <a href="<c:url value="/bourse?redir=obligations"/>"> Obligations </a> </li>
</ul>
</nav>
</div> 