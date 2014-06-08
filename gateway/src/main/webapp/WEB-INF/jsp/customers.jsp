<%@include file="includes/header.jsp"%>

<form role="form" method="GET" enctype="multipart/form-data" action="/gateway/customers/addCustomer">
	<div class="form-group">		
		<input type="submit" value="New Customer" class="btn btn-primary">
	</div>
</form>

<p>${message}</p>
<h3>Customers:</h3>

<table class="table table-hover">
	<thead>
		<tr>
			<th>Operations</th>
			<th>#</th>
			<th>Customer Name</th>			
			<th>Phone Number</th>
			<th>Email</th>
			<th>State</th>
			<th>City</th>
			<th>Street</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty customers}">
			<c:forEach var="customer" items="${customers}">
						${customer}
			</c:forEach>
		</c:if>
	</tbody>
</table>

<%@include file="includes/footer.jsp"%>