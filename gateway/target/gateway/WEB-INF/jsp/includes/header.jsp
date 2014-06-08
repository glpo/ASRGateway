<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>ASR B2B Gateway</title>

<link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="resources/css/my_style.css" rel="stylesheet" type="text/css">

</head>
<body>

	<nav class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">ASR B2B Gateway</a>
		</div>
		<div>
			<p class="navbar-text navbar-right">
				Signed in as <a href="#" class="navbar-link">Gleb</a>
			</p>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> Menu <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Profile</a></li>
						<li class="divider"></li>
						<li><a href="/gateway/upload">New Request</a></li>
						<li class="divider"></li>
						<li><a href="/gateway/customers/getAllCustomers">Customers</a></li>
						<li><a href="/gateway/admin/addAttribute">Attributes</a></li>
						<li><a href="/gateway/admin/validationRules">Validation Rules</a></li>
						<li class="divider"></li>
						<li><a href="/gateway/requests">Requests</a></li>
						<li class="divider"></li>
						<li><a href="#">Log Out</a></li>
					</ul></li>
			</ul>
		</div>
	</nav>
	<div class="workingArea">