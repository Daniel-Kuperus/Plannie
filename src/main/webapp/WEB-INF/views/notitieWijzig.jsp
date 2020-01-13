<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div class="container mt-4">
    <div class="row">
        <form:form action = "/${groepId}/reisItemDetail/${reisItem.reisItemId}/notitieWijzig" method="post" modelAttribute="notitieWijzigingsFormulier">
            <input type="text" name="naam" required="required" value="${reisItems.naam}">
            <input type="date" name="startDatum" required="required" value="${reisItems.startDatum}">
            <input type="text" name="tekst" required="required" value="${reisItems.tekst}">
            <input id="notitieWijzigen" type="submit" class="btn btn-primary" value="Wijzig notitie">
        </form:form>
    </div>

    <a href="/{groepId}/reisItemDetail/{reisItemId}"><button type="text" class="btn btn-primary mt-3" id="annuleren">Annuleren</button></a>
</div>