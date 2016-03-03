<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="utf-8" />
		<title>Inscription</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
	</head>
	
	<body>
		<c:import url="/inc/menuDebut.jsp" />
	
		<form method="post" action="inscription">
			<fieldset>
				<legend>Inscription</legend>
				<p>Vous pouvez vous inscrire via ce formulaire.</p>
				
				<label for="nom">Login</label>
				<input type="text" id="login" name="login" value="<c:out value="${joueur.login}"/>" size="20" maxlength="20" />
				<span class="erreur">${form.erreurs['login']}</span>
				<br />
				
				<label for="motDeDasse">Mot de passe <span class="requis">*</span></label>
				<input type="password" id="motDePasse" name="motDePasse" value="" size="20" maxlength="20" />
				<span class="erreur">${form.erreurs['motDeDasse']}</span>
				<br />
				
				<label for="confirmation">Confirmation du mot de passe <span class="requis">*</span></label>
				<input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
				<span class="erreur">${form.erreurs['confirmation']}</span>
				<br />
				
				<input type="submit" value="Inscription" class="sansLabel" />
				<br />
				
				<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
				
			</fieldset>
		</form>
	</body>
</html> 