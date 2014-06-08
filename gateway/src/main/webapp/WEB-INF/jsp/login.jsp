<html lang="en">
<head>
<meta charset="utf-8">
<title>ASR B2B Gateway</title>

<!-- <link href="resources/css/my_style.css" rel="stylesheet" type="text/css">-->
<link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css">
</head>
<body>

	<nav class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">ASR B2B Gateway</a>
		</div>
	</nav>

	<div class="" id="loginModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3>Have an Account?</h3>
		</div>
		<div class="modal-body">
			<div class="well">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#login" data-toggle="tab">Login</a></li>
					<li><a href="#create" data-toggle="tab">Create Account</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane active in" id="login">
						<form class="form-horizontal" action="login" method="POST">
							<fieldset>
								<div id="legend">
									<legend class="">Login</legend>
								</div>
								<div class="control-group">
									<!-- Username -->
									<label class="control-label" for="username">Username</label>
									<div class="controls">
										<input type="text" id="login" name="login" placeholder="" class="input-xlarge" required="required">
									</div>
								</div>

								<div class="control-group">
									<!-- Password-->
									<label class="control-label" for="password">Password</label>
									<div class="controls">
										<input type="password" id="passwd" name="passwd" placeholder="" class="input-xlarge" required="required">
									</div>
								</div>


								<div class="control-group">
									<!-- Button -->
									<br>
									<div class="controls">
										<input type="submit" class="btn btn-primary" value="Login">
									</div>
									<label class="control-label" for="message"> <%
     if (request.getAttribute("message") != null) {
 		request.getAttribute("message");
     }
 %>
									</label>
								</div>
							</fieldset>
						</form>
					</div>
					<div class="tab-pane fade" id="create">
						<form id="tab" class="form-horizontal" action="/gateway/register" method="POST">
							<div id="legend">
								<legend class="">New Account</legend>
							</div>

							<div class="control-group">
								<label class="control-label">Name</label>
								<div class="controls">
									<input type="text" value="" name="name" class="input-xlarge" required="required"><br>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Password</label>
								<div class="controls">
									<input type="password" value="" name="passwd" class="input-xlarge" required="required"><br>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Repeat Password</label>
								<div class="controls">
									<input type="password" value="" name="password2" class="input-xlarge" required="required"><br>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Phone Number</label>
								<div class="controls">
									<input type="text" value="" name="phoneNumber" class="input-xlarge" required="required"><br>
								</div>
							</div>							
							<div class="control-group">
								<label class="control-label">Email</label>
								<div class="controls">
									<input type="text" value="" name="email" class="input-xlarge"><br>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Sate</label>
								<div class="controls">
									<input type="text" value="" name="state" class="input-xlarge"><br>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">City</label>
								<div class="controls">
									<input type="text" value="" name="city" class="input-xlarge"><br>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Street</label>
								<div class="controls">
									<input type="text" value="" name="street" class="input-xlarge"><br>
								</div>
							</div>
							<!-- <div class="control-group">
								<label class="control-label">Address</label>
								<div class="controls">
									<textarea value="" rows="3" class="input-xlarge"></textarea>
									<br>
								</div>
							</div>
 -->							<br>
							<div class="control-group">
								<div class="controls">
									<button type="submit" class="btn btn-primary">Create Account</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="resources/js/jquery.js"></script>
	<script src="resources/js/bootstrap.js"></script>
</body>
</html>