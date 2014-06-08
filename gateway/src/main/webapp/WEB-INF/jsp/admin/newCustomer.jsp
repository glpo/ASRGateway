<%@include file="/WEB-INF/jsp/includes/header.jsp" %>

	<p>${message}</p>
	<br>
	<form name="input" action="" method="POST">
		Customer Name: <input type="text" name="name" class="form-control" required="required"><br> 
		Password: <input type="password" name="passwd" class="form-control" required="required"><br>
		Repeat Password: <input type="password" name="passwd2" class="form-control" required="required"><br>
		Phone Number: <input type="text" name="phoneNumber" class="form-control" required="required"><br> 
		Email: <input type="text" name="email" class="form-control" required="required"><br> 
		State: <input type="text" name="state" class="form-control"><br> 
		City: <input type="text" name="city" class="form-control"><br> 
		Street: <input type="text" name="street" class="form-control"><br> 
		<input type="submit" value="Create" class="btn btn-primary">
	</form>	
	
<%@include file="/WEB-INF/jsp/includes/footer.jsp" %>