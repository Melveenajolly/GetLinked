3<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container-fluid d-flex flex-column "
	style="padding-top: 0%; width: 100%; height: auto; min-height: 100%; margin: 0; padding-bottom: 0%; background: url('/img/signup (2).jpg'); background-position: center center; background-size: cover; padding-left: 0%; padding-right: 0;">
	<div class=" container-fluid  d-flex flex-column"
		style="background-color: rgb(0, 0, 0, .55); min-height: 100%; top: 0; min-width: 100%; height: auto; width: 100%; padding-right: 0; margin: 0;">
		<div class="container-fluid"
			style="padding-left: 30%; padding-top: 10%; width: 100%; padding-right: 37%">
			<div class="jumbotron" style="background-color: #FFFFFF;">
				<div class="container-fluid"
					style="margin: 0; padding-top: 0; padding-left: 0;">
					<form:form method="post" modelAttribute="user" id="signupformid">
					<div class="login-error" id="errormessagediv">
					<form:errors path="email"></form:errors>
					<form:errors path="plainPassword"></form:errors>
					<form:errors path="password"></form:errors>
					</div>
						 <c:if test="${param.error != null}">
							<div class="login-error" id="errormessagediv">Incorrect
								username or password.</div>
						</c:if> 
						
						<b><div id="localError" style="color: red;"></div></b>
						
						<c:if test="${ errormessage != null}">
							<b><div style="color: red;">${errormessage }</div></b>
						</c:if>
						<div class="form-group">
							<label for="exampleInputEmail1"
								style="font-family: 'Alatsi'; font-size: 20px; color: black;">Email
								address</label>
							<form:input type="email" class="form-control"
								style="border-radius: 0; font-family: 'Alatsi'; font-size: 13px; color: black;"
								id="exampleInputEmail1" aria-describedby="emailHelp"
								path="email" placeholder="Enter email" />
							<small id="emailHelp" class="form-text text-muted"
								style="font-family: 'Alatsi'; font-size: 13px; color: black;">We'll
								never share your email with anyone else.</small>
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1"
								style="font-family: 'Alatsi'; font-size: 20px; color: black;">Password</label>
							<form:input type="password" class="form-control"
								style="border-radius: 0; font-family: 'Alatsi'; font-size: 13px; color: black;"
								id="exampleInputPassword1" placeholder="Password"
								path="plainPassword" />
						</div>
						<div class="form-group">
							<label for="exampleInputPassword2"
								style="font-family: 'Alatsi'; font-size: 20px; color: black;">Confirm
								Password</label>
							<form:input type="password" class="form-control"
								style="border-radius: 0; font-family: 'Alatsi'; font-size: 13px; color: black;"
								id="exampleInputPassword2" placeholder="Confirm Password"
								path="repeatPassword" />
						</div>
						<div class="form-group">
							<label for="exampleInputAddress1"
								style="font-family: 'Alatsi'; font-size: 20px; color: black;">Address</label>
							<form:input type="text" class="form-control"
								style="border-radius: 0; font-family: 'Alatsi'; font-size: 13px; color: black;"
								id="address" placeholder="Address" path="address" />
						</div>
						<button type="button" onclick="submitSignupForm();"
							style="width: 100%; font-family: 'Alatsi'; background-color: black; color: white;"
							class="btn ">Sign Up</button>

					</form:form>
				</div>

			</div>
		</div>
	</div>
</div>

<script>
//pasword mismatch validation
function submitSignupForm()	{
	if(document.getElementById("exampleInputPassword1").value != document.getElementById("exampleInputPassword2").value)	{
		document.getElementById("localError").innerHTML = "Password mismatch, try again";
	}	else	{
		$("#signupformid").submit();
	}
}
</script>
