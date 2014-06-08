<%@include file="/WEB-INF/jsp/includes/header.jsp"%>
<H3>New Validation Rule:</H3>
<br>
<p>${message}</p>

<form name="validationRuleForm" action="addValidationRule" method="POST" role="form">
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
	</select> <br>
	Validator Name: <input type="text" name="validatorName" class="form-control" required="required"> <br>
	Validator Class: <input type="text" name="validatorClass" class="form-control" required="required" value="com.glpo.gateway.oc.GenericParameterValidator"> <br> 
	Target Attributes: <input type="text" name="targetAttributes" class="form-control" required="required"> <br>
	Required clause: <textarea name="required" class="form-control"> </textarea> <br>
	Optional clause: <textarea name="optional" class="form-control"> </textarea> <br>
	Prohibited clause:<textarea name="prohibited" class="form-control"> </textarea> <br>
	<input type="submit" value="Create" class="btn btn-primary"> <br>
</form>
<br>
<h3>Validation Rules:</h3>
<table class="table table-hover">
	<thead>
		<tr>
			<th>#</th>
			<th>Validator Name</th>
			<th>Target Attribute</th>
			<th>Validator Class</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty validators}">
			<c:forEach var="validator" items="${validators}">
						${validator}
			</c:forEach>
		</c:if>
	</tbody>
</table>

<%@include file="/WEB-INF/jsp/includes/footer.jsp"%>