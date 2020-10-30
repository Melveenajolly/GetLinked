<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html style="height: 100%">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="ISO-8859-1">
<title><tiles:insertAttribute name="title" /></title>
<link href='https://fonts.googleapis.com/css?family=Fascinate'
	rel='stylesheet'>
<link href='https://fonts.googleapis.com/css?family=Brawler'
	rel='stylesheet'>
<link href='https://fonts.googleapis.com/css?family=Coda Caption:800'
	rel='stylesheet'>
<link href='https://fonts.googleapis.com/css?family=Alatsi'
	rel='stylesheet'>
<link href='https://fonts.googleapis.com/css?family=Anton'
	rel='stylesheet'>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<link rel="stylesheet" href="/css/main.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>


<body style="background-color: #F8F8FF; height: 100%">
	<nav class="navbar navbar-expand-lg navbar-dark   fixed-top"
		style="background-color: #000000; min-height: 73px;">
		<img alt="" style="padding-left: 6%; overflow-x: hidden;"
			src="/img/network-512.png" width="9%" height="auto"> <a
			class="navbar-brand" style="padding-left: 1%" href="/"><b
			style="font-family: 'Fascinate'; font-size: 26px; letter-spacing: 1px">GetLinked</b></a>



		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<div class="upperA"></div>
			<ul class="nav navbar-right ml-auto">
				<li class="nav-item active"><a class="nav-link" href="/"
					style="font-family: 'Alatsi'; font-size: 14px; color: white; letter-spacing: 2px">HOME
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="/about"
					style="font-family: 'Alatsi'; font-size: 14px; color: white; letter-spacing: 2px">ABOUT</a>
				</li>

				<sec:authorize access="!isAuthenticated()">
					<li class="nav-item"><a class="nav-link" href="/register"
						style="font-family: 'Alatsi'; font-size: 14px; color: white; letter-spacing: 2px">REGISTER
							AS A PROFESSIONAL</a></li>
					<li class="nav-item"><a class="nav-link" href="/login"
						style="font-family: 'Alatsi'; font-size: 14px; color: white; letter-spacing: 2px">LOGIN/SIGNUP</a>
					</li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="nav-item"><a class="nav-link" href="/admin"
						style="font-family: 'Alatsi'; font-size: 14px; color: white; letter-spacing: 2px">ADMIN</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<c:if test="${userType.equals('user')}">
					<li class="nav-item active"><a class="nav-link" href="/myhires"
						style="font-family: 'Alatsi'; font-size: 14px; color: white; letter-spacing: 2px">MY HIRES
							<span class="sr-only">(current)</span>
					</a></li>
					</c:if>
					<c:if test="${userType.equals('professional')}">
					<li class="nav-item active"><a class="nav-link" href="/mywork"
						style="font-family: 'Alatsi'; font-size: 14px; color: white; letter-spacing: 2px">MY WORK
							<span class="sr-only">(current)</span>
					</a></li>
					</c:if>
					
					<li class="nav-item"><a class="nav-link"
						style="font-family: 'Alatsi'; font-size: 14px; color: white; letter-spacing: 2px"
						href="javascript:$('#logoutForm').submit();">LOGOUT</a></li>
				</sec:authorize>

			</ul>
		</div>
	</nav>

	<c:url var="logoutLink" value="/logout" />
	<form id="logoutForm" method="post" action="${logoutLink}">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<div class="container-fluid"
		style="padding-left: 0; padding-right: 0; padding-bottom: 0; height: 100%; width: 100%; margin: 0">

		<tiles:insertAttribute name="content" />
	</div>




	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<!-- <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script> -->
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
		crossorigin="anonymous"></script>

</body>
</html>