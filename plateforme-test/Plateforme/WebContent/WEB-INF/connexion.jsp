<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>Connexion</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	</head>
	
	
	<body>
		<c:import url="/inc/menuDebut.jsp" />
		
		<c:if test="${sessionScope.messageAlerte!=null}">
			Vous devez être connecté pour accéder à cette page, merci de vous connecter avec le formulaire ci-dessous. 
		</c:if>
		
		<form method="post" action="<c:url value="/connexion" />">
			<fieldset>
				<legend>Connexion</legend>
				<p>Vous pouvez vous connecter via ce formulaire.</p>
				
				<label for="nom">Login <span class="requis">*</span></label>
				<input type="text" id="login" name="login" value="<c:out value="${joueur.login}"/>" size="20" maxlength="60" />
				<span class="erreur">${form.erreurs['login']}</span>
				<br />
				
				<label for="motDeDasse">Mot de passe <span class="requis">*</span></label>
				<input type="password" id="motDePasse" name="motDePasse" value="" size="20" maxlength="20" />
				<span class="erreur">${form.erreurs['motDePasse']}</span>
				<br />
				
				<input type="submit" value="Connexion" class="sansLabel" />
				<br />
				
				<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
				
				<%-- Vérification de la présence d'un objet joueur en session --%>
				<c:if test="${!empty sessionScope.sessionJoueur}">
					<%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
					<p class="succes">Vous êtes connecté(e) avec l'adresse : ${sessionScope.sessionJoueur.login}</p>
				</c:if> 
				
			</fieldset>
		</form>
	</body>
	
</html> 