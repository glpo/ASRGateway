<%@include file="includes/header.jsp"%>
<br>
<p>${message}</p>
<h3>ASR Requests:</h3>

<table class="table table-hover">
	<thead>
		<tr>
			<th>Operations</th>
			<th>#</th>
			<th>Customer Name</th>
			<th>Submition Date</th>
			<th>Request Type</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty requests}">
			<c:forEach var="request" items="${requests}">
						${request}
			</c:forEach>
		</c:if>
	</tbody>
</table>

<%@include file="includes/footer.jsp"%>