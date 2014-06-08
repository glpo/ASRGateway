<%@include file="/WEB-INF/jsp/includes/header.jsp" %>

<p>${message}</p>
<br>
<H3>Create New Asr Attribute</H3>

<form name="newAttrForm" action="addAttribute" method="POST" role="form">
	Attribute Name: <input type="text" name="name" class="form-control" required="required"> </br> 
	Asr Form Type: <select class="form-control"
		name="formType">
		<option value="ASR" selected>ASR</option>
		<option value="TRUNKING">Trunking</option>
		<option value="TRANSPORT">Transport</option>
		<option value="EUSA">EUSA</option>
		<option value="FGA">FGA</option>
		<option value="Ring">Ring</option>
		<option value="WAL">WAL</option>
		<option value="ACI">ACI</option>
		<option value="ARI">ARI</option>
		<option value="SALI">SALI</option>
		<option value="MSL">MSL</option>
		<option value="EOD">EOD</option>
		<option value="EVC">EVC</option>
		<option value="Multi EC">Multi EC</option>
		<option value="TQ">TQ</option>
		<option value="VC">VC</option>
		<option value="PC">PC</option>
	</select> </br> <input type="submit" value="Create" class="btn btn-primary">
</form>
<br>
<H3 id="tables-hover-rows">Attributes:</H3>
<br>
<table class="table table-hover">
	<thead>
		<tr>
			<th>#</th>
			<th>Attribute Name</th>
			<th>Asr Form</th>
			<th>Description</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty attributes}">
			<c:forEach var="attr" items="${attributes}">
						${attr}
					</c:forEach>
		</c:if>
	</tbody>
</table>

<%@include file="/WEB-INF/jsp/includes/footer.jsp" %>
