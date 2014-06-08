<%@include file="includes/header.jsp"%>
<p>${message}</p>
<br>
<form role="form" method="POST" enctype="multipart/form-data" action="/gateway/upload">
	<div class="form-group">
		File to upload: <input type="file" name="file"> <br /> Name: <input type="text" name="name" class="form-control" required="required"><br>
		<input type="submit" value="Upload" class="btn btn-primary">
	</div>
</form>
<%@include file="includes/footer.jsp"%>

