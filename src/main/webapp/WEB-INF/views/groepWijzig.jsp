<div class="container mt-4">
    <div class="row">
        <form:form action = "/groepsNaamWijzigen" method="post" modelAttribute="groepsNaamWijzigingsFormulier">
            <input type="text" name="groepsNaam" required="required" value="${groep.groepsNaam}">


            <form:button id="groepsNaamWijzigen" type="submit" class="btn btn-primary mt-3"  >Wijzig groepsnaam</form:button>

        </form:form>
    </div>

    <a href="/groepDetail"><button type="text" class="btn btn-primary mt-3" id="annuleren">Annuleren</button></a>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>