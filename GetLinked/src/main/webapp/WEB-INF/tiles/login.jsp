<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="loginUrl" value="/login" />


<div class="container-fluid fill-height"
	style="padding-top: 0%; width: 100%; height: auto; min-height: 100%; margin: 0; padding-bottom: 0%; background-attachment: fixed; background: url('/img/loginBackground.jpg'); background-position: center center; background-size: cover; padding-left: 0%; padding-right: 0;">
	<div class=" container-fluid  d-flex flex-column"
		style="background-color: rgb(0, 0, 0, .55); background-attachment: fixed; min-height: 100%; top: 0; min-width: 100%; height: auto; width: 100%; padding-right: 0; margin: 0;">
		<div class="container-fluid"
			style="padding-left: 30%; padding-top: 10%; width: 100%; padding-right: 37%">
			<div class="jumbotron" style="background-color: #FFFFFF;">
				<c:if test="${param.error != null}">
					<div class="login-error" id="errormessagediv">Incorrect
						username or password.</div>
				</c:if>
				<div class="container-fluid" id="maindiv0"
					style="margin: 0; padding-top: 0; padding-left: 0;"></div>
				<div class="container-fluid" id="maindiv"
					style="margin: 0; padding-top: 0; padding-left: 0;">
					<form method="post" id="loginForm" action="${loginUrl}">

						<div id="localError" style="color: green;">${localError}</div>


						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

						<div class="form-group">
							<label for="exampleInputEmail1"
								style="font-family: 'Alatsi'; font-size: 20px; color: black;">Email
								address</label> 
								<input type="email" class="form-control"
								style="border-radius: 0; font-family: 'Alatsi'; color: black;"
								id="exampleInputEmail_Test1" aria-describedby="emailHelp"
								placeholder="Enter email">
								
								<input type="email" class="form-control"
								style="border-radius: 0; font-family: 'Alatsi'; color: black; display:none;"
								id="exampleInputEmail1" aria-describedby="emailHelp"
								placeholder="Enter email" name="username"> <small
								id="emailHelp" class="form-text text-muted"
								style="font-family: 'Alatsi'; font-size: 13px; color: black;">We'll
								never share your email with anyone else.</small>
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1"
								style="font-family: 'Alatsi'; font-size: 20px; color: black;">Password</label>
							<input type="password" class="form-control"
								style="border-radius: 0; font-family: 'Alatsi'; color: black;"
								name="password" id="exampleInputPassword1"
								placeholder="Password">
						</div>

						<div class="form-check">
							<input class="form-check-input" type="checkbox" value=""
								id="defaultCheck1"> <label class="form-check-label"
								for="defaultCheck1"> Login as a professional </label>
						</div>

						<button type="button" onclick="submitLoginForm();"
							style="width: 100%; background-color: black; color: white;"
							class="btn ">Sign In</button>

					</form>
				</div>

				<div id="seconddiv"
					style="padding-top: 5%; font-family: 'Alatsi'; color: black;">
					Not a member? <a href='/signup'>Sign Up</a> now
				</div>
			</div>

		</div>
	</div>
</div>

<script>
	(function() {
		document.getElementById("localError").innerHTML = "";
	});

	function submitLoginForm() {
		document.getElementById("seconddiv").innerHTML = "Loading..";

		if (document.getElementById("defaultCheck1").checked === true) {
			//setting new email id
			document.getElementById("exampleInputEmail1").value = document
					.getElementById("exampleInputEmail_Test1").value.trim()
			+':professional';
		} else {
			//setting new email id
			document.getElementById("exampleInputEmail1").value = document
					.getElementById("exampleInputEmail_Test1").value.trim()
			+':user';
		}
		//email id is disabled when submitting
		document.getElementById("exampleInputEmail_Test1").disabled = "disabled";
		$("#loginForm").submit();
	}
</script>




